package com.example.pacman;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;


public class Pacman {
    private Direction direction;
    private boolean doubleSpeedState, doubleScoreState, superState;
    private GameView view;
    private float spawn_x = 0;
    private float spawn_y = 0;
    private float vel;
    private Bitmap sprite;
    private int boxSize;
    private int scoreMultiplier;
    public int score;
    public int superSpeedTimer, superScoreTimer, superTimer;
    public int lives;
    public float x = 0;
    public float y = 0;


    public Pacman(GameView view, Bitmap sprite) {
        //RESET TO FALSE
        this.superState = false;
        //RESET TO 0
        this.superTimer = 0;
        this.score = 0;
        this.direction = Direction.RIGHT;
        this.doubleSpeedState = false;
        this.lives = 3;
        this.superSpeedTimer = 0;
        this.superScoreTimer = 0;
        this.view = view;
        this.sprite = sprite;
        this.scoreMultiplier = 1;
    }

    public void setLocation(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setSpawnPoint(int x, int y) {
        spawn_x = x;
        spawn_y = y;
    }

    public void respawn() {
        x = spawn_x;
        y = spawn_y;
        this.direction = Direction.RIGHT;
    }

    public void setSuper(boolean bool) {
        superState = bool;
    }

    public boolean getSuper() {
        return superState;
    }

    public Bitmap getBitmap() {
        return sprite;
    }

    public void setBoxSize(int boxSize) {
        this.boxSize = boxSize;
        this.vel = boxSize / 8.0f;
        Log.d("Pacman speed: ", "" + vel);
    }

    private boolean collide(float x1, float y1, float e_x1, float e_y1) {
        //https://stackoverflow.com/a/31035335/19170967
        float x2 = x1 + boxSize;
        float y2 = y1 + boxSize;
        float e_x2 = e_x1 + boxSize / 1.5f;
        float e_y2 = e_y1 + boxSize / 1.5f;
        return (x1 < e_x2 && x2 > e_x1 &&
                y1 < e_y2 && y2 > e_y1);
    }

    private float roundFloat(float number, int scale) {
        int pow = 10;
        for (int i = 1; i < scale; i++)
            pow *= 10;
        float tmp = number * pow;
        return ((float) ((int) ((tmp - (int) tmp) >= 0.5f ? tmp + 1 : tmp))) / pow;
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
        Log.d("Super", "" + superTimer);
        if (superTimer > 0) {
            superTimer--;
        } else {
            superTimer = 0;
            superState = false;
        }
        if (superSpeedTimer > 0) {
            superSpeedTimer--;
        } else {
            superSpeedTimer = 0;
            if (doubleSpeedState) vel /= 2;
            doubleSpeedState = false;
        }

        if (superScoreTimer > 0) {
            superScoreTimer--;
        } else {
            superScoreTimer = 0;
            if (doubleScoreState) scoreMultiplier = 1;
            doubleScoreState = false;
        }
        //Log.d("Pacman.grid_xy: ", x/boxSize + ", " + y/boxSize);
        //Log.d("Pacman.xy: ", x + ", " + y);
        //Log.d("Collision check", "" + x%boxSize + "," + y%boxSize + "\n");
        //Log.d("Check for wall collision: ", "" + (x%boxSize == 0 && y%boxSize == 0));
        if (x % boxSize == 0 && y % boxSize == 0) { //check for wall and pellet
            Point currentBlock = view.getPoint((int) x / boxSize, (int) y / boxSize);
            switch (currentBlock.type) {
                case PELLET:
                    //Add Points
                    score += 50;
                    //lives = 0; //debug
                    currentBlock.type = PointType.EMPTY;
                    break;
                case DOUBLE_PELLET:
                    //Add Points + Super
                    score += (100 * this.scoreMultiplier);
                    //superState = true;
                    if (!doubleScoreState) this.scoreMultiplier = 2;
                    doubleScoreState = true;
                    superScoreTimer = 100;
                    currentBlock.type = PointType.EMPTY;
                    break;
                case SPEED_PELLET:
                    //Add Points + Super
                    score += (100 * this.scoreMultiplier);
                    superSpeedTimer = 100;
                    if (!doubleSpeedState) this.vel *= 2;
                    doubleSpeedState = true;
                    currentBlock.type = PointType.EMPTY;
                    break;
                case INVINCIBLE_PELLET:
                    //Add Points + Super
                    superState = true;
                    superTimer = 250;
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
                    if (!superState) {
                        lives--;
                        if (lives > 0) {
                            view.resetMap();
                        }
                        break;
                    } else {
                        if (view.enemyQueue.isEmpty()) {
                            view.spawnTimer = 120;
                        }
                        view.enemies[i].setVisible(false);
                        view.enemyQueue.addLast(view.enemies[i].getEnemyType());
                        view.enemies[i].setLocation(view.mBoxSize * 8, view.mBoxSize * 7);
                        score += 100;
                    }
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
