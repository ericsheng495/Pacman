package com.example.pacman;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class MagentaGhost extends Enemy {
    float x;
    float y;
    private Bitmap sprite;

    @Override
    public PointType getEnemyType() {
        return PointType.ENEMYMAG;
    }

    public void setBitmap(Bitmap sprite) {
        this.sprite = sprite;
    }

    public Bitmap getBitmap() { return sprite; }

    public void setLocation(float x, float y) {
        this.x = x;
        this.y = y;
    }
}
