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

import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfRetribution;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.exotic.ScrollOfPsionicBlast;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.utils.Bundle;

public class Baptized extends Buff {

	{
		type = buffType.POSITIVE;
		announced = true;
	}

	private int partialRegen = 0;
	private boolean injured = false;
	public int level = 0;

	private static final String LEVEL	    = "level";
	private static final String INJURED	    = "injured";

	@Override
	public void storeInBundle( Bundle bundle ) {
		super.storeInBundle( bundle );
		bundle.put( LEVEL, level );
		bundle.put( INJURED, injured );
	}

	@Override
	public void restoreFromBundle( Bundle bundle ) {
		super.restoreFromBundle( bundle );
		level = bundle.getInt( LEVEL );
		injured = bundle.getBoolean( INJURED );
	}

	public void gainLevel(Mob mob) {
		if (level >= 30-mob.maxLvl) {
			Buff.affect(mob, Bless.class, Bless.DURATION);
		} else {
			level++;
			int curHT = mob.HT;
			mob.HT += 2*level;
			mob.HP += Math.max(mob.HT - curHT, 0);
		}
	}

	@Override
	public boolean attachTo(Char target) {
		if (super.attachTo(target)){
			target.alignment = Char.Alignment.ALLY;
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean act() {

		if (target.isAlive()) {
			partialRegen++;

			if (partialRegen >= 10) {
				target.HP = Math.min(target.HT, target.HP+1);
				partialRegen = 0;
			}

			if (target.HP <= target.HT/4 && !injured) {
				injured = true;
				GLog.w( Messages.get(Devotion.class, "baptize_injured", target.name) );
			} else if (target.HP > target.HT/4) injured = false;
		}

		spend(TICK);
		return true;
	}

	@Override
	public void fx(boolean on) {
		if (on) {
			target.sprite.add( CharSprite.State.ILLUMINATED );
			target.sprite.add( CharSprite.State.LIGHTENED ); }
		else if (target.invisible == 0) {
			target.sprite.remove( CharSprite.State.ILLUMINATED );
			target.sprite.remove( CharSprite.State.LIGHTENED );
		}
	}

	@Override
	public int icon() {
		return BuffIndicator.BAPTIZED;
	}

	@Override
	public String toString() {
		return Messages.get(Devotion.class, "baptize_buff_name");
	}

	@Override
	public String desc() {
		return Messages.get(Devotion.class, "baptize_buff_desc", level, level*3, level*2, level);
	}

	{
		immunities.add( ScrollOfRetribution.class );
		immunities.add( ScrollOfPsionicBlast.class );
		immunities.add( Charm.class );
		immunities.add( Amok.class );
	}
}
