/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2021 Evan Debenham
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

package com.shatteredpixel.shatteredpixeldungeon.items.armor;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Invisibility;
import com.shatteredpixel.shatteredpixeldungeon.effects.Flare;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.ui.ActionIndicator;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;

import static com.shatteredpixel.shatteredpixeldungeon.Dungeon.hero;

public class ClericArmor extends ClassArmor {
	{
		image = ItemSpriteSheet.ARMOR_CLERIC;
	}

	public static class ClericArmorBuff extends Buff{};

	@Override
	public void doSpecial() {

		if (curUser.buff(ClericArmorBuff.class) != null)
			GLog.w(Messages.get(this, "abort"));
		else {
			Buff.affect(curUser, ClericArmorBuff.class);

			charge -= 35;
			updateQuickslot();

			curUser.spend(Actor.TICK);
			curUser.sprite.operate(curUser.pos);
			Invisibility.dispel();
			curUser.busy();

			new Flare( 6, 32 ).show( hero.sprite, 4f );
			Sample.INSTANCE.play(Assets.Sounds.CHARGEUP);
			GLog.p(Messages.get(this, "charge"));

			ActionIndicator.updateIcon();
			BuffIndicator.refreshHero();
		}
	}
}