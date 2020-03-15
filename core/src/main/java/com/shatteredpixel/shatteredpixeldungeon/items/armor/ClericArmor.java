package com.shatteredpixel.shatteredpixeldungeon.items.armor;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Blindness;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Paralysis;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfBlastWave;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.noosa.audio.Sample;

public class ClericArmor extends ClassArmor {
    public ClericArmor() {
        this.image = ItemSpriteSheet.ARMOR_CLERIC;
    }

    public void doSpecial() {
        for (Mob mob : Dungeon.level.mobs.toArray(new Mob[0])) {
                if (Dungeon.level.heroFOV[mob.pos] && mob.alignment == Char.Alignment.ENEMY) {
                    Buff.affect(mob, Blindness.class, 3f);
                    WandOfBlastWave.throwChar(mob, new Ballistica(mob.pos, mob.pos + (mob.pos - curUser.pos), 6), 2);
                    Buff.affect(mob, Paralysis.class, 0f);  // just keep their current turn, like AquaBlast spell
                   }  }
        Hero hero = curUser;
        hero.HP -= curUser.HP / 3;
        curUser.spend( Actor.TICK );
        curUser.sprite.operate(curUser.pos);
        curUser.busy();
        GameScene.flash(CharSprite.DEFAULT);
        Sample.INSTANCE.play(Assets.SND_BLAST);
    }
}