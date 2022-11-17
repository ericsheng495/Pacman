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

    private boolean collide(float x1, float y1, float e_x1, float e_y1) {
        //https://stackoverflow.com/a/31035335/19170967
        float x2 = x1 + boxSize;
        float y2 = y1 + boxSize;
        float e_x2 = e_x1 + boxSize/1.5f;
        float e_y2 = e_y1 + boxSize/1.5f;
        return (x1 < e_x2 && x2 > e_x1 &&
                y1 < e_y2 && y2 > e_y1);
    }

    private float roundFloat(float number, int scale) {
        int pow = 10;
        for (int i = 1; i < scale; i++)
            pow *= 10;
        float tmp = number * pow;
        return ( (float) ( (int) ((tmp - (int) tmp) >= 0.5f ? tmp + 1 : tmp) ) ) / pow;
    }

    private void move() {
        switch (direction) {
            case UP:
                y = (y - vel < 0) ? (GameView.MAP_SIZE * boxSize - vel) : y - vel;
                //y = roundFloat(y, 2);
                break;
            case DOWN:
                y = (y + vel >= GameView.MAP_SIZE * boxSize) ? vel : y + vel;
                //y = roundFloat(y, 2);
                break;
            case LEFT:
                x = (x - vel < 0) ? (GameView.MAP_SIZE * boxSize - vel) : x - vel;
                //x = roundFloat(x, 2);
                break;
            case RIGHT:
                x = (x + vel >= GameView.MAP_SIZE * boxSize) ? vel : x + vel;
                //x = roundFloat(x, 2);
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

        //Log.d("Pacman.grid_xy: ", x/boxSize + ", " + y/boxSize);
        //Log.d("Pacman.xy: ", x + ", " + y);
        //Log.d("Collision check", "" + x%boxSize + "," + y%boxSize + "\n");
        //Log.d("Check for wall collision: ", "" + (x%boxSize == 0 && y%boxSize == 0));
        if (x%boxSize == 0 && y%boxSize == 0) { //check for wall and pellet
            Point currentBlock = view.getPoint((int)x/boxSize, (int)y/boxSize);
            switch (currentBlock.type) {
                case PELLET:
                    //Add Points
                    score += 50;
                    //lives = 0; //debug
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
        for (int i = 0; i < view.enemies.length; i++) {
            if (view.enemies[i].getVisible()) {
                float e_x = view.enemies[i].x;
                float e_y = view.enemies[i].y;
                if (collide(x, y, e_x, e_y)) {
                    lives = 0;
                    break;
                }
            }
        }


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
