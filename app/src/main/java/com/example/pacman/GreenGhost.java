package com.example.pacman;

import java.util.ArrayList;
import java.util.Random;

public class GreenGhost extends Enemy {
    GameView g;
    public int changeDirection = 5;
    public GreenGhost (GameView g) {
        this.g = g;
    }
    @Override
    public PointType getEnemyType() {
        return PointType.ENEMYGREEN;
    }

    @Override
    public void moveAlgo1(Pacman p) {
        Point l = p.getPoint();
        int pac_x = l.x;
        int pac_y = l.y;
        int x = this.getPoint().x - pac_x;
        int y = this.getPoint().y - pac_y;
        Random rand = new Random();
        ArrayList<Direction> available_dir = g.getEnemyPath(this);
        int rand_num = rand.nextInt(4);
        if (changeDirection == 0 || !available_dir.contains(this.getNext_direction())) {
            if (rand_num != 0) {
                //Go toward Pacman
                if (available_dir.contains(Direction.LEFT) && x <= 0) {
                    this.setNext_direction(Direction.LEFT);
                } else if (available_dir.contains(Direction.RIGHT) && x > 0) {
                    this.setNext_direction(Direction.RIGHT);
                } else if (available_dir.contains(Direction.UP) && y > 0) {
                    this.setNext_direction(Direction.UP);
                } else if (available_dir.contains(Direction.DOWN) && y <= 0) {
                    this.setNext_direction(Direction.DOWN);
                } else {
                    int n = rand.nextInt(available_dir.size());
                    this.setNext_direction(available_dir.get(n));
                }
            } else {
                int n = rand.nextInt(available_dir.size());
                this.setNext_direction(available_dir.get(n));
            }
            changeDirection = 5;
        }
        else {
            changeDirection--;
        }

    }
}