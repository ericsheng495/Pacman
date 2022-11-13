package com.example.pacman;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
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
    public float x = 0;
    public float y = 0;
    private float vel;
    private Bitmap sprite;
    private int boxSize;
    public Pacman(GameView view, Bitmap sprite) {
        this.score = 0;
        this.direction = Direction.RIGHT;
        this.superState = false;
        this.lives = 3;
        superTimer = 0;
        this.view = view;
        this.sprite = sprite;
        invincibilityTimer = 0;
    }

    public void setPoint(Point point) {
        location = point;
    }
    public void setLocation(int x, int y) {
        this.x = x;
        this.y = y;
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

    public Bitmap getBitmap() {
        return sprite;
    }

    public void setBoxSize(int boxSize) {
        this.boxSize = boxSize;
        this.vel = boxSize/8.0f;
        Log.d("Pacman speed: ", "" + vel);
    }

    private void move() {
        switch (direction) {
            case UP:
                y = (y - vel < 0) ? (GameView.MAP_SIZE * boxSize - vel) : y - vel;
                break;
            case DOWN:
                y = (y + vel >= GameView.MAP_SIZE * boxSize) ? vel : y + vel;
                break;
            case LEFT:
                x = (x - vel < 0) ? (GameView.MAP_SIZE * boxSize - vel) : x - vel;
                break;
            case RIGHT:
                x = (x + vel >= GameView.MAP_SIZE * boxSize) ? vel : x + vel;
                break;
        }
    }

    public void next(Direction nextDirection) {
        if (superTimer > 0) {
            superTimer--;
        } else {
            superTimer = 0;
            superState = false;
        }

        Log.d("Pacman.grid_xy: ", x/boxSize + ", " + y/boxSize);
        Log.d("Check for wall collision: ", "" + (x%boxSize == 0 && y%boxSize == 0));
        if (x%boxSize == 0 && y%boxSize == 0) { //check for wall and pellet
            Point currentBlock = view.getPoint((int)x/boxSize, (int)y/boxSize);
            switch (currentBlock.type) {
                case PELLET:
                    //Add Points
                    score += 50;
                    lives = 0; //debug
                    currentBlock.type = PointType.EMPTY;
                    break;
                case POWER_PELLET:
                    //Add Points + Super
                    score += 100;
                    superState = true;
                    superTimer = 20;
                    currentBlock.type = PointType.EMPTY;
                    break;
            }
            Point nextBlock = view.getNext(currentBlock, nextDirection);
            if (nextDirection != direction && nextBlock.type != PointType.WALL) {
                direction = nextDirection;
            }
            nextBlock = view.getNext(currentBlock, direction);
            if (nextBlock.type != PointType.WALL) {
                move();
            }
        } else {
            move();
        }

        //check for enemy collision


        /*Point pacmanFirst = location;
        Point pacmanNext = view.getNext(pacmanFirst, nextDirection);




        if (nextDirection != direction && pacmanNext.type != PointType.WALL) {
            setDirection(nextDirection);
        }

        pacmanNext = view.getNext(pacmanFirst, direction);
        switch (pacmanNext.type) {
            case PELLET:
                //Add Points
                score += 50;
                lives = 0; //debug
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
        }*/
    }

}
