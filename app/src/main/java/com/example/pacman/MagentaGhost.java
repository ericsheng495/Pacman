package com.example.pacman;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class MagentaGhost extends Enemy {
    private Bitmap sprite;

    @Override
    public PointType getEnemyType() {
        return PointType.ENEMYMAG;
    }

    public void setBitmap(Bitmap sprite) {
        this.sprite = sprite;
    }

    public Bitmap getBitmap() { return sprite; }
}
