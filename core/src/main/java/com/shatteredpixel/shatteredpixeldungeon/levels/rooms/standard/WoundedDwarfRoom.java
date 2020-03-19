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

package com.shatteredpixel.shatteredpixeldungeon.levels.rooms.standard;

import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.WoundedDwarf;
import com.shatteredpixel.shatteredpixeldungeon.levels.Level;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.levels.painters.Painter;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.SummoningTrap;
import com.watabou.utils.Point;

public class WoundedDwarfRoom extends StandardRoom {

	@Override
	public int minWidth() {
		return Math.max(super.minWidth(), 4);
	}

	@Override
	public int minHeight() {
		return Math.max(super.minHeight(), 4);
	}

	public void paint(Level level ) {

		Painter.fill( level, this, Terrain.WALL );
		Painter.fill( level, this, 1, Terrain.TRAP );

		for (Door door : connected.values()) {
			door.set( Door.Type.HIDDEN );
		}

		WoundedDwarf npc = new WoundedDwarf();
		do {
			npc.pos = level.pointToCell(random( 1 ));
		} while (level.heaps.get( npc.pos ) != null);
		level.mobs.add( npc );

		for(Point p : getPoints()) {
			int cell = level.pointToCell(p);
			if (level.map[cell] == Terrain.TRAP){
				level.setTrap(new SummoningTrap().reveal(), cell);
			}
		}
	}
}
