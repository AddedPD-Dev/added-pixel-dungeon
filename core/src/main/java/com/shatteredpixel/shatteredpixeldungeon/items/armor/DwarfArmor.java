package com.shatteredpixel.shatteredpixeldungeon.items.armor;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.DwarfArmorBuff;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;

public class DwarfArmor extends ClassArmor {
    public DwarfArmor() {
        this.image = ItemSpriteSheet.ARMOR_DWARF;
    }

    public void doSpecial() {
        DwarfArmorBuff overheated = curUser.buff(DwarfArmorBuff.class);
        if (overheated != null) {
            GLog.w( Messages.get(this, "already_activated") );
            return;
        }

        curUser.HP -= (curUser.HP / 3);

        curUser.spend( Actor.TICK );
        curUser.sprite.operate( curUser.pos );
        curUser.busy();

        // this formula will gain buff "after" spend TICK - so "real duration" is still 8f
        Buff.prolong(curUser, DwarfArmorBuff.class, 8f);
        GLog.p( Messages.get(this, "activated") );

        CellEmitter.get( curUser.pos ).burst( Speck.factory( Speck.STEAM ), 8 );
        Sample.INSTANCE.play(Assets.SND_BEACON);
    }
}
