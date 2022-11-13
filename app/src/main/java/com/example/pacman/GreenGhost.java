package com.example.pacman;

public class GreenGhost extends Enemy {
    float x;
    float y;
    @Override
    public PointType getEnemyType() {
        return PointType.ENEMYGREEN;
    }
}
