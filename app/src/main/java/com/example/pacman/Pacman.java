package com.example.pacman;

import android.content.Context;
import android.widget.ImageView;


public class Pacman {
    private Direction direction;
    private Point location;
    private Point spawnPoint;
    private boolean superState;
    private GameView view;

    public int score;
    public int superTimer;

    public int lives;
    public int invincibilityTimer;

    public Pacman(GameView view) {
        this.score = 0;
        this.direction = Direction.LEFT;
        this.superState = false;
        this.lives = 3;
        superTimer = 0;
        invincibilityTimer = 0;
        this.view = view;
    }

    public void setPoint(Point point) {
        location = point;
    }

    public void setSpawnPoint(Point point) {
        spawnPoint = point;
    }
    public Point getPoint() {
        return location;
    }

    public void setSuper(boolean bool) {
        superState = bool;
    }
    public boolean getSuper() {
        return superState;
    }

    public void setDirection(Direction dir) {
        this.direction = dir;
    }

    public Direction getDirection() {
        return direction;
    }

    public void next(Direction nextDirection) {
        Point pacmanFirst = location;
        Point pacmanNext = view.getNext(pacmanFirst, nextDirection);

        if (superTimer > 0) {
            superTimer--;
        } else {
            superTimer = 0;
            superState = false;
        }


        if (nextDirection != direction && pacmanNext.type != PointType.WALL) {
            setDirection(nextDirection);
        }

        pacmanNext = view.getNext(pacmanFirst, direction);
        switch (pacmanNext.type) {
            case PELLET:
                //Add Points
                score += 50;
                //lives = 0; //debug
                break;
            case POWER_PELLET:
                //Add Points + Super
                score += 100;
                superState = true;
                superTimer = 20;
                break;
            case ENEMYRED:
            case ENEMYGREEN:
            case ENEMYMAG:
                if (!superState) {
                    lives--;
                } else {
                    score += 200;
                    //view.enemyQueue.add(pacmanNext.type);
                }
                break;
        }

        if (pacmanNext.type != PointType.WALL) {
            if (superState || (pacmanNext.type != PointType.ENEMYRED && pacmanNext.type != PointType.ENEMYMAG
                    && pacmanNext.type != PointType.ENEMYGREEN)) {
                pacmanNext.type = PointType.PACMAN;
            }
            pacmanFirst.type = PointType.EMPTY;
            location = pacmanNext;
        }
    }

}
