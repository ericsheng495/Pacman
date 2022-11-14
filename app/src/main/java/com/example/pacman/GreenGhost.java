package com.example.pacman;

import android.graphics.Bitmap;
import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

public class GreenGhost extends Enemy {
    private GameView g;
    public int changeDirection = 12;
    private Bitmap sprite;
    public GreenGhost (GameView g) {
        this.g = g;
    }

    public void setBitmap(Bitmap sprite) {
        this.sprite = sprite;
    }

    @Override
    public Bitmap getBitmap() { return sprite; }

    @Override
    public PointType getEnemyType() {
        return PointType.ENEMYGREEN;
    }

    public void moveAlgo1(Pacman p) {
        if (x%GameView.mBoxSize == 0 && y%GameView.mBoxSize == 0) {
            float dir_x = x - p.x;
            float dir_y = y - p.y;
            Random rand = new Random();
            ArrayList<Direction> available_dir = g.getEnemyPath(g.getPoint((int)x/GameView.mBoxSize, (int)y/GameView.mBoxSize));
            int rand_num = rand.nextInt(4);

            if (changeDirection == 0 || !available_dir.contains(this.getDirection())) {
                Log.d("Magenta ghost changeDir: ", "" + this.getDirection().name());
                if (rand_num != 0) {
                    //Go toward Pacman
                    if (available_dir.contains(Direction.LEFT) && x <= 0) {
                        this.setDirection(Direction.LEFT);
                    } else if (available_dir.contains(Direction.RIGHT) && x > 0) {
                        this.setDirection(Direction.RIGHT);
                    } else if (available_dir.contains(Direction.UP) && y <= 0) {
                        this.setDirection(Direction.UP);
                    } else if (available_dir.contains(Direction.DOWN) && y > 0) {
                        this.setDirection(Direction.DOWN);
                    } else {
                        int n = rand.nextInt(available_dir.size());
                        this.setDirection(available_dir.get(n));
                    }
                } else {
                    int n = rand.nextInt(available_dir.size());
                    this.setDirection(available_dir.get(n));
                }
                Log.d("Enemy:", "Enemy Direction: " + this.getDirection());
                changeDirection = 12;
            } else {
                changeDirection--;
            }
        }
        move();
    }
}
