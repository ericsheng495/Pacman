package com.example.pacman;

import android.content.Context;
import android.widget.ImageView;


public class Pacman {
    private int x_location;
    private int y_location;
    private Direction direction;
    private Direction next_direction;
    private Point location;
    private boolean superState;
    private int lives;

    public int score;
    public int superTimer;
    public Pacman() {
        this.score = 0;
        this.direction = Direction.RIGHT;
        this.next_direction = Direction.RIGHT;
        this.superState = false;
        this.lives = 3;
        superTimer = 0;
    }

    public void setPoint(Point point) {
        location = point;
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

    public int getXloc() {
        return x_location;
    }

    public int getYloc() {
        return y_location;
    }

    public void setX_location(int x) {
        this.x_location = x;
    }

    public void setY_location(int y_location) {
        this.y_location = y_location;
    }

    public void setDirection(Direction dir) {
        this.direction = dir;
    }

    public void setNext_direction(Direction next_direction) {
        this.next_direction = next_direction;
    }

    public Direction getDirection() {
        return direction;
    }

    public Direction getNext_direction(){
        return next_direction;
    }

    public void setLives(int l) {
        lives = l;
    }

    public int getLives() {
        return lives;
    }
}
