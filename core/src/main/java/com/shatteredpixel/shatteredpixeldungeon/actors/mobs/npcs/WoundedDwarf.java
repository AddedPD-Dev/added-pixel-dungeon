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

package com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs;

import com.shatteredpixel.shatteredpixeldungeon.Badges;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.HeroClass;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfHealing;
import com.shatteredpixel.shatteredpixeldungeon.journal.Notes;
import com.shatteredpixel.shatteredpixeldungeon.levels.rooms.Room;
import com.shatteredpixel.shatteredpixeldungeon.levels.rooms.standard.WoundedDwarfRoom;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.WoundedDwarfSprite;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndQuest;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndWoundedDwarf;
import com.watabou.noosa.Game;
import com.watabou.utils.Bundle;
import com.watabou.utils.Callback;

import java.util.ArrayList;

public class WoundedDwarf extends NPC {

	{
		spriteClass = WoundedDwarfSprite.class;
		properties.add(Property.IMMOVABLE);
	}

	private boolean seenBefore = false;

	@Override
	protected boolean act() {

		if (!Quest.given && Dungeon.level.heroFOV[pos]) {
			if (!seenBefore) {
				yell( Messages.get(this, "yell" ) );
			}
			seenBefore = true;
		} else {
			seenBefore = false;
		}
		throwItem();
		return super.act();
	}
	
	@Override
	public int defenseSkill( Char enemy ) {
		return 100_000_000;
	}
	
	@Override
	public void damage( int dmg, Object src ) {
	}
	
	@Override
	public void add( Buff buff ) {
	}
	
	@Override
	public boolean reset() {
		return true;
	}
	
	@Override
	public boolean interact() {
		
		sprite.turnTo( pos, Dungeon.hero.pos );
		if (Quest.given) {
			Item item;
			item = Dungeon.hero.belongings.getItem(PotionOfHealing.class);

			if (item != null) {
				Game.runOnRenderThread(new Callback() {
					@Override
					public void call() {
						GameScene.show( new WndWoundedDwarf( WoundedDwarf.this, item ) );
					}
				});
			} else {
				String msg;
				msg = Messages.get(this, "reminder", Dungeon.hero.givenName());
				Game.runOnRenderThread(new Callback() {
					@Override
					public void call() {
						GameScene.show(new WndQuest(WoundedDwarf.this, msg));
					}
				});
			}
			
		} else {

			String msg1 = "";
			String msg2 = "";
			switch(Dungeon.hero.heroClass){
				case WARRIOR: default:
					msg1 += Messages.get(this, "intro_warrior");
					break;
				case ROGUE:
					msg1 += Messages.get(this, "intro_rogue");
					break;
				case MAGE:
					msg1 += Messages.get(this, "intro_mage");
					break;
				case HUNTRESS:
					msg1 += Messages.get(this, "intro_huntress");
					break;
				case CLERIC:
					msg1 += Messages.get(this, "intro_cleric");
					break;
			}

			msg1 += Messages.get(this, "intro_1");
			msg2 += Messages.get(this, "intro_2");
			final String msg1Final = msg1;
			final String msg2Final = msg2;
			
			Game.runOnRenderThread(new Callback() {
				@Override
				public void call() {
					GameScene.show(new WndQuest(WoundedDwarf.this, msg1Final){
						@Override
						public void hide() {
							super.hide();
							GameScene.show(new WndQuest(WoundedDwarf.this, msg2Final));
						}
					});
				}
			});

			Notes.add( Notes.Landmark.DWARF );
			Quest.given = true;
		}

		return false;
	}
	
	public static class Quest {
		
		private static boolean spawned;
		private static boolean given;
		
		public static void reset() {
			spawned		= false;
			given		= false;
		}
		
		private static final String NODE		= "dwarf";
		private static final String SPAWNED		= "spawned";
		private static final String GIVEN		= "given";
		
		public static void storeInBundle( Bundle bundle ) {
			
			Bundle node = new Bundle();
			
			node.put( SPAWNED, spawned );
			
			if (spawned) {
				node.put( GIVEN, given );
			}
			
			bundle.put( NODE, node );
		}
		
		public static void restoreFromBundle( Bundle bundle ) {

			Bundle node = bundle.getBundle( NODE );
			
			if (!node.isNull() && (spawned = node.getBoolean( SPAWNED ))) {
				given = node.getBoolean( GIVEN );
			} else {
				reset();
			}
		}

		
		public static ArrayList<Room> spawn( ArrayList<Room> rooms) {
			if (!spawned && (Dungeon.depth == 23)
					&& !Badges.isUnlocked(Badges.Badge.UNLOCK_DWARF)
					&& Dungeon.hero.heroClass != HeroClass.DWARF) {
				rooms.add(new WoundedDwarfRoom());

				spawned = true;
				given = false;
			}
			return rooms;
		}
		
		public static void complete() {
			Notes.remove( Notes.Landmark.DWARF );
		}
	}
}
