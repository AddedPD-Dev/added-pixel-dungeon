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

package com.shatteredpixel.shatteredpixeldungeon.actors.hero;

import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.watabou.utils.Bundle;

public enum HeroSubClass {

	NONE( null ),
	
	GLADIATOR( "gladiator" ),
	BERSERKER( "berserker" ),
	
	WARLOCK( "warlock" ),
	BATTLEMAGE( "battlemage" ),
	
	ASSASSIN( "assassin" ),
	FREERUNNER( "freerunner" ),
	
	SNIPER( "sniper" ),
	WARDEN( "warden" ),

	// AddedPD : 3rd subclasses for 'basic classes'
	SEALKNIGHT("sealknight"),		// Warrior 3rd subclass : 'shield master', enhanced glyph
	TRANSMUTER("transmuter"),		// Mage 3rd subclass : trickery magic, transmute staff when fully charged or ran out of charge
	BURGLAR("burglar"),			// Rogue 3rd subclass : professional of thief-work, dungeoneering
	SPIRITWALKER("spiritwalker"),	// Huntress 3rd subclass : detect enemy, aoe spirit arrow

	// Cleric : No magic wands, Divine caster ; smite, combat-regen-aura, 'holy enchant'
	CRUSADER("crusader"),			// Antimagic brawler - antimagic smite(blind), arcane armor, adrenaline aura
	SCHOLAR("scholar"),			// Miracle master - greater smite(Aoe), blob of dispell undead, flood of holy water
	REDEEMER("redeemer"),			// Reclaimer - healing smite(for ally), high-cost convert enemy which can level up

	// Dwarf Survivor : 'one-melee-only', Infighter with fist of cyborg
	THUNDERBRINGER("thunderbringer"), // Hit and run, charge stack by attack, blast lightning when stack becomes 3 charges
	MONK("monk"),						// Building 'focus' while moving, dodge all attacks while focused, counter attack
	NECROSMITH("necrosmith");			// Extreact souls, activate own 'Prismatic golem', with tanker form & attacker form

	private String title;
	
	HeroSubClass( String title ) {
		this.title = title;
	}
	
	public String title() {
		return Messages.get(this, title);
	}
	
	public String desc() {
		return Messages.get(this, title+"_desc");
	}
	
	private static final String SUBCLASS	= "subClass";
	
	public void storeInBundle( Bundle bundle ) {
		bundle.put( SUBCLASS, toString() );
	}
	
	public static HeroSubClass restoreInBundle( Bundle bundle ) {
		String value = bundle.getString( SUBCLASS );
		return valueOf( value );
	}
	
}
