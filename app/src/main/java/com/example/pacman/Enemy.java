package com.example.pacman;

import android.graphics.Bitmap;

public abstract class Enemy {
    private Direction direction;
    private Direction next_direction;
    private Point location;
    private int landedOnPellet;
    private boolean visible = true;
    float x;
    float y;
    Bitmap sprite;

    public Enemy() {
        landedOnPellet = 0;
    }

    public void setPoint(Point point) {
        //direction = Direction.LEFT;
        //next_direction = Direction.LEFT;
        location = point;
    }

    public Point getPoint() {
        return location;
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

    public void setLandedOnPellet(int i) {
        landedOnPellet = i;
    }
    public int getLandedOnPellet() {
        return landedOnPellet;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
    public boolean getVisible() { return visible; }

    public abstract PointType getEnemyType();

    public void moveAlgo1() {

    }

    public void setLocation(float x, float y) {
        this.x = x;
        this.y = y;
    }

}
