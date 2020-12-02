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
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.ToxicGas;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Bleeding;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Poison;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.HeroSubClass;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.ElmoParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfTeleportation;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.CellSelector;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.ui.ActionIndicator;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.shatteredpixel.shatteredpixeldungeon.ui.Icons;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.Image;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Callback;

import java.util.ArrayList;

public class DMArmor extends Armor {

	{
		image = ItemSpriteSheet.ARMOR_DM_BASIC;

		bones = false;
	}

	@Override
	public boolean isUpgradable() {
		return false;
	}

	@Override
	public int DRMax(int lvl) {
		return 2 + Dungeon.hero.lvl/2
				+ augment.defenseFactor(Dungeon.hero.lvl/2);
	}

	@Override
	public int DRMin(int lvl) {
		return 1 + (int)(Dungeon.hero.lvl/3.3f)
				+ augment.defenseFactor((int)(Dungeon.hero.lvl/3.3f));
	}

	public DMArmor() {
		super( 1 );
	}

	public static class DM_Teleport extends Buff implements ActionIndicator.Action {

		{
			type = buffType.POSITIVE;
		}

		public int charge;
		private int pos;
		private static final String CHARGE = "charge";
		private static final String POS		= "pos";
		private static final float STEP = 1f;

		@Override
		public void storeInBundle( Bundle bundle ) {
			super.storeInBundle( bundle );
			bundle.put(POS, pos);
			bundle.put(CHARGE, charge);
		}

		@Override
		public void restoreFromBundle( Bundle bundle ) {
			super.restoreFromBundle(bundle);
			pos = bundle.getInt(POS);
			charge = bundle.getInt(CHARGE);
			if (charge >= 0) ActionIndicator.setAction(this);
		}

		@Override
		public Image getIcon() {
			Image icon;
			icon = Icons.get(Icons.DM_HERO);
			icon.tint(0xFF00FF00);
			return icon;
		}

		@Override
		public void doAction() {
			GameScene.selectCell(doTeleport);
		}

		public CellSelector.Listener doTeleport = new CellSelector.Listener() {
			@Override
			public void onSelect(Integer cell) {
				DM_Teleport teleport = Dungeon.hero.buff(DM_Teleport.class);

				// self blink
				if (cell != null) {

					if (Dungeon.level.distance(cell, Dungeon.hero.pos) > teleport.currentCharge()) {
						GLog.w(Messages.get(DMArmor.class, "cant_far"), teleport.currentCharge());
					} else if (  !Dungeon.level.heroFOV[cell]) {
						GLog.w(Messages.get(DMArmor.class, "cant_see"));
					} else {

						Dungeon.hero.spend(Actor.TICK);
						Dungeon.hero.busy();
						Dungeon.hero.sprite.attack(Dungeon.hero.pos, new Callback() {

							@Override
							public void call() {
								ScrollOfTeleportation.teleportToLocation(Dungeon.hero, cell);
								Dungeon.hero.sprite.emitter().burst(ElmoParticle.FACTORY, 10);
								teleport.loseCharge();
								Dungeon.hero.onOperateComplete();
							}
						});
					}
				}
			}

			@Override
			public String prompt () {
				DM_Teleport teleport = Dungeon.hero.buff(DM_Teleport.class);
				return Messages.get(DMArmor.class, "teleport_prompt", teleport.currentCharge());
			}
		};

		public int currentCharge(){
			return charge;
		}

		@Override
		public boolean attachTo( Char target ) {
			pos = target.pos;
			return super.attachTo( target );
		}

		@Override
		public boolean act() {
			if (target.pos != pos) {
				loseCharge();
				pos = target.pos;
			} else {
				gainCharge();
			}
			spend( STEP );
			return true;
		}

		public void gainCharge(){
			if (charge < 3) {
				charge = Math.min(charge + 1, 3);
				target.sprite.emitter().burst(ElmoParticle.FACTORY, 5);
				Sample.INSTANCE.play( Assets.Sounds.CHARGEUP );
			}
		}

		public void loseCharge(){
			charge = 0;
		}

		@Override
		public int icon() {
			return BuffIndicator.VERTIGO;
		}

		@Override
		public void tintIcon(Image icon) {
			if (charge >= 3) icon.hardlight(0, 1f, 0);
			else if (charge == 2) icon.hardlight(0, 0.8f, 0);
			else if (charge == 1) icon.hardlight(0, 0.5f, 0);
			else if (charge == 0) icon.hardlight(0, 0.3f, 0);
		}

		@Override
		public String toString() {
			return Messages.get(DMArmor.class, "teleport_name");
		}

		@Override
		public String desc() {
			return Messages.get(DMArmor.class, "teleport_desc", charge);
		}
	}

	public static class DM_Immunities extends Buff {

		{
			immunities.add( ToxicGas.class );
			immunities.add( Poison.class );
			immunities.add( Bleeding.class );
		}
	}
}