package com.shatteredpixel.shatteredpixeldungeon.actors.buffs;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.watabou.noosa.Image;

public class BurglarSense extends FlavourBuff {

    {
        type = buffType.POSITIVE;
        announced = true;
    }

    public static final float TRAP_DURATION	= 20f; // cooltime

    @Override
    public int icon() {
        return BuffIndicator.DEFERRED;
    }

    @Override
    public void tintIcon(Image icon) {
        greyIcon(icon, 5f, cooldown());
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
