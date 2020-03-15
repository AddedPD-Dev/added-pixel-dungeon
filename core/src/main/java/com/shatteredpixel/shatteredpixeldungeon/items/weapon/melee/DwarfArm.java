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

package com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.DwarfThunder;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Haste;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.HeroSubClass;
import com.shatteredpixel.shatteredpixeldungeon.effects.Lightning;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.SparkParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfRecharging;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.utils.BArray;
import com.watabou.noosa.Camera;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.PathFinder;

import java.util.ArrayList;

public class DwarfArm extends MeleeWeapon {

    {
        image = ItemSpriteSheet.DWARFARM;

        tier = 1;
        DLY = 0.5f; //2x speed

        bones = false;
    }

    @Override
    public ArrayList<String> actions(Hero hero) {
        ArrayList<String> actions = super.actions( hero );
        actions.remove(AC_UNEQUIP);
        actions.remove(AC_THROW);
        actions.remove(AC_DROP);
        return actions;
    }

    @Override
    public int proc(Char attacker, Char defender, int damage) {
        if (attacker instanceof Hero && ((Hero)attacker).subClass == HeroSubClass.THUNDERBRINGER) {

            int shocked = 0;

            Buff.affect(attacker, DwarfThunder.class).set();

            if (Dungeon.hero.buff(DwarfThunder.class).level() >= 4) {
                for (int i : PathFinder.NEIGHBOURS8) {
                    Char ch = Actor.findChar(attacker.pos + i);
                    if (ch != null) {
                        // only harm enemy(no self damage, no friendly fire)
                        if (ch.alignment != Dungeon.hero.alignment) {
                            shocked++;
                            thunder(this, attacker, ch, damage);
                            // yes, this means more enemy = more shocking. DESTRUCTIVE!
                        }
                    }
                }
                if (attacker.buff(Haste.class) == null && shocked >= 3) {
                    // shock 3+ enemies gain haste
                    Buff.prolong(attacker, Haste.class, 3f);
                    ScrollOfRecharging.charge((Hero)attacker);
                }
                Camera.main.shake(2, 0.3f);
                Dungeon.hero.buff(DwarfThunder.class).reset();
            }
        }
        return super.proc(attacker, defender, damage);
    }

    public int thunder( Weapon weapon, Char attacker, Char defender, int damage ) {
    // similar as shocking enchantment, but you can see below there are some critical changes...
        affected.clear();

        arcs.clear();
        arc(attacker, defender, 2);

        for (Char ch : affected) {
            if (ch.alignment != attacker.alignment)
            ch.damage(Math.round(damage*0.6f), this);
        }

        attacker.sprite.parent.addToFront( new Lightning( arcs, null ) );
        Sample.INSTANCE.play( Assets.SND_LIGHTNING );

        return damage;
    }

    private ArrayList<Char> affected = new ArrayList<>();

    private ArrayList<Lightning.Arc> arcs = new ArrayList<>();

    private void arc( Char attacker, Char defender, int dist ) {

        affected.add(defender);

        defender.sprite.centerEmitter().burst(SparkParticle.FACTORY, 3);
        defender.sprite.flash();

        PathFinder.buildDistanceMap( defender.pos, BArray.not( Dungeon.level.solid, null ), dist );
        for (int i = 0; i < PathFinder.distance.length; i++) {
            if (PathFinder.distance[i] < Integer.MAX_VALUE) {
                Char n = Actor.findChar(i);
                if (n != null && n != attacker && n.alignment != attacker.alignment && !affected.contains(n)) {
                    arcs.add(new Lightning.Arc(defender.sprite.center(), n.sprite.center()));
                    arc(attacker, n, (Dungeon.level.water[n.pos] && !n.flying) ? 2 : 1);
                }
            }
        }
    }

    @Override
    public int max(int lvl) {
        return  5 + lvl*tier;       //5 base, +1 per level
    }

    @Override
    public int defenseFactor( Char owner ) {
        int def = 1+(int)(Math.floor(0.5*level()));
        return def;    //1 extra defence, plus 1 per 2 level;
    }

    public String statsInfo(){
        if (isIdentified()){
            return Messages.get(this, "stats_desc", 1+(int)(Math.floor(0.5*level())));
        } else {
            return Messages.get(this, "typical_stats_desc", 1);
        }
    }
}
