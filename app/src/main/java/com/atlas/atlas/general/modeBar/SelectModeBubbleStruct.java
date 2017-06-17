package com.atlas.atlas.general.modeBar;

import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;



 class SelectModeBubbleStruct {
    @DrawableRes private int drawableId;
    @StringRes private int stringId;
    private boolean selected;

     SelectModeBubbleStruct(int drawableId, int stringId, boolean selected) {
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

     public void setDrawableId(int drawableId) {
         this.drawableId = drawableId;
     }

     public void setStringId(int stringId) {
         this.stringId = stringId;
     }

     public void setSelected(boolean selected) {
         this.selected = selected;
     }
 }
