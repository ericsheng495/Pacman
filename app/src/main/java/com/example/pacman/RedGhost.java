package com.example.pacman;

public class RedGhost extends Enemy {
    float x;
    float y;
    @Override
    public PointType getEnemyType() {
        return PointType.ENEMYRED;
    }
}
