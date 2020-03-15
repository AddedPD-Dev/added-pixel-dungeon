package com.shatteredpixel.shatteredpixeldungeon.actors.buffs;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.DwarfArmor;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.Image;
import com.watabou.noosa.audio.Sample;

public class DwarfArmorBuff extends FlavourBuff {

    {
        type = buffType.POSITIVE;
    }

    @Override
    public boolean act() {
        super.act();

        if (cooldown() <= 0) {
            CellEmitter.get( target.pos ).burst( Speck.factory( Speck.STEAM ), 8);
            Sample.INSTANCE.play(Assets.SND_BEACON);
            GLog.w( Messages.get(DwarfArmor.class, "deactivated") );
        }
        return true;
    }

    @Override
    public int icon() {
        return BuffIndicator.MOMENTUM;
    }

    @Override
    public void tintIcon(Image icon) {
        icon.tint(0xF3BE33, 0.6f);
        if (cooldown() < 4f) greyIcon(icon, 2f,cooldown());
    }

    @Override
    public String toString() {
        return Messages.get(this, "name");
    }

    @Override
    public String desc() {
        return Messages.get(this, "desc", dispTurns());
    }

}
