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

package com.shatteredpixel.shatteredpixeldungeon.items.armor.glyphs;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.HeroSubClass;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.EnergyParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.Armor;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.Armor.Glyph;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite.Glowing;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.watabou.noosa.Image;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class Potential extends Glyph {
	
	private static ItemSprite.Glowing WHITE = new ItemSprite.Glowing( 0xFFFFFF, 0.6f );
	
	@Override
	public int proc( Armor armor, Char attacker, Char defender, int damage) {

		int level = Math.max( 0, armor.level() );
		
		// lvl 0 - 16.7%
		// lvl 1 - 28.6%
		// lvl 2 - 37.5%
		if (defender instanceof Hero && Random.Int( level + 6 ) >= 5 ) {
			int wands = ((Hero) defender).belongings.charge( 1f );
			if (wands > 0) {
				defender.sprite.centerEmitter().burst(EnergyParticle.FACTORY, 10);
			}

			// AddedPD : for sealknight - imbue your attack with magical strike when glyph is activated
			if (defender == Dungeon.hero && Dungeon.hero.subClass == HeroSubClass.SEALKNIGHT
					&& armor.checkSeal() != null) {
				SealCharge charge = Buff.affect( defender, SealCharge.class );
				charge.prolong( damage );
				if (wands <= 0) {
					defender.sprite.centerEmitter().burst(EnergyParticle.FACTORY, 10);
				}
			}
		}
		
		return damage;
	}

	@Override
	public Glowing glowing() {
		return WHITE;
	}

	// AddedPD : for sealknight
	public static class SealCharge extends Buff {

		{
			type = buffType.POSITIVE;
		}

		protected int damage = 0;
		protected int partialDamage = 3;

		private static final String DAMAGE	= "damage";
		private static final String PARTIAL_DAMAGE	= "partialDamage";

		@Override
		public void storeInBundle( Bundle bundle ) {
			super.storeInBundle( bundle );
			bundle.put( DAMAGE, damage );
			bundle.put( PARTIAL_DAMAGE, partialDamage );
		}

		@Override
		public void restoreFromBundle( Bundle bundle ) {
			super.restoreFromBundle( bundle );
			damage = bundle.getInt( DAMAGE );
			partialDamage = bundle.getInt( PARTIAL_DAMAGE );
		}

		public int getDamage(){
			// without this, sometimes we met funny situation - "kill blacksmith", etc.
			if (damage >= 100) { damage = 100; }
			return damage;
		}

		public void costDamage(int dmg) {
			damage -= dmg;
			if (damage < 0) damage = 0;
		}

		@Override
		public boolean attachTo( Char target ) {
			if (super.attachTo( target )) {
				postpone( TICK );
				return true;
			} else {
				return false;
			}
		}

		@Override
		public boolean act() {
			spend( TICK );
			partialDamage--;
			if (0 >= partialDamage){
				partialDamage = 3;
				damage -= TICK;
			}
			if (damage <= 0) {
				detach();
			}
			return true;
		}

		public void prolong( int damage ) {
			this.damage += damage;
		}

		@Override
		public int icon() {
			return BuffIndicator.WEAPON;
		}

		@Override
		public void tintIcon(Image icon) {
			icon.hardlight(0xFFFF4C);
		}

		@Override
		public String toString() {
			return Messages.get(this, "name");
		}

		@Override
		public String desc() {
			return Messages.get(SealCharge.class, "desc",  damage);
		}
	}
}
