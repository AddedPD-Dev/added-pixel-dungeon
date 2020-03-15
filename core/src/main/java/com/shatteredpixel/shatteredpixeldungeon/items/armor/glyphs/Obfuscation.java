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
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.Blob;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.SmokeScreen;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Blindness;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.FlavourBuff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Frost;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.HeroSubClass;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.Armor;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.shatteredpixel.shatteredpixeldungeon.utils.BArray;
import com.watabou.noosa.Image;
import com.watabou.utils.PathFinder;

public class Obfuscation extends Armor.Glyph {

	private static ItemSprite.Glowing GREY = new ItemSprite.Glowing( 0x888888 );

	@Override
	public int proc(Armor armor, Char attacker, Char defender, int damage) {

		// AddedPD : for sealknight - remove aggro from other enemies
		if (defender == Dungeon.hero  && armor.checkSeal() != null
				&& Dungeon.hero.subClass == HeroSubClass.SEALKNIGHT) {

			int affected = 0;
			int set = Math.max(20, 20-(2*armor.level()));

			SealCooldown cooldown = defender.buff(SealCooldown.class);
			if (cooldown == null) {

				int cell = Dungeon.hero.pos;
				PathFinder.buildDistanceMap( cell, BArray.not( Dungeon.level.solid, null ), 2 );
				for (int i = 0; i < PathFinder.distance.length; i++) {
					if (PathFinder.distance[i] < Integer.MAX_VALUE) {
						GameScene.add(Blob.seed(i, 5, SmokeScreen.class));
					}
				}

			}
			if (affected > 0) {
                CellEmitter.center(defender.pos).burst(Speck.factory(Speck.SMOKE), 30);
				Buff.affect(defender, SealCooldown.class, set);
			}

			return damage; } else

		//no proc effect, see armor.stealthfactor for effect.
		return damage;
	}

	@Override
	public ItemSprite.Glowing glowing() {
		return GREY;
	}

	// AddedPD : for sealknight
	public static class SealCooldown extends FlavourBuff {

		{
			type = buffType.NEUTRAL;
		}

		float left = cooldown();

		@Override
		public int icon() {
			return BuffIndicator.DEFERRED;
		}

		@Override
		public void tintIcon(Image icon) {
			icon.hardlight(-2f, 1f, 2f);
		}

		@Override
		public String toString() {
			return Messages.get(this, "name");
		}

		{
			immunities.add( SmokeScreen.class );
		}

		@Override
		public String desc() {
			return Messages.get(this, "desc",  left);
		}
	}
}
