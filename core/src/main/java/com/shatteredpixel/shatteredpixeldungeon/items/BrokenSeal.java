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

package com.shatteredpixel.shatteredpixeldungeon.items;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.ShieldBuff;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.HeroSubClass;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.Armor;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndBag;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndItem;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndOptions;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;

import java.util.ArrayList;

public class BrokenSeal extends Item {

	public static final String AC_AFFIX = "AFFIX";

	//only to be used from the quickslot, for tutorial purposes mostly.
	public static final String AC_INFO = "INFO_WINDOW";

	// AddedPD : for sealknight's glyph erasing
	public static final String AC_ERASE = "ERASE";

	{
		image = ItemSpriteSheet.SEAL;

		cursedKnown = levelKnown = true;
		unique = true;
		bones = false;

		defaultAction = AC_INFO;
	}

	public Armor.Glyph glyph;

	private static final String GLYPH			= "glyph";
	private static final String LEVEL			= "sealLevel";

	@Override
	public void storeInBundle( Bundle bundle ) {
		bundle.put(GLYPH, glyph);
		bundle.put(LEVEL, super.level());
	}

	@Override
	public void restoreFromBundle( Bundle bundle ) {
		getGlyph((Armor.Glyph) bundle.get(GLYPH));
		this.level(bundle.getInt(LEVEL));
	}

	public void getGlyph(Armor.Glyph G) {
		glyph = G;
	}

	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions =  super.actions(hero);
		actions.add(AC_AFFIX);
		if (hero.subClass == HeroSubClass.SEALKNIGHT && this.glyph != null) {
			actions.add(AC_ERASE);
		}
		return actions;
	}

	@Override
	public void execute(Hero hero, String action) {

		super.execute(hero, action);

		if (action.equals(AC_AFFIX)){
			curItem = this;
			GameScene.selectItem(armorSelector, WndBag.Mode.ARMOR, Messages.get(this, "prompt"));
		} else if (action.equals(AC_INFO)) {
			GameScene.show(new WndItem(null, this, true));
		} else if (action.equals(AC_ERASE)) {
			GameScene.show(new WndOptions(Messages.get(BrokenSeal.class, "sealknight_glyph"),
					Messages.get(BrokenSeal.class, "sealknight_sure_erase", glyph.name()),
					Messages.get(BrokenSeal.class, "sealknight_yes"),
					Messages.get(BrokenSeal.class, "sealknight_no")) {
				@Override
				protected void onSelect(int index) { if (index == 0) { glyph = null; } } });
		}
	}

	@Override
	public ItemSprite.Glowing glowing() {
		if (glyph != null) { return glyph.glowing(); }
		else return null;
	}

	@Override
	public String name() {
		if (glyph != null) { return glyph.name( super.name() ); }
		else return super.name();
	}

	@Override
	//scroll of upgrade can be used directly once, same as upgrading armor the seal is affixed to then removing it.
	public boolean isUpgradable() {
		return level() == 0;
	}

	protected static WndBag.Listener armorSelector = new WndBag.Listener() {
		@Override
		public void onSelect( Item item ) {
			if (item != null && item instanceof Armor) {
				Armor armor = (Armor)item;
				if (!armor.levelKnown){
					GLog.w(Messages.get(BrokenSeal.class, "unknown_armor"));
				} else if (armor.cursed || armor.level() < 0){
					GLog.w(Messages.get(BrokenSeal.class, "degraded_armor"));
				} else {
					GLog.p(Messages.get(BrokenSeal.class, "affix"));
					Dungeon.hero.sprite.operate(Dungeon.hero.pos);
					Sample.INSTANCE.play(Assets.SND_UNLOCK);
					armor.affixSeal((BrokenSeal)curItem);
					curItem.detach(Dungeon.hero.belongings.backpack);
				}
			}
		}
	};

	public static class WarriorShield extends ShieldBuff {

		private Armor armor;
		private float partialShield;

		@Override
		public synchronized boolean act() {
			if (shielding() < maxShield()) {
				partialShield += 1/30f;
				// AddedPD : for sealknight, warriror's 3rd subclass
				if (Dungeon.hero.subClass == HeroSubClass.SEALKNIGHT) {
					partialShield += 1/30f;
					// just regen shield twice, as doubled!
				}
			}
			
			while (partialShield >= 1){
				incShield();
				partialShield--;
			}
			
			if (shielding() <= 0 && maxShield() <= 0){
				detach();
			}
			
			spend(TICK);
			return true;
		}
		
		public synchronized void supercharge(int maxShield){
			if (maxShield > shielding()){
				setShield(maxShield);
			}
		}

		public synchronized void setArmor(Armor arm){
			armor = arm;
		}

		public synchronized int maxShield() {
			if (armor != null && armor.isEquipped((Hero)target)) {
				return 1 + armor.tier + armor.level();
			} else {
				return 0;
			}
		}
		
		@Override
		//logic edited slightly as buff should not detach
		public int absorbDamage(int dmg) {
			if (shielding() >= dmg){
				decShield(dmg);
				dmg = 0;
			} else {
				dmg -= shielding();
				setShield(0);
			}
			return dmg;
		}
	}
}
