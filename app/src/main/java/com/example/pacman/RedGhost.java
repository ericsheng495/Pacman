package com.example.pacman;

import java.util.ArrayList;
import java.util.Random;

public class RedGhost extends Enemy {
    GameView g;
    private int changeDirection = 5;
    public RedGhost (GameView g) {
        this.g = g;
    }
    @Override
    public PointType getEnemyType() {
        return PointType.ENEMYRED;
    }

    @Override
    public void moveAlgo1(Pacman p) {
        Random rand = new Random();
        ArrayList<Direction> available_dir = g.getEnemyPath(this);
        int n = rand.nextInt(available_dir.size());
        if (changeDirection == 0 || !available_dir.contains(this.getNext_direction())) {
            this.setNext_direction(available_dir.get(n));
            changeDirection = 5;
        }
        else {
            changeDirection--;
        }
    }
}
