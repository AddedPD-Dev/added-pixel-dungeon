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

package com.shatteredpixel.shatteredpixeldungeon.items.armor;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Burning;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Roots;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.SoulMark;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.TransmuterBuff;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.HeroSubClass;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.effects.SpellSprite;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.ElmoParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfRecharging;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.Wand;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MagesStaff;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;

public class MageArmor extends ClassArmor {

	private Wand wand;
	private MagesStaff staff;
	
	{
		image = ItemSpriteSheet.ARMOR_MAGE;
	}

	@Override
	public void doSpecial() {

		for (Mob mob : Dungeon.level.mobs.toArray(new Mob[0])) {
			if (Dungeon.level.heroFOV[mob.pos]
					&& mob.alignment != Char.Alignment.ALLY) {

				int hit = 0;

				// if there is no enemy in your sight, cancel ability(like huntress's ability)
				if (mob == null) {
					GLog.w(Messages.get(this, "no_enemies"));
					return;
				}

				hit++;

				// battlemage - similar effects on staff attack effects(use code from stone of shock)
				if (curUser.subClass == HeroSubClass.BATTLEMAGE){
					curUser.belongings.charge(hit);
					ScrollOfRecharging.charge(curUser);
				}

				// warlock - mark souls of all affected enemy, but with shorter duration
				if (curUser.subClass == HeroSubClass.WARLOCK) {
					SoulMark soulMark = mob.buff(SoulMark.class);
					// prevents reduce mark duration which are already marked mobs
					if (soulMark == null) { SoulMark.prolong(mob, SoulMark.class, 5); }
				}

				// transmuter - remove 'moment of change', allow to instant re-transmuting
				if (curUser.subClass == HeroSubClass.WARLOCK) {
					TransmuterBuff tbuff = curUser.buff(TransmuterBuff.class);
					// remove 'cooltime' instantly
					if (tbuff != null) { tbuff.detach(); }
				}

				Buff.affect( mob, Burning.class ).reignite( mob );
				Buff.prolong( mob, Roots.class, 3 );
			}
		}

		curUser.HP -= (curUser.HP / 3);

		curUser.spend( Actor.TICK );
		curUser.sprite.operate( curUser.pos );
		curUser.busy();

		curUser.sprite.centerEmitter().start( ElmoParticle.FACTORY, 0.15f, 4 );
		Sample.INSTANCE.play( Assets.SND_READ );
	}
}