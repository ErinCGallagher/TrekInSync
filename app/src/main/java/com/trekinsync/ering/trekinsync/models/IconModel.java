package com.trekinsync.ering.trekinsync.models;

import android.support.annotation.DrawableRes;

public class IconModel {
    private @DrawableRes int drawable;
    private int id;

    public IconModel(@DrawableRes int drawable, int id) {
        this.drawable = drawable;
        this.id = id;
    }

    public @DrawableRes int getDrawable() {
        return drawable;
    }

    public int getId() {
        return id;
    }
}
