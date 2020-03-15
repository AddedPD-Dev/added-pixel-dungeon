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

import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.watabou.noosa.Image;
import com.watabou.utils.Bundle;

public class DwarfThunder extends Buff {
	
	{
		type = buffType.POSITIVE;
		announced = false;
	}

	protected int level;
	protected int interval;
	
	@Override
	public boolean act() {
		if (target.isAlive()) {
			interval-=TICK;
			spend(TICK);
			if (interval <= 0) {
				interval = 3;
				level--;
				BuffIndicator.refreshHero();
				if(level <= 0) {
					detach();
				}
			}
		} else {
			detach();
		}
		
		return true;
	}
	
	public int level() {
		return level;
	}
	
	public void set() {
		interval = 3;
		level++;
		if (level == 4) {
			target.sprite.showStatus(0x00CBFF, Messages.get(this, "blast"));
		} else target.sprite.showStatus(0x00CBFF, Messages.get(this, "level", level));
		BuffIndicator.refreshHero();
	}

	public void reset() {
		level = 0;
		interval = 0;
		detach();
	}

	@Override
	public int icon() {
		return BuffIndicator.VERTIGO;
	}
	
	@Override
	public void tintIcon(Image icon) {
		icon.tint(0x00CBFF, 0.25f*level);

	}
	
	@Override
	public String toString() {
		return Messages.get(this, "name");
	}
	
	@Override
	public String desc() {
		return Messages.get(this, "desc", level, interval);
	}
	
	private static final String LEVEL	    = "level";
	private static final String INTERVAL    = "interval";
	
	@Override
	public void storeInBundle( Bundle bundle ) {
		super.storeInBundle( bundle );
		bundle.put( INTERVAL, interval );
		bundle.put( LEVEL, level );
	}
	
	@Override
	public void restoreFromBundle( Bundle bundle ) {
		super.restoreFromBundle( bundle );
		interval = bundle.getInt( INTERVAL );
		level = bundle.getInt( LEVEL );
	}
}
