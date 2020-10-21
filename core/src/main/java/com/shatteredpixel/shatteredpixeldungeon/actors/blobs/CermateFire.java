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

package com.shatteredpixel.shatteredpixeldungeon.actors.blobs;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Devotion;
import com.shatteredpixel.shatteredpixeldungeon.effects.BlobEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.CermateFireParticle;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.ElmoParticle;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;

import static com.shatteredpixel.shatteredpixeldungeon.Dungeon.hero;

public class CermateFire extends Blob {

	@Override
	protected void evolve() {

		boolean[] s = Dungeon.level.secret;
		int cell;
		int fire;

		Blob blob = Dungeon.level.blobs.get( Blob.class );

		boolean observe = false;

		for (int i = area.left-1; i <= area.right; i++) {
			for (int j = area.top-1; j <= area.bottom; j++) {
				cell = i + j*Dungeon.level.width();
				if (cur[cell] > 0) {

					if (blob != null && blob.volume > 0 && blob.cur[cell] > 0){
						blob.clear(cell);
						off[cell] = cur[cell] = 0;
						if (Dungeon.level.heroFOV[cell]) {
							CellEmitter.get( cell ).burst( Speck.factory( Speck.DISCOVER ), 2 );
						}
						continue;
					}

					burnUndead( cell );

					fire = cur[cell] - 1;
					if (fire <= 0 && s[cell]) {

						CellEmitter.get( cell ).start( Speck.factory( Speck.DISCOVER ), 0.1f, 4 );

						observe = true;
						GameScene.updateMap( cell );

					}

				} else {
					fire = 0;
				}
				volume += (off[cell] = fire);
			}
		}

		if (observe) {
			Dungeon.observe();
		}
	}

	public void burnUndead( int pos ) {
		Char ch = Actor.findChar( pos );
		if (ch != null && ch.alignment != hero.alignment
				&& ch.properties().contains(Char.Property.UNDEAD)) {
			if (ch.isAlive()) {
				if (ch.HP <= 4) {
					ch.damage(ch.HP, this);
				} else { ch.damage(ch.HT / 4, this); }
				ch.sprite.emitter().burst( ElmoParticle.FACTORY, 10 );
			}
		}
	}

	@Override
	public void use( BlobEmitter emitter ) {
		super.use( emitter );
		emitter.pour( CermateFireParticle.FACTORY, 0.05f );
	}

	@Override
	public String tileDesc() {
		return Messages.get(Devotion.class, "cermate_blob");
	}
}