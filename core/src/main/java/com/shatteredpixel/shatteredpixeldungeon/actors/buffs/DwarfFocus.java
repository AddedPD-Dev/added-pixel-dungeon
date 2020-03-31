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

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.watabou.utils.Bundle;

public class DwarfFocus extends Buff {

	{
		type = buffType.POSITIVE;
		announced = true;
	}

	private int stacks = 0;
	private boolean focused = false;

	@Override
	public boolean act() {
		BuffIndicator.refreshHero();

		if (focused) {
			if (Dungeon.hero.visibleEnemies() <= 0) {
				stacks = Math.max(stacks-5, 0);
			}
			stacks = Math.max(stacks-4, 0);
			if (stacks == 0) {
				focused = false;
				stacks = 1;
			}
		} else {
			if (Dungeon.hero.visibleEnemies() <= 0) {
				stacks = Math.max(stacks-2, 0);
			}
		}

		if (stacks == 0) detach();

		spend(TICK);
		return true;
	}

	public void gainStack(){
		if (!focused) {
			stacks = Math.min(stacks + 1, 10);
			if (stacks >= 10) {
				focused = true;
				target.sprite.showStatus(CharSprite.POSITIVE, Messages.get(this, "focused"));
			}
		}
		BuffIndicator.refreshHero();
	}

	public boolean focused(){
		return focused;
	}

	@Override
	public int icon() {
		if (focused) {
			return BuffIndicator.MIND_VISION;
		} else return BuffIndicator.DROWSY;
	}

	@Override
	public String toString() {
		return Messages.get(this, "name");
	}

	@Override
	public String desc() {
		if (focused){
			return Messages.get(this, "desc_focused");
		} else return Messages.get(this, "desc", Math.max(10-stacks, 1));
	}

	private static final String STACKS =        "stacks";
	private static final String FOCUSED = 		"focused";

	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(STACKS, stacks);
		bundle.put(FOCUSED, focused);
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		stacks = bundle.getInt(STACKS);
		focused = bundle.getBoolean(FOCUSED);
	}
}
