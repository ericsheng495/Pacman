package com.example.pacman;

public class RedGhost extends Enemy {
    @Override
    public PointType getEnemyType() {
        return PointType.ENEMYRED;
    }
}
