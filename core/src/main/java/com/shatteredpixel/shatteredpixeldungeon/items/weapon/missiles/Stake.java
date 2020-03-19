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

package com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Baptized;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Bless;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Corruption;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.MagicImmune;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.PinCushion;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Bat;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.ShaftParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Crossbow;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.utils.Random;

public class Stake extends MissileWeapon {
	
	{
		image = ItemSpriteSheet.STAKE;
		
		bones = false;

		tier = 1;

	}

	@Override
	public int min(int lvl) {
		int dmg =  2 * tier + (tier == 1 ? lvl : 2*lvl);
		if (bow != null){
			return  4 +                 //4 base
					2*bow.level() + dmg;  //+2 per bow level
		} else {
			return dmg;
		}
	}

	@Override
	public int max(int lvl) {
		int dmg = 5 * tier + (tier == 1 ? 2*lvl : tier*lvl);
		if (bow != null){
			return  12 +                   //12 base
					3*bow.level() + dmg;  //+3 per bow level
		} else {
			return dmg;
		}
	}

	private static Crossbow bow;

	private void updateCrossbow(){
		if (Dungeon.hero.belongings.weapon instanceof Crossbow){
			bow = (Crossbow) Dungeon.hero.belongings.weapon;
		} else {
			bow = null;
		}
	}

	@Override
	public boolean hasEnchant(Class<? extends Enchantment> type, Char owner) {
		if (bow != null && bow.hasEnchant(type, owner)){
			return true;
		} else {
			return super.hasEnchant(type, owner);
		}
	}

	@Override
	protected void rangedHit(Char enemy, int cell) {
		if (Dungeon.depth >= 6) {	//Before you enter the prison, stake will never broken
			decrementDurability();
		}
		if (durability > 0){
			//attempt to stick the missile weapon to the enemy, just drop it if we can't.
			if (sticky && enemy != null && enemy.isAlive() && enemy.buff(Corruption.class) == null
					&& enemy.buff(Baptized.class) == null){	// AddedPD : also baptized mob
				PinCushion p = Buff.affect(enemy, PinCushion.class);
				if (p.target == enemy){
					p.stick(this);
					return;
				}
			}
			Dungeon.level.drop( this, cell ).sprite.drop();
		}
	}

	@Override
	public int proc(Char attacker, Char defender, int damage) {
		if (bow != null && bow.enchantment != null && attacker.buff(MagicImmune.class) == null){
			level(bow.level());
			damage = bow.enchantment.proc(this, attacker, defender, damage);

			// AddedPD : for cleric's enlightened weapon
			if (bow.enlightened) {
				Bless bless = attacker.buff(Bless.class);
				int duration = 3+Dungeon.hero.lvl/2;
				if (Random.Int(3 ) >= 2) {
					// 33% chance to blessing
					if (bless == null) {
						Buff.prolong(attacker, Bless.class, duration);
						CellEmitter.get( attacker.pos ).start( ShaftParticle.FACTORY, 0.2f, 3 );
					}

					for (Mob mob : Dungeon.level.mobs.toArray( new Mob[0] )) {
						Bless allybless = mob.buff(Bless.class);
						if (mob.alignment == Char.Alignment.ALLY && attacker.fieldOfView[mob.pos]
								&& !mob.isCharmedBy(Dungeon.hero) && allybless == null) {
							Buff.prolong(mob, Bless.class, duration);
							CellEmitter.get( mob.pos ).start( ShaftParticle.FACTORY, 0.2f, 3 );
						}
					}
				}

			}

			level(0);
		}

		if (defender instanceof Bat){	//Stake is good for vampires :)
			damage += defender.HP/2;
		}

		return super.proc(attacker, defender, damage);
	}

	@Override
	protected void onThrow(int cell) {
		updateCrossbow();
		super.onThrow(cell);
	}

	@Override
	public String info() {
		updateCrossbow();
		return super.info();
	}
}
