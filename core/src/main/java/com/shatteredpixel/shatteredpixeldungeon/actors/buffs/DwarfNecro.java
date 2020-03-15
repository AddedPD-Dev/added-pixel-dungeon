/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2019 Evan Debenham
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package com.shatteredpixel.shatteredpixeldungeon.actors.buffs;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.Necrogolem;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.PrismaticImage;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.ElmoParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfTeleportation;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.watabou.noosa.Camera;
import com.watabou.noosa.Image;
import com.watabou.noosa.audio.Sample;
import com.watabou.noosa.tweeners.AlphaTweener;
import com.watabou.utils.Bundle;
import com.watabou.utils.PathFinder;

import static com.shatteredpixel.shatteredpixeldungeon.Dungeon.hero;

public class DwarfNecro extends Buff {
	
	{
		type = buffType.POSITIVE;
	}
	
	private float HP;
	private int Ectoplasm; // 'Fuel'
	
	@Override
	public boolean act() {
		
		Hero hero = (Hero)target;

		if (checkFuel()) {

			Mob closest = null;
			int v = hero.visibleEnemies();
			for (int i = 0; i < v; i++) {
				Mob mob = hero.visibleEnemy(i);
				if (mob.isAlive() && mob.state != mob.PASSIVE && mob.state != mob.WANDERING && mob.state != mob.SLEEPING && !hero.mindVisionEnemies.contains(mob)
						&& (closest == null || Dungeon.level.distance(hero.pos, mob.pos) < Dungeon.level.distance(hero.pos, closest.pos))) {
					closest = mob;
				}
			}

			if (closest != null && Dungeon.level.distance(hero.pos, closest.pos) < 5) {
				//spawn golem
				int bestPos = -1;
				for (int i = 0; i < PathFinder.NEIGHBOURS8.length; i++) {
					int p = hero.pos + PathFinder.NEIGHBOURS8[i];
					if (Actor.findChar(p) == null && Dungeon.level.passable[p]) {
						if (bestPos == -1 || Dungeon.level.trueDistance(p, closest.pos) < Dungeon.level.trueDistance(bestPos, closest.pos)) {
							bestPos = p;
						}
					}
				}
				if (bestPos != -1) {
					Necrogolem necrogolem = new Necrogolem();
					necrogolem.summon(hero, (int) Math.floor(HP));
					necrogolem.state = necrogolem.HUNTING;
					GameScene.add(necrogolem, 1);

					necrogolem.move( bestPos );
					if (necrogolem.pos == bestPos) necrogolem.sprite.place( bestPos );

					if (necrogolem.invisible == 0) {
						necrogolem.sprite.alpha( 0 );
						necrogolem.sprite.parent.add( new AlphaTweener( necrogolem.sprite, 1, 0.4f ) );
					}
					necrogolem.sprite.speed.set( 0, -64 );
					necrogolem.sprite.acc.set( 0, 64 * 4 );

					necrogolem.sprite.interruptMotion();

					necrogolem.sprite.speed.set( 0 );
					necrogolem.sprite.acc.set( 0 );
					necrogolem.sprite.place( necrogolem.pos );

					Camera.main.shake( 2, 0.2f );

					CellEmitter.get(necrogolem.pos).burst(ElmoParticle.FACTORY, 10);
					Sample.INSTANCE.play( Assets.SND_BLAST );

					detach();
				} else {
					spend(TICK);
				}


			} else {
				spend(TICK);
			}

			LockedFloor lock = target.buff(LockedFloor.class);
			if (HP < maxHP() && (lock == null || lock.regenOn())){
				HP += 0.1f;
			}

		} else spend(TICK);
		
		return true;
	}
	
	public void set( int HP ){
		this.HP = HP;
	}

	public void gainFuel(){
		Ectoplasm++;
	}

	public void reverseFuel(){
		Ectoplasm = 10;
		checkFuel();
	}

	public boolean checkFuel(){
		if (Ectoplasm >= 10) {
			return true;
		} else return false;
	}
	
	public int maxHP(){
		return maxHP((Hero)target);
	}
	
	public static int maxHP( Hero hero ){
		return 12 + (int)Math.floor(hero.lvl * 3f);
	}
	
	@Override
	public int icon() {
		return BuffIndicator.ECTOPLASM;
	}
	
	@Override
	public String toString() {
		return Messages.get(this, "name");
	}
	
	@Override
	public String desc() {

		if (checkFuel()) {

			return Messages.get(this, "desc", (int) HP, maxHP(),
					(int) Math.ceil(hero.lvl * 0.5), (int) Math.ceil(hero.lvl * 1.2), // guardian form
					(int) Math.floor(hero.lvl * 0.3));                                // sentry form

		} else return Messages.get(this, "needfuel", Ectoplasm);
	}
	
	private static final String HEALTH = "hp";
	private static final String FUEL   = "fuel";

	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(HEALTH, HP);
		bundle.put(FUEL, Ectoplasm);
	}
	
	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		HP = bundle.getFloat(HEALTH);
		Ectoplasm = bundle.getInt(FUEL);
	}
}
