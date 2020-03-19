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

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.Electricity;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.ToxicGas;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Eye;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Shaman;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Warlock;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Yog;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.RainbowParticle;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.ShaftParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfRetribution;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfTerror;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.exotic.ScrollOfPsionicBlast;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfBlastWave;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfDisintegration;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfFireblast;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfFrost;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfLightning;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfLivingEarth;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfMagicMissile;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfPrismaticLight;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfTransfusion;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfWarding;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.DisintegrationTrap;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.GrimTrap;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.shatteredpixel.shatteredpixeldungeon.utils.BArray;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.Image;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

public class Baptized extends Buff {

	{
		type = buffType.POSITIVE;
		announced = true;
	}

	private enum Perk {
		NORMAL,
		VIGOR,		// Faster regen, more faster when injured
		ELEMENTAL,	// Resist elements, just like RoElements
		GUARDIAN	// Shields nearby allies
	}
	private Perk perk = Perk.NORMAL;

	private int partialRegen = 0;
	private boolean injured = false;
	public int level = 0;

	private static final String PERK 	= "perk";
	private static final String LEVEL	= "level";
	private static final String INJURED	= "injured";

	@Override
	public void storeInBundle( Bundle bundle ) {
		super.storeInBundle( bundle );
		bundle.put( PERK, perk );
		bundle.put( LEVEL, level );
		bundle.put( INJURED, injured );
	}

	@Override
	public void restoreFromBundle( Bundle bundle ) {
		super.restoreFromBundle( bundle );
		perk = bundle.getEnum( PERK, Perk.class );
		level = bundle.getInt( LEVEL );
		injured = bundle.getBoolean( INJURED );
	}

	public void gainLevel(Mob mob) {
		if (level >= 30-mob.maxLvl) {
			Buff.affect(mob, Bless.class, Bless.DURATION);
		} else {
			level++;
			int curHT = mob.HT;
			mob.HT += 4*level;
			mob.HP += Math.max(mob.HT - curHT, 0);

			if (level == 3) {
				GLog.p( Messages.get(Devotion.class, "baptize_perk", target.name) );
				switch (Random.Int(2)) {
					case 0: default:
						perk = Perk.VIGOR;
						break;
					case 1:
						perk = Perk.ELEMENTAL;
						resistances.add( Burning.class );
						resistances.add( Charm.class );
						resistances.add( Chill.class );
						resistances.add( Frost.class );
						resistances.add( Ooze.class );
						resistances.add( Paralysis.class );
						resistances.add( Poison.class );
						resistances.add( Corrosion.class );
						resistances.add( Weakness.class );

						resistances.add( DisintegrationTrap.class );
						resistances.add( GrimTrap.class );

						resistances.add( WandOfBlastWave.class );
						resistances.add( WandOfDisintegration.class );
						resistances.add( WandOfFireblast.class );
						resistances.add( WandOfFrost.class );
						resistances.add( WandOfLightning.class );
						resistances.add( WandOfLivingEarth.class );
						resistances.add( WandOfMagicMissile.class );
						resistances.add( WandOfPrismaticLight.class );
						resistances.add( WandOfTransfusion.class );
						resistances.add( WandOfWarding.Ward.class );

						resistances.add( ToxicGas.class );
						resistances.add( Electricity.class );

						resistances.add( Shaman.LightningBolt.class );
						resistances.add( Warlock.DarkBolt.class );
						resistances.add( Eye.DeathGaze.class );
						resistances.add( Yog.BurningFist.DarkBolt.class );
						break;
					case 2:
						perk = Perk.GUARDIAN;
						break;
				}
			}

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
			if (perk == Perk.VIGOR) {
				target.HP = Math.min(target.HT, target.HP+1);
				if (injured){
					target.HP = Math.min(target.HT, target.HP+5);
				}
			}

			if (partialRegen == 7 && perk == Perk.GUARDIAN){
				target.sprite.emitter().pour(RainbowParticle.ATTRACTING, 0.05f);
			}

			if (partialRegen >= 8) {
				target.HP = Math.min(target.HT, target.HP+1);
				partialRegen = 0;

				if (perk == Perk.GUARDIAN) {
					PathFinder.buildDistanceMap( target.pos, BArray.not( Dungeon.level.solid, null ), 2 );
					for (int i = 0; i < PathFinder.distance.length; i++) {
						if (PathFinder.distance[i] < Integer.MAX_VALUE) {
							Char ch = Actor.findChar( i );
							if (ch != null && ch.alignment == Dungeon.hero.alignment
									&& ch.buff(Charm.class) != null) {
								if (ch.isAlive() && ch.shielding() <= Math.min(24, level*4)) {
									target.sprite.emitter().burst(RainbowParticle.BURST, 12);
									Buff.affect(ch, Barrier.class).setShield(Math.min(24, level*4));
									CellEmitter.get( ch.pos ).start( ShaftParticle.FACTORY, 0.2f, 3 );
								}
							}
						}
					}
					Sample.INSTANCE.play(Assets.SND_MELD);
				}
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
	public void tintIcon(Image icon) {
		switch (perk){
			case NORMAL: default:
				break;
			case VIGOR:
				icon.tint(0, 0.5f, 0, 0.25f);
				break;
			case ELEMENTAL:
				icon.tint(0, 0.5f, 0.5f, 0.25f);
				break;
			case GUARDIAN:
				icon.tint(0, 0, 0.5f, 0.25f);
				break;
		}
	}

	@Override
	public String toString() {
		return Messages.get(Devotion.class, "baptize_buff_name");
	}

	@Override
	public String desc() {
		String msg = "";
		if (level >= 3) {
			if (perk == Perk.VIGOR) {
				msg = Messages.get(Devotion.class, "baptize_perk_vigor");
			} else if (perk == Perk.ELEMENTAL) {
				msg = Messages.get(Devotion.class, "baptize_perk_elemental");
			} else if (perk == Perk.GUARDIAN) {
				msg = Messages.get(Devotion.class, "baptize_perk_guardian");
			}
		}
		return Messages.get(Devotion.class, "baptize_buff_desc", level, 10+(level*4), Math.min(level*3, 21), Math.min(level, 12)) + msg;
	}

	{
		immunities.add( ScrollOfRetribution.class );
		immunities.add( ScrollOfPsionicBlast.class );
		immunities.add( ScrollOfTerror.class );
		immunities.add( Charm.class );
		immunities.add( Amok.class );
	}
}
