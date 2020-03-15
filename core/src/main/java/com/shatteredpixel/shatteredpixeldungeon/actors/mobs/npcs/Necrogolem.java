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

package com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.CorrosiveGas;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.ToxicGas;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Amok;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Burning;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Corruption;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.DwarfNecro;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.ElmoParticle;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.NecroGolemSprite;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndOptions;
import com.watabou.noosa.Game;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

public class Necrogolem extends NPC {
	
	{
		spriteClass = NecroGolemSprite.class;
		
		HP = HT = 12;
		defenseSkill = 1;

		alignment = Alignment.ALLY;
		intelligentAlly = true;
		state = HUNTING;

		WANDERING = new Necrogolem.Wandering();
		
		//before other mobs
		actPriority = MOB_PRIO + 1;
	}

	private Hero hero;
	private int heroID;
	public int golemTier;
	
	@Override
	protected boolean act() {
		
		if ( hero == null ){
			hero = (Hero)Actor.findById(heroID);
			if ( hero == null ){
				die(null);
				sprite.killAndErase();
				return true;
			}
		}

		if (golemTier == 0) {
			flying = true;
		} else flying = false;

		if (distance(hero) > 5) {
			CellEmitter.get(pos).burst(ElmoParticle.FACTORY, 10);
			Buff.affect(hero, DwarfNecro.class).set( HP );
			Buff.affect(hero, DwarfNecro.class).reverseFuel();
			destroy();
			sprite.killAndErase();
			Sample.INSTANCE.play( Assets.SND_MELD );
			return true;
		}

		return super.act();
	}
	
	private static final String HEROID	= "hero_id";
	
	@Override
	public void storeInBundle( Bundle bundle ) {
		super.storeInBundle( bundle );
		bundle.put( HEROID, heroID );
	}
	
	@Override
	public void restoreFromBundle( Bundle bundle ) {
		super.restoreFromBundle( bundle );
		heroID = bundle.getInt( HEROID );
	}
	
	public void summon( Hero hero, int HP ) {
		this.hero = hero;
		heroID = this.hero.id();
		this.HP = HP;
		HT = DwarfNecro.maxHP( hero );
		golemTier = 1;
	}

	@Override
	public int damageRoll() {
		return Random.NormalIntRange(4 + hero.lvl / 8, 8 + hero.lvl / 2);
	}

	@Override
	public int attackSkill( Char target ) {
		return hero.attackSkill(target);
	}

	@Override
	public int defenseSkill(Char enemy) {
		if (hero != null) {
			int baseEvasion = 4 + hero.lvl;
			int heroEvasion = hero.defenseSkill(enemy);

			//if the hero has more/less evasion, 50% of it is applied
			return super.defenseSkill(enemy) * (baseEvasion + heroEvasion) / 2;
		} else {
			return 0;
		}
	}

	@Override
	public boolean interact() {

		Char ally = this;
		Game.runOnRenderThread(new Callback() {
			@Override
			public void call() {
				GameScene.show(new WndOptions(
						Messages.get(Necrogolem.class, "interact_title"),
						Messages.get(Necrogolem.class, "interact_prompt"),
						Messages.get(Necrogolem.class, "interact_guardian"),
						Messages.get(Necrogolem.class, "interact_sentry"),
						Messages.get(Necrogolem.class, "interact_place"),
						Messages.get(Necrogolem.class, "interact_cancel")){
					@Override
					protected void onSelect(int index) {
						if (index == 0){
							golemTier = 1;
							((NecroGolemSprite)sprite).transformGolem(1);
							sprite.update();
							CellEmitter.get(pos).burst(ElmoParticle.FACTORY, 10);
						}

						if (index == 1) {
							golemTier = 0;
							((NecroGolemSprite)sprite).transformGolem(0);
							sprite.update();
							CellEmitter.get(pos).burst(ElmoParticle.FACTORY, 10);
						}

						if (index == 2) {
							int curPos = pos;

							moveSprite( pos, Dungeon.hero.pos );
							move( Dungeon.hero.pos );

							Dungeon.hero.sprite.move( Dungeon.hero.pos, curPos );
							Dungeon.hero.move( curPos );

							Dungeon.hero.spend( 1 / Dungeon.hero.speed() );
						}

						if (index == 3) {
							// cancel, do noting
						} }

				}); }	});

		return false;
	}

	@Override
	protected float attackDelay() {
		if (golemTier == 1) {
			return 2f; // guardian form is not good at attack
		} else return super.attackDelay();
	}
	
	@Override
	protected boolean canAttack(Char enemy) {
		if (golemTier == 0 && distance(enemy) > 5) {
			return new Ballistica( pos, enemy.pos, Ballistica.MAGIC_BOLT).collisionPos == enemy.pos;
		} else return super.canAttack(enemy);
	}

	//used so resistances can differentiate between melee and magical attacks
	public static class NecroBolt {}

	@Override
	public boolean attack( Char enemy ) {

		if (!Dungeon.level.adjacent( pos, enemy.pos )) {
			spend( attackDelay() );
			if (hit( this, enemy, true )) {
				int dmg =  damageRoll();
				enemy.damage( dmg, new NecroBolt() );

				enemy.sprite.bloodBurstA( sprite.center(), dmg );
				enemy.sprite.flash();

				if (!enemy.isAlive() && enemy == Dungeon.hero) {
					Dungeon.fail( getClass() );
					GLog.n( Messages.get(Char.class, "kill", name) );
				}
				return true;
			} else {
				enemy.sprite.showStatus( CharSprite.NEUTRAL,  enemy.defenseVerb() );
				return false;
			}
		} else {
			return super.attack( enemy );
		}
	}

	@Override
	public int drRoll() {
		if (hero != null){
			if (golemTier == 1) {
				return Random.NormalIntRange((int)Math.ceil(hero.lvl*0.5), (int)Math.ceil(hero.lvl*1.2));
			} else return Random.NormalIntRange(0, (int)Math.floor(hero.lvl*0.3));
		} else {
			return 0;
		}
	}
	
	@Override
	public int attackProc( Char enemy, int damage ) {
		damage = super.attackProc( enemy, damage );
		
		if (enemy instanceof Mob && golemTier == 1) {
			((Mob)enemy).aggro( this ); // guardian form only
		}

		return damage;
	}

	@Override
	public CharSprite sprite() {
		CharSprite s = super.sprite();
		((NecroGolemSprite)s).transformGolem( golemTier );
		return s;
	}

	{
		immunities.add( ToxicGas.class );
		immunities.add( CorrosiveGas.class );
		immunities.add( Burning.class );
		immunities.add( Corruption.class );
		immunities.add( Amok.class );
	}

	private class Wandering extends Mob.Wandering{

		@Override
		public boolean act(boolean enemyInFOV, boolean justAlerted) {
			if (!enemyInFOV){
				CellEmitter.get(pos).burst(ElmoParticle.FACTORY, 10);
				Buff.affect(hero, DwarfNecro.class).set( HP );
				Buff.affect(hero, DwarfNecro.class).reverseFuel();
				destroy();
				sprite.killAndErase();
				Sample.INSTANCE.play( Assets.SND_MELD );
				return true;
			} else {
				return super.act(enemyInFOV, justAlerted);
			}
		}

	}
}