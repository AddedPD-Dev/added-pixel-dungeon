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
import com.watabou.utils.Bundle;
import com.watabou.utils.Point;


public class WoundedDwarfRoom extends StandardRoom {

	private Door door;
	public boolean locked = false;

	private static final String DOOR = "door";
	private static final String LOCKED	= "locked";

	@Override
	public void storeInBundle( Bundle bundle ) {
		super.storeInBundle( bundle );
		bundle.put( LOCKED, locked );
		bundle.put( DOOR, door );
	}

	@Override
	public void restoreFromBundle( Bundle bundle ) {
		super.restoreFromBundle( bundle );
		locked = bundle.getBoolean( LOCKED );
		if (bundle.contains(DOOR)){
			door = (Door)bundle.get(DOOR);
		}
	}

	@Override
	public int maxWidth() {
		return 8;
	}

	@Override
	public int maxHeight() {
		return 8;
	}

	@Override
	public int minWidth() {
		return 7;
	}

	@Override
	public int minHeight() {
		return 7;
	}

	public void paint( Level level ) {

		Painter.fill( level, this, Terrain.WALL );
		Painter.fill( level, this, 1, Terrain.WATER );
		Painter.fill( level, this, 2, Terrain.SECRET_TRAP );

		Point c = center();
		int cx = c.x;
		int cy = c.y;


		for (Door door : connected.values()) {
			door.set( Door.Type.HIDDEN );
			Painter.drawInside( level, this, door, 1, Terrain.WATER );
		}

		WoundedDwarf npc = new WoundedDwarf();
		npc.pos = cx + cy * level.width();
		level.mobs.add( npc );

		for(Point p : getPoints()) {
			int cell = level.pointToCell(p);
			if (level.map[cell] == Terrain.SECRET_TRAP){
				level.setTrap(new SummoningTrap().hide(), cell);
			}
		}
	}
}