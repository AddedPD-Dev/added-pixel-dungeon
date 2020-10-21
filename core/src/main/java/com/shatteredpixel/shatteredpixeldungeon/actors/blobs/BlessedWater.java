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

package com.shatteredpixel.shatteredpixeldungeon.actors.blobs;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Devotion;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Slow;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Weakness;
import com.shatteredpixel.shatteredpixeldungeon.effects.BlobEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.watabou.noosa.Image;
import com.watabou.utils.PathFinder;

public class BlessedWater extends Blob {

	private boolean[] water;

	@Override
	protected void evolve() {

		water = Dungeon.level.water;
		int cell;

		//spread first..
		for (int i = area.left-1; i <= area.right; i++) {
			for (int j = area.top-1; j <= area.bottom; j++) {
				cell = i + j*Dungeon.level.width();

				if (cur[cell] > 0) {
					spreadFromCell(cell, cur[cell]);
				}
			}
		}

		//..then slow + weaken evil beings!
		for (int i = area.left-1; i <= area.right; i++) {
			for (int j = area.top-1; j <= area.bottom; j++) {
				cell = i + j*Dungeon.level.width();
				if (cur[cell] > 0) {
					Char ch = Actor.findChar( cell );
					if (ch != null && ch.alignment != Dungeon.hero.alignment
							&& (ch.properties().contains(Char.Property.DEMONIC)
							||ch.properties().contains(Char.Property.UNDEAD))
							&& !ch.flying) {
						Buff.prolong( ch, Slow.class, 1f);
						Buff.prolong( ch, Weakness.class, 1f);
						ch.sprite.centerEmitter().burst( Speck.factory(Speck.LIGHT), 4);
					}

					off[cell] = cur[cell] - 1;
					volume += off[cell];
				} else {
					off[cell] = 0;
				}
			}
		}

	}

	private void spreadFromCell( int cell, int power ){
		if (cur[cell] == 0) {
			area.union(cell % Dungeon.level.width(), cell / Dungeon.level.width());
		}
		cur[cell] = Math.max(cur[cell], power);

		for (int c : PathFinder.NEIGHBOURS4){
			if (water[cell + c] && cur[cell + c] < power){
				spreadFromCell(cell + c, power);
			}
		}
	}

	@Override
	public void use( BlobEmitter emitter ) {
		super.use( emitter );
		emitter.start(Speck.factory(Speck.LIGHT), 1f, 0 );
		emitter.start(Speck.factory(Speck.LIGHT), 0.5f, -1 );
	}

	@Override
	public String tileDesc() {
		return Messages.get(Devotion.class, "holy_water_desc");
	}
}