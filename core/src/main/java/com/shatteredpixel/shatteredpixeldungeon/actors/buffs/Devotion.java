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
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.BlessedWater;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.Blob;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.CermateFire;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.HeroClass;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.HeroSubClass;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.RotHeart;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.RotLasher;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.PrismaticImage;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.Flare;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.effects.Splash;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.CermateFireParticle;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.ElmoParticle;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.ShadowParticle;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.ShaftParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.Armor;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.ClericArmor;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.shatteredpixeldungeon.levels.Level;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.CellSelector;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.HeroSprite;
import com.shatteredpixel.shatteredpixeldungeon.ui.ActionIndicator;
import com.shatteredpixel.shatteredpixeldungeon.ui.AttackIndicator;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.shatteredpixel.shatteredpixeldungeon.ui.Icons;
import com.shatteredpixel.shatteredpixeldungeon.utils.BArray;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndBag;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndOptions;
import com.watabou.glwrap.Blending;
import com.watabou.noosa.Camera;
import com.watabou.noosa.Game;
import com.watabou.noosa.Halo;
import com.watabou.noosa.Image;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Callback;
import com.watabou.utils.GameMath;
import com.watabou.utils.PathFinder;
import com.watabou.utils.PointF;

import static com.shatteredpixel.shatteredpixeldungeon.Dungeon.hero;

public class Devotion extends Buff implements ActionIndicator.Action {

	private int rank = 0;
	private int partialRank = 0;
	private int baptizeUsed = 0;

	private static final String RANK = "rank";
	private static final String RANK_PARTIAL = "partialRank";
	private static final String BAPTIZE_USED = "baptizeUsed";

	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(RANK, rank);
		bundle.put(RANK_PARTIAL, partialRank);
		bundle.put(BAPTIZE_USED, baptizeUsed);
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		rank = bundle.getInt(RANK);
		partialRank = bundle.getInt(RANK_PARTIAL);
		baptizeUsed = bundle.getInt(BAPTIZE_USED);
		if (rank >= 0) ActionIndicator.setAction(this);
	}

	public void onHeroGainExp() {
		if (hero.subClass == HeroSubClass.SCHOLAR) {
			partialRank++;
			if(partialRank >= 4) {
				partialRank = 0;
				rank++;
			}
		}
		rank++;
		ActionIndicator.setAction( this );
		ActionIndicator.updateIcon();
		BuffIndicator.refreshHero();
	}

	public void onLevelUp(){
		rank += 5;
		ActionIndicator.setAction( this );
		ActionIndicator.updateIcon();
		BuffIndicator.refreshHero();
	}

	public void onOther(int point){
		rank += point;
		ActionIndicator.setAction( this );
		ActionIndicator.updateIcon();
		BuffIndicator.refreshHero();
	}

	public void onReset(){
		rank = 0;
		ActionIndicator.setAction( this );
		ActionIndicator.updateIcon();
		BuffIndicator.refreshHero();
	}

	public void invocation(){
		((HeroSprite) hero.sprite).read();
		Invisibility.dispel();
		Sample.INSTANCE.play( Assets.Sounds.LEVELUP );
		new Flare( 6, 32 ).show( hero.sprite, 4f );
		ActionIndicator.setAction( this );
		ActionIndicator.updateIcon();
		BuffIndicator.refreshHero();

		if (hero.hasTalent(Talent.SEER))
			Buff.prolong(hero, Foresight.class, 3 + 7*(1-hero.pointsInTalent(Talent.SEER)));
	}

	public int getrank() {
		return rank;
	}

	@Override
	public int icon() {
		return BuffIndicator.DEVOTION;
	}

	@Override
	public String toString() {
		return Messages.get(this, "name");
	}

	@Override
	public String desc() {
		String desc = Messages.get(this, "desc", rank);
		String miracles = Messages.get(this, "miracles");

		if (hero.subClass == HeroSubClass.CRUSADER) {
			String crusader = Messages.get(this, "crusader");
			return desc + miracles + crusader;

		} if (hero.subClass == HeroSubClass.SCHOLAR) {
			String scholar = Messages.get(this, "scholar");
			return desc + miracles + scholar;

		}
		return desc + miracles;
	}

	@Override
	public Image getIcon() {
		Image icon;

		if (hero.buff(ClericArmor.ClericArmorBuff.class) != null) {
			icon = Icons.get_miracle(Icons.ENLIGHTEN);
		} else if (rank >= 45) {
			icon = Icons.get_miracle(Icons.ENLIGHTEN);
		} else if (rank >= 30) {
			icon = Icons.get_miracle(Icons.ZEALOT);
		} else if (rank >= 15) {
			if (hero.subClass == HeroSubClass.SCHOLAR) {
				if (rank >= 25) {
					icon = Icons.get_miracle(Icons.CREMATE);
				} else if (rank >= 20) {
					icon = Icons.get_miracle(Icons.HOLY_WATER);
				} else {
					icon = Icons.get_miracle(Icons.SMITE_AOE);
				}
			} else icon = Icons.get_miracle(Icons.SMITE);
		} else icon = Icons.get(HeroClass.CLERIC);

		return icon;
	}

	@Override
	public void doAction() {
		if (hero.subClass == HeroSubClass.NONE || hero.subClass == HeroSubClass.CRUSADER) {
			if (rank >= 45 || hero.buff(ClericArmor.ClericArmorBuff.class) != null) {
				// rank 45+ : smite, zealot, enlighten
				GameScene.show(
						new WndOptions(Messages.get(this, "name"), Messages.get(this, "prompt", rank),
								Messages.get(this, "choose_smite"), Messages.get(this, "choose_zealot"),
								Messages.get(this, "choose_enlighten"), Messages.get(this, "cancel")) {
							@Override
							protected void onSelect(int index) {
								if (index == 0) { GameScene.selectCell(smite); }
								if (index == 1) { doZealot(); }
								if (index == 2) { doEnlighten(); }
								if (index == 3) { }}});

			} else if (rank >= 30) { // rank 30-44 : smite, zealot
				GameScene.show(
						new WndOptions(Messages.get(this, "name"), Messages.get(this, "prompt", rank),
								Messages.get(this, "choose_smite"), Messages.get(this, "choose_zealot"),
								Messages.get(this, "cancel")) {
							@Override
							protected void onSelect(int index) {
								if (index == 0) { GameScene.selectCell(smite); }
								if (index == 1) { doZealot(); }
								if (index == 2) { }}});

			} else if (rank >= 15) { // rank 15-29 : smite
				GameScene.selectCell(smite);
			} else { // rank 0-14 : none
				GLog.w(Messages.get(this, "need_more_rank"));
			}
		}
		else if (hero.subClass == HeroSubClass.SCHOLAR) {
			if (rank >= 45 || hero.buff(ClericArmor.ClericArmorBuff.class) != null) {
				// rank 45+ : smite, holy_water, cremate, zealot, enlighten
				GameScene.show(
						new WndOptions(Messages.get(this, "name"), Messages.get(this, "prompt", rank),
								Messages.get(this, "choose_smite"), Messages.get(this, "choose_holy_water"),
								Messages.get(this, "choose_cremate"), Messages.get(this, "choose_zealot"),
								Messages.get(this, "choose_enlighten"), Messages.get(this, "cancel")) {
							@Override
							protected void onSelect(int index) {
								if (index == 0) { GameScene.selectCell(smite); }
								if (index == 1) { doHolyWater(); } if (index == 2) { doCermate(); }
								if (index == 3) { doZealot(); } if (index == 4) { doEnlighten(); }
								if (index == 5) { }}});

			} else if (rank >= 30) { // rank 30-44 : smite, holy_water, cremate, zealot
				GameScene.show(
						new WndOptions(Messages.get(this, "name"), Messages.get(this, "prompt", rank),
								Messages.get(this, "choose_smite"), Messages.get(this, "choose_holy_water"),
								Messages.get(this, "choose_cremate"), Messages.get(this, "choose_zealot"),
								Messages.get(this, "cancel")) {
							@Override
							protected void onSelect(int index) {
								if (index == 0) { GameScene.selectCell(smite); }
								if (index == 1) { doHolyWater(); } if (index == 2) { doCermate(); }
								if (index == 3) { doZealot(); } if (index == 4) { }}});

			} else if (rank >= 25) { // rank 25-29 : smite, holy_water, cremate
				GameScene.show(
						new WndOptions(Messages.get(this, "name"), Messages.get(this, "prompt", rank),
								Messages.get(this, "choose_smite"), Messages.get(this, "choose_holy_water"),
								Messages.get(this, "choose_cremate"),Messages.get(this, "cancel")) {
							@Override
							protected void onSelect(int index) {
								if (index == 0) { GameScene.selectCell(smite); }
								if (index == 1) { doHolyWater(); } if (index == 2) { doCermate(); }
								if (index == 3) { }}});

			} else if (rank >= 20) { // rank 20-24 : smite, holy_water
				GameScene.show(
						new WndOptions(Messages.get(this, "name"), Messages.get(this, "prompt", rank),
								Messages.get(this, "choose_smite"), Messages.get(this, "choose_holy_water"),
								Messages.get(this, "choose_cremate"), Messages.get(this, "cancel")) {
							@Override
							protected void onSelect(int index) {
								if (index == 0) { GameScene.selectCell(smite); }
								if (index == 1) { doHolyWater(); } if (index == 2) { }}});

			} else if (rank >= 15) { // rank 15-19 : smite(aoe)
				GameScene.selectCell(smite);
			} else { // rank 0-14 : none
				GLog.w(Messages.get(this, "need_more_rank"));
			}
		}
		else {
			if (rank >= 45) { // rank 45-59 : smite, zealot, enlighten
				GameScene.show(
						new WndOptions(Messages.get(this, "name"), Messages.get(this, "prompt", rank),
								Messages.get(this, "choose_smite"), Messages.get(this, "choose_zealot"),
								Messages.get(this, "choose_enlighten"), Messages.get(this, "cancel")) {
							@Override
							protected void onSelect(int index) {
								if (index == 0) { GameScene.selectCell(smite); }
								if (index == 1) { doZealot(); }
								if (index == 2) { doEnlighten(); }
								if (index == 3) { }}});

			} else if (rank >= 30) { // rank 30-44 : smite, zealot
				GameScene.show(
						new WndOptions(Messages.get(this, "name"), Messages.get(this, "prompt", rank),
								Messages.get(this, "choose_smite"), Messages.get(this, "choose_zealot"),
								Messages.get(this, "cancel")) {
							@Override
							protected void onSelect(int index) {
								if (index == 0) { GameScene.selectCell(smite); }
								if (index == 1) { doZealot(); }
								if (index == 2) { }}});

			} else if (rank >= 15) { // rank 15-29 : smite
				GameScene.selectCell(smite);
			} else { // rank 0-14 : none
				GLog.w(Messages.get(this, "need_more_rank"));
			}
		}

	}

	public CellSelector.Listener smite = new CellSelector.Listener() {
		@Override
		public void onSelect(Integer cell) {
			if (cell == null) return;
			final Char enemy = Actor.findChar( cell );
			if (enemy == null || !Dungeon.level.heroFOV[cell] || enemy == hero){
				GLog.w( Messages.get(Devotion.class, "smite_bad_target") );
				return;
			} else {
				if (cell != null) {
					hero.sprite.attack(cell, new Callback() {
						@Override
						public void call() {
							doSmite(enemy);
						}
					});
				} else return;
			}
		}

		private void doSmite(final Char enemy){
			AttackIndicator.target(enemy);

			int dmg = 15 + 5*(hero.lvl/2);

			if (enemy.properties().contains(Char.Property.DEMONIC)
					|| enemy.properties().contains(Char.Property.UNDEAD)) {
				dmg = Math.round(dmg*1.333f);
			}

			if (enemy.properties().contains(Char.Property.BOSS)) {
				// prvents "SMITE! HA HA I WON" situation, by NEVER one-kill bosses
				if (dmg >= enemy.HT/10)
    				dmg = enemy.HT/10;

				GLog.w( Messages.capitalize(Messages.get(Devotion.class, "smite_boss", enemy.name())) );
			}

			switch (hero.subClass) {
				case NONE: default:
					enemy.damage( dmg, this );
					Camera.main.shake( GameMath.gate( 1, 10, 5), 0.3f );
					break;
				case CRUSADER:
					if (enemy.properties().contains(Char.Property.SPELLCASTER)) {
						Blindness blindness = enemy.buff(Blindness.class);
						if (blindness == null) {
							Buff.prolong(enemy, Blindness.class, 10f);
						}
						enemy.damage( dmg, this );
					} else { enemy.damage( dmg, this );
						Camera.main.shake( GameMath.gate( 1, 10, 5), 0.3f );}
					break;
				case SCHOLAR:
					for (int i : PathFinder.NEIGHBOURS8) {
						enemy.damage( dmg, this );
						CellEmitter.get(i).start( ShaftParticle.FACTORY, 0.25f, 8 );
						Char ch = Actor.findChar(enemy.pos + i);
						if (ch != null) {
							// only harm enemy(no self damage, no friendly fire)
							if (ch.alignment != Char.Alignment.ALLY || ch != Dungeon.hero) {
								ch.damage( dmg, this );
							}
						}
					}
					Camera.main.shake( GameMath.gate( 1, 10, 5), 0.3f );
					break;
			}

			Sample.INSTANCE.play(Assets.Sounds.BLAST);
			CellEmitter.get(enemy.pos).start( ShaftParticle.FACTORY, 0.025f, 8 );

			if (hero.buff(ClericArmor.ClericArmorBuff.class) != null){
				hero.buff(ClericArmor.ClericArmorBuff.class).detach();
			} else rank -= 15;

			if (!enemy.isAlive()){
				GLog.i( Messages.capitalize(Messages.get(Char.class, "defeat", enemy.name())) );
			}

			Invisibility.dispel();
			Sample.INSTANCE.play( Assets.Sounds.LEVELUP );
			new Flare( 6, 32 ).show( hero.sprite, 4f );

			if (hero.hasTalent(Talent.SEER))
				Buff.prolong(hero, Foresight.class, 3 + 7*(1-hero.pointsInTalent(Talent.SEER)));

			Devotion devotion = Dungeon.hero.buff(Devotion.class);
			ActionIndicator.setAction(devotion);
			ActionIndicator.updateIcon();
			BuffIndicator.refreshHero();
			hero.spendAndNext(Actor.TICK);
		}

		@Override
		public String prompt () {
			int dmg = 10 + 5*(hero.lvl/2);
			switch (hero.subClass) {
				case NONE:
				default: return Messages.get(Devotion.class, "smite_prompt", dmg, Math.round(dmg*1.333f));
				case CRUSADER: return Messages.get(Devotion.class, "smite_prompt_crusader", dmg, Math.round(dmg*1.333f));
				case SCHOLAR: return Messages.get(Devotion.class, "smite_prompt_scholar", dmg, Math.round(dmg*1.333f));
			}
		}
	};

	public static class ZealotBuff extends Buff {
		{
			type = buffType.POSITIVE;
		}
		protected int heal = 0;
		private static final String HEAL = "heal";

		int danger = 0;

		@Override
		public void storeInBundle( Bundle bundle ) {
			super.storeInBundle( bundle );
			bundle.put( HEAL, heal );

		}
		@Override
		public void restoreFromBundle( Bundle bundle ) {
			super.restoreFromBundle( bundle );
			heal = bundle.getInt( HEAL );
		}

		@Override
		public boolean attachTo( Char target ) {
			if (super.attachTo( target )) {
				postpone( TICK );
				return true;
			} else { return false; }
		}

		public void prolong( int heal ) { this.heal += heal; }

		@Override
		public int icon() { return BuffIndicator.ZEALOT; }

		@Override
		public String toString() { return Messages.get(Devotion.class, "zealot_buff_name"); }

		@Override
		public boolean act() {
			danger = 0;	// recalculating...

			for (Buff buff : target.buffs()) {
				if (target.buff(Levitation.class) != null
						&& (target.buff(Burning.class) != null || target.buff(Ooze.class) != null)) {
					danger++;	// the deadly situation, familiar to us
				}
				else if (buff.type == Buff.buffType.NEGATIVE) {
					if (buff instanceof Paralysis || buff instanceof Frost || buff instanceof Roots) {
						danger ++;	// makes any situation dangerous
					} danger++;		// debuffs, but not so critical
				}
				else if (buff.type == buffType.POSITIVE) {
					if (buff instanceof Healing || buff instanceof Invisibility
							|| (buff instanceof ShieldBuff && target.shielding() >= target.HT/2)) {
						danger--;	// good enough to save your life any situation
					}
				}
			}

			if (target.HP <= target.HT/2) {
				danger++;	// of course, low HP means dangerous
				if (target.HP <= target.HT/4) {
					danger++;	// it seems things getting worse...
				}
			}

			int allies = 0;	// We are constant allies, can still fighting and not critically injured
			int enemies = 0;
			for (Mob mob : Dungeon.level.mobs.toArray( new Mob[0] )) {
				if (Dungeon.level.heroFOV[mob.pos]) {
					if (mob.alignment == Char.Alignment.ALLY && !mob.isCharmedBy(hero)
							&& mob.buff(Frost.class) == null && mob.buff(Paralysis.class) == null
							&& mob.buff(Amok.class) == null && mob.HP >= mob.HT/2) {
						allies++;
					} else {
						if (mob.state != mob.SLEEPING) {
							int gap = (mob.maxLvl+2) - hero.lvl;
							if (gap >= 0) {
								enemies++; // normal enemy
							} else if (gap >= -2 && mob.HP >= mob.HT/2) {
								enemies++; // weak enemy, but still on combat
							} else {
								// these are too weak to threat
							}

							if (mob.properties().contains(Char.Property.BOSS)) {
								enemies += 2;
							}

							if (mob.properties().contains(Char.Property.MINIBOSS)) {
								if (mob instanceof RotLasher || mob instanceof RotHeart) {
									if (mob.distance(target) == 1) {
										enemies++;
									}
								} else enemies++;
							}
						}
					}
				}
			}

			// Final calculation of danger level

			if (enemies == 0 && danger >= 2) { danger = 2; } // noncombat
			if (allies >= enemies && danger >= 4) { danger = 4; } // many allies means enough safety

			if (danger >= 8) { danger = 8; }	// prevents 'undying hero' situation

			if (target.isAlive() || (target instanceof PrismaticImage && target.HP >= 0)) {
				// prevents crash from PrisImage death-timer

				if (danger > 0) {
					int actualHeal = 0;

					if (target.HP+danger <= target.HT) {
						heal -= danger;
						actualHeal = danger;
					} else {
						heal -= target.HT-target.HP;
						actualHeal = target.HT-target.HP;
					}

					target.HP = Math.min(target.HT, target.HP+danger);

					if (actualHeal > 0) {
						target.sprite.showStatus(CharSprite.POSITIVE, Integer.toString(actualHeal));
						CellEmitter.get(target.pos).burst(Speck.factory(Speck.LIGHT), 4 + actualHeal);
					}
				}

				if (enemies > 0 && hero.subClass == HeroSubClass.CRUSADER) {
					// crusader special : additional magic resistance...
					if (target.buff(ArcaneArmor.class) == null) {
						Buff.affect(target, ArcaneArmor.class).set(5 + hero.lvl / 2, danger / 2 + 1);
					}
					// ...and adrenaline boost depend on danger
					if (danger >= 3) {
						if (target.buff(Adrenaline.class) == null && target != hero) {
							Buff.affect(target, Adrenaline.class, danger*10);
						}
						if (target.buff(AdrenalineSurge.class) == null && target == hero) {
							Buff.append(target, AdrenalineSurge.class).reset(1, (float) (danger*5));
						}
					}
				}
				spend(TICK);

				if (heal <= 0) {
					detach();
				}
			} else {
				detach();
			}
			return true;
		}

		@Override
		public String desc() { return Messages.get(Devotion.class, "zealot_buff_desc", heal, Math.max(danger, 0), Math.max(danger, 0)); }
	}

	private void doZealot(){

		int amount = 8 + hero.HT/5 + 4*hero.lvl;

		for (Mob mob : Dungeon.level.mobs.toArray( new Mob[0] )) {
			if (Dungeon.level.heroFOV[mob.pos]) {
				if (mob.alignment == Char.Alignment.ALLY && !mob.isCharmedBy(hero)) {
					ZealotBuff zealotBuff = Buff.affect( mob, ZealotBuff.class );
					zealotBuff.prolong( amount );
				}
			}
		}
		ZealotBuff zealotBuff = Buff.affect( hero, ZealotBuff.class );
		zealotBuff.prolong( amount );

		if (hero.buff(ClericArmor.ClericArmorBuff.class) != null){
			hero.buff(ClericArmor.ClericArmorBuff.class).detach();
		} else rank -= 30;
		invocation();
		hero.spendAndNext(Actor.TICK);
	}

	public WndBag doEnlighten() {
		return GameScene.selectItem( itemSelector, WndBag.Mode.ENCHANTABLE, Messages.get(Devotion.class, "which_enlighten"));
	}

	private final WndBag.Listener itemSelector = new WndBag.Listener() {
		@Override
		public void onSelect( Item item ) {
			if (item != null) {
				if (item.cursed) {
					item.cursedKnown = true;
					item.cursed = false;

					if (item instanceof Weapon && ((Weapon) item).hasCurseEnchant())
						((Weapon) item).enchant(null);
					if (item instanceof Armor && ((Armor) item).hasCurseGlyph())
						((Armor) item).inscribe(null);

					hero.sprite.emitter().start(ShadowParticle.UP, 0.05f, 10);
				}

				item.enlightened = true;
				GLog.p( Messages.get(Devotion.class, "enlighten"));

				if (hero.buff(ClericArmor.ClericArmorBuff.class) != null){
					hero.buff(ClericArmor.ClericArmorBuff.class).detach();
				} else rank -= 45;
				invocation();
				hero.spendAndNext(Actor.TICK);
			}
		}
	};

	private void doHolyWater(){

		int amount = 6*hero.lvl;

		PathFinder.buildDistanceMap( hero.pos, BArray.not( Dungeon.level.solid, null ), 2 );
		for (int i = 0; i < PathFinder.distance.length; i++) {
			if (PathFinder.distance[i] < Integer.MAX_VALUE) {
				int terr = Dungeon.level.map[i];
				if (terr == Terrain.EMPTY || terr == Terrain.GRASS ||
						terr == Terrain.EMBERS || terr == Terrain.EMPTY_SP ||
						terr == Terrain.HIGH_GRASS || terr == Terrain.FURROWED_GRASS
						|| terr == Terrain.EMPTY_DECO) {
					Level.set(i, Terrain.WATER);
					Splash.at(i, 0x00AAFF, 10);
					GameScene.updateMap(i);
				} else if (terr == Terrain.SECRET_TRAP || terr == Terrain.TRAP || terr == Terrain.INACTIVE_TRAP) {
					Level.set(i, Terrain.WATER);
					CellEmitter.get(i).burst(Speck.factory(Speck.STEAM), 6);
					Dungeon.level.traps.remove(i);
					Splash.at(i, 0x00AAFF, 10);
					GameScene.updateMap(i);
				}

				int bless = Dungeon.level.map[i];
				if (bless == Terrain.WATER) {
					CellEmitter.get(i).burst(Speck.factory( Speck.BUBBLE ), 2);
					GameScene.add(Blob.seed(i, amount, BlessedWater.class));
					Sample.INSTANCE.play( Assets.Sounds.DRINK );
				}
			}
		}

		if (hero.buff(ClericArmor.ClericArmorBuff.class) != null){
			hero.buff(ClericArmor.ClericArmorBuff.class).detach();
		} else rank -= 20;
		invocation();
		hero.spendAndNext(Actor.TICK);
	}

	private void doCermate(){

		int amount = 3*hero.lvl;

		PathFinder.buildDistanceMap( hero.pos, BArray.not( Dungeon.level.solid, null ), 2 );
		for (int i = 0; i < PathFinder.distance.length; i++) {
			if (PathFinder.distance[i] < Integer.MAX_VALUE) {
				if (Dungeon.level.pit[i])
					GameScene.add(Blob.seed(i, 2, CermateFire.class));
				else
					GameScene.add(Blob.seed(i, amount+1, CermateFire.class));
				CellEmitter.get(i).burst(CermateFireParticle.FACTORY, 5);

				Char ch = Actor.findChar( i );
				if (ch != null && ch.alignment != hero.alignment
						&& ch.properties().contains(Char.Property.UNDEAD)) {
					if (ch.isAlive()) {
						if (ch.HP <= 4) {
							ch.damage(ch.HP, this);
						} else { ch.damage(ch.HT / 4, CermateFire.class); }
						ch.sprite.emitter().burst( ElmoParticle.FACTORY, 10 );
					}
				}
			}
		}
		Sample.INSTANCE.play(Assets.Sounds.BURNING);

		if (hero.buff(ClericArmor.ClericArmorBuff.class) != null){
			hero.buff(ClericArmor.ClericArmorBuff.class).detach();
		} else rank -= 25;

		invocation();
		hero.spendAndNext(Actor.TICK);
	}
}