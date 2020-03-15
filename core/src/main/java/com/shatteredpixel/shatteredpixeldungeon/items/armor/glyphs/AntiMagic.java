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

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Charm;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Weakness;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.HeroSubClass;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Eye;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Shaman;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Warlock;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Yog;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.MagicMissile;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.Armor;
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
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.watabou.noosa.Image;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

import java.util.HashSet;

public class AntiMagic extends Armor.Glyph {

	private static ItemSprite.Glowing TEAL = new ItemSprite.Glowing( 0x88EEFF );
	
	public static final HashSet<Class> RESISTS = new HashSet<>();
	static {
		RESISTS.add( Charm.class );
		RESISTS.add( Weakness.class );
		
		RESISTS.add( DisintegrationTrap.class );
		RESISTS.add( GrimTrap.class );

		RESISTS.add( WandOfBlastWave.class );
		RESISTS.add( WandOfDisintegration.class );
		RESISTS.add( WandOfFireblast.class );
		RESISTS.add( WandOfFrost.class );
		RESISTS.add( WandOfLightning.class );
		RESISTS.add( WandOfLivingEarth.class );
		RESISTS.add( WandOfMagicMissile.class );
		RESISTS.add( WandOfPrismaticLight.class );
		RESISTS.add( WandOfTransfusion.class );
		RESISTS.add( WandOfWarding.Ward.class );
		
		RESISTS.add( Shaman.LightningBolt.class );
		RESISTS.add( Warlock.DarkBolt.class );
		RESISTS.add( Eye.DeathGaze.class );
		RESISTS.add( Yog.BurningFist.DarkBolt.class );
	}
	
	@Override
	public int proc(Armor armor, Char attacker, Char defender, int damage) {
		//no proc effect, see Hero.damage

		// AddedPD : for sealknight - 'counter zap'
		if (defender == Dungeon.hero && Dungeon.hero.subClass == HeroSubClass.SEALKNIGHT
				&& armor.checkSeal() != null) {

			SealMagic sealMagic = Dungeon.hero.buff(SealMagic.class);
			if (sealMagic != null) {
				sealMagic.counterZap(attacker);
			}
		}
		return damage;
	}
	
	public static int drRoll( int level ){
		return Random.NormalIntRange(level, 4 + (level*2));
	}

	@Override
	public ItemSprite.Glowing glowing() {
		return TEAL;
	}

	// AddedPD : for sealknight
	public static class SealMagic extends Buff {

		{
			type = buffType.POSITIVE;
		}

		protected int damage = 0;
		protected int partialDamage = 2;

		private static final String DAMAGE	= "damage";
		private static final String PARTIAL_DAMAGE	= "partialDamage";

		@Override
		public void storeInBundle( Bundle bundle ) {
			super.storeInBundle( bundle );
			bundle.put( DAMAGE, damage );
			bundle.put( PARTIAL_DAMAGE, partialDamage );
		}

		@Override
		public void restoreFromBundle( Bundle bundle ) {
			super.restoreFromBundle( bundle );
			damage = bundle.getInt( DAMAGE );
			partialDamage = bundle.getInt( PARTIAL_DAMAGE );
		}

		public int getDamage(){
			return damage;
		}

		public void getSource(int dmg) {
			damage += dmg;
			BuffIndicator.refreshHero();
		}

		@Override
		public boolean attachTo( Char target ) {
			if (super.attachTo( target )) {
				postpone( TICK );
				return true;
			} else {
				return false;
			}
		}

		@Override
		public boolean act() {
			spend( TICK );
			partialDamage--;
			if (0 >= partialDamage){
				partialDamage = 2;
				damage -= TICK;
			}
			if (damage <= 0) {
				detach();
			}
			return true;
		}

		public void spendTime() {
			spend( TICK );
			if (damage <= 0) {
				detach();
			}
		}

		public void prolong( int damage ) {
			this.damage = damage;
			BuffIndicator.refreshHero();
		}

		@Override
		public int icon() {
			return BuffIndicator.ARMOR;
		}

		@Override
		public void tintIcon(Image icon) {
			icon.hardlight(0x88EEFF);
		}

		@Override
		public String toString() {
			return Messages.get(this, "name");
		}

		@Override
		public String desc() {
			return Messages.get(this, "desc",  damage);
		}

		public void counterZap(Char mob) {

			final Ballistica bolt = new Ballistica(Dungeon.hero.pos, mob.pos, Ballistica.MAGIC_BOLT);

			if (damage >= 1 && mob.alignment != Dungeon.hero.alignment) {
				Sample.INSTANCE.play(Assets.SND_ZAP);
				CellEmitter.center(Dungeon.hero.pos).burst(MagicMissile.MagicParticle.FACTORY, 4);
				MagicMissile.boltFromChar(Dungeon.hero.sprite.parent,
						MagicMissile.OTHER_MAGIC,
						Dungeon.hero.sprite,
						bolt.collisionPos,
						new Callback() {
							@Override
							public void call() {

								int realDamage = 0;
								if (mob.HP <= damage) { realDamage = mob.HP;
								} else { realDamage = damage; }

								mob.sprite.flash();
								CellEmitter.center(mob.pos).burst(MagicMissile.MagicParticle.FACTORY, realDamage + 3);
								mob.damage(realDamage, Dungeon.hero);

								damage -= realDamage;
							}
						});
			} else {
				CellEmitter.center(Dungeon.hero.pos).burst(MagicMissile.MagicParticle.FACTORY, 1);
			}
		}
	}
}