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

package com.shatteredpixel.shatteredpixeldungeon.sprites;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.Necrogolem;
import com.shatteredpixel.shatteredpixeldungeon.effects.MagicMissile;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.ElmoParticle;
import com.watabou.noosa.TextureFilm;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;

public class NecroGolemSprite extends MobSprite {

	public NecroGolemSprite() {
		super();

		texture( Assets.NECROGOLEM );
		transformGolem( 1 ); // 1 = guardian mode, 0 = sentry mode
		idle();
	}

	@Override
	public void link( Char ch ) {
		super.link( ch );
		add( State.NECROTIC );
		transformGolem( ((Necrogolem)ch).golemTier);
	}

	public void transformGolem(int tier ) {

	    if ( tier == 1 ) {
            texture( Assets.NECROGOLEM );
            TextureFilm frames = new TextureFilm( texture, 16, 16 );

            idle = new Animation(4, true);
            idle.frames(frames, 0, 1);

            run = new Animation(12, true);
            run.frames(frames, 2, 3, 4, 5);

            attack = new Animation(10, false);
            attack.frames(frames, 6, 7, 8);

            die = new Animation(15, false);
            die.frames(frames, 9, 10, 11, 12, 13);

            play(idle);

        } else if ( tier == 0 ) {
            texture( Assets.NECROGOLEM );
            TextureFilm frames = new TextureFilm( texture, 16, 16 );

            idle = new Animation( 4, true );
            idle.frames( frames, 16, 17 );

            run = new Animation( 12, true );
            run.frames( frames, 18, 19, 20, 21 );

            attack = new Animation( 10, false );
            attack.frames( frames, 22, 23, 24 );

            die = new Animation( 15, false );
            die.frames( frames, 25, 26, 27, 28, 29 );

            play( idle );

        }
	}

	@Override
	public int blood() {
		return 0x22EE66;
	}

	private int posToShoot;

	@Override
	public void attack( int cell ) {
		if (((Necrogolem)ch).golemTier == 0) {
			posToShoot = cell;
		}
		super.attack( cell );
	}

	@Override
	public void onComplete( Animation anim ) {
		if (anim == attack && ((Necrogolem)ch).golemTier == 0) {
			Sample.INSTANCE.play( Assets.SND_ZAP );
			MagicMissile.boltFromChar( parent,
					MagicMissile.ELMO,
					this,
					posToShoot,
					new Callback() {
						@Override
						public void call() {
							ch.onAttackComplete();
						}
					} );

			idle();

		} else if (anim == die) {
			emitter().burst( ElmoParticle.FACTORY, 3 );
		} else super.onComplete( anim );
	}
}
