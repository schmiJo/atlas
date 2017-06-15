package com.atlas.atlas.general.modeBar;

import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;



 class ModeBubbleStruct {
    @DrawableRes private int drawableId;
    @StringRes private int stringId;
    private boolean selected;

     ModeBubbleStruct(int drawableId, int stringId, boolean selected) {
        this.drawableId = drawableId;
        this.stringId = stringId;
        this.selected = selected;
    }

    public int getDrawableId() {
        return drawableId;
    }

    public int getStringId() {
        return stringId;
    }

    public boolean isSelected() {
        return selected;
    }
}
