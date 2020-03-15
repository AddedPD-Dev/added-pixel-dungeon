package com.shatteredpixel.shatteredpixeldungeon.actors.buffs;

import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;

public class OnBlessedWater extends FlavourBuff {
    {
        type = buffType.NEGATIVE;
        announced = false;
    }

    @Override
    public int icon() {
        return BuffIndicator.DEVOTION;
    }

    @Override
    public String toString() {
        return Messages.get(Devotion.class, "holy_water_debuff_name");
    }

    @Override
    public String desc() {
        return Messages.get(Devotion.class, "holy_water_debuff_desc", dispTurns());
    }

}