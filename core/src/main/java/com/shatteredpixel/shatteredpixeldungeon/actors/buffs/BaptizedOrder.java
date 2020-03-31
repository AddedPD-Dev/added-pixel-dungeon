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
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.ShaftParticle;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.CellSelector;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.ui.ActionIndicator;
import com.shatteredpixel.shatteredpixeldungeon.ui.ActionIndicatorSub;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.shatteredpixel.shatteredpixeldungeon.ui.Icons;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndOptions;
import com.watabou.noosa.Game;
import com.watabou.noosa.Image;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Callback;

import static com.shatteredpixel.shatteredpixeldungeon.Dungeon.hero;

public class BaptizedOrder extends Buff implements ActionIndicatorSub.Action {

	{
		type = buffType.POSITIVE;
	}

	private Mob mob = null;

	public int BdefendingPos = -1;
	public boolean BmovingToDefendPos = false;

	private static final String DPOS = "BdefendingPos";
	private static final String MPOS = "BmovingToDefendPos";

	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(DPOS, BdefendingPos);
		bundle.put(MPOS, BmovingToDefendPos);
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		BdefendingPos = bundle.getInt(DPOS);
		BmovingToDefendPos = bundle.getBoolean(MPOS);
	}

	public void clearDefensingPos(){
		BdefendingPos = -1;
		BmovingToDefendPos = false;
	}

	@Override
	public boolean attachTo( Char target ) {
		ActionIndicatorSub.setAction(this);
		ActionIndicatorSub.updateIcon();
		BuffIndicator.refreshHero();
		return super.attachTo( target );
	}

	@Override
	public boolean act() {
		if (target.isAlive()) {
			boolean found = false;
			for (Mob m : Dungeon.level.mobs.toArray(new Mob[0])){
				if (m.isBaptized()){
					found = true;
					mob = m;
					ActionIndicatorSub.setAction(this);
					ActionIndicatorSub.updateIcon();
					BuffIndicator.refreshHero();
				}
			}

			if (!found) {
				ActionIndicatorSub.clearAction(this);
				detach();
			}
			spend(TICK);
			return true;
		} else {
			ActionIndicatorSub.clearAction(this);
			detach();
			return true;
		}
	}

	@Override
	public Image getIcon() {
		Image icon;
		icon = Icons.TARGET.get();
		icon.tint(0xFFFFFF);

		return icon;
	}

	@Override
	public void doAction() {
		Char ally = (Char)mob;
		Game.runOnRenderThread(new Callback() {
			@Override
			public void call() {
				GameScene.show(new WndOptions(
						Messages.get(Devotion.class, "interact_title", mob.name),
						Messages.get(Devotion.class, "interact_prompt"),
						Messages.get(Devotion.class, "interact_direct"),
						Messages.get(Devotion.class, "interact_dismiss"),
						Messages.get(Devotion.class, "interact_cancel")) {
					@Override
					protected void onSelect(int index) {
						if (index == 0) {
							GameScene.selectCell(baptizedDirector);
						}
						if (index == 1) {
							GLog.p(Messages.get(Devotion.class, "interact_dismiss_on", ally.name));
							CellEmitter.get(ally.pos).start(ShaftParticle.FACTORY, 0.3f, 4);
							ally.destroy();
							ally.sprite.kill();

							Devotion devotion = Dungeon.hero.buff(Devotion.class);
							devotion.Baptized_canUse();
							devotion.onOther(60);
							ActionIndicator.setAction(devotion);
							ActionIndicator.updateIcon();
							BuffIndicator.refreshHero();

							Dungeon.hero.spendAndNext(Actor.TICK);
						}
					}
				});
			}
		});
	}

	public CellSelector.Listener baptizedDirector = new CellSelector.Listener(){

		@Override
		public void onSelect(Integer cell) {
			if (cell == null) return;

			Sample.INSTANCE.play( Assets.SND_READ );
			CellEmitter.center(mob.pos).start( Speck.factory( Speck.CALM ), 0.3f, 3 );

			if (!Dungeon.level.heroFOV[cell]
					|| Actor.findChar(cell) == null
					|| (Actor.findChar(cell) != hero && Actor.findChar(cell).alignment != Char.Alignment.ENEMY)){
				mob.aggro(null);
				mob.state = mob.WANDERING;
				BdefendingPos = cell;
				BmovingToDefendPos = true;
				CellEmitter.center(cell).start( Speck.factory( Speck.CALM ), 0.3f, 3 );
				return;
			}

			if (mob.fieldOfView == null || mob.fieldOfView.length != Dungeon.level.length()){
				mob.fieldOfView = new boolean[Dungeon.level.length()];
			}
			Dungeon.level.updateFieldOfView( mob, mob.fieldOfView );

			if (Actor.findChar(cell) == hero){
				mob.aggro(null);
				mob.state = mob.WANDERING;
				BdefendingPos = -1;
				BmovingToDefendPos = false;
				CellEmitter.center(cell).start( Speck.factory( Speck.CALM ), 0.3f, 3 );

			} else if (Actor.findChar(cell).alignment == Char.Alignment.ENEMY){
				mob.aggro(Actor.findChar(cell));
				BmovingToDefendPos = false;
				mob.isTargeting(Actor.findChar(cell));
				CellEmitter.center(cell).start( Speck.factory( Speck.CALM ), 0.3f, 3 );
			}
		}

		@Override
		public String prompt() {
			return  "\"" + Messages.get(Devotion.class, "direct_prompt") + "\"";
		}
	};
}
