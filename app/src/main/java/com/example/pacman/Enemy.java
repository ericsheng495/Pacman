package com.example.pacman;

import android.graphics.Bitmap;

public abstract class Enemy {
    private Direction direction = Direction.RIGHT;
    private Direction next_direction;
    private Point location;
    private int landedOnPellet;
    private boolean visible = false;
    float x;
    float y;
    float vel;
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

    //public Direction getNext_direction(){
    //    return next_direction;
    //}

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

    public abstract void moveAlgo1(Pacman p);
    public abstract Bitmap getBitmap();
    public void setLocation(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void move() {
        float vel = GameView.mBoxSize/8.0f;
        switch (direction) {
            case UP:
                y = (y - vel < 0) ? (GameView.MAP_SIZE * GameView.mBoxSize - vel) : y - vel;
                break;
            case DOWN:
                y = (y + vel >= GameView.MAP_SIZE * GameView.mBoxSize) ? vel : y + vel;
                break;
            case LEFT:
                x = (x - vel < 0) ? (GameView.MAP_SIZE * GameView.mBoxSize - vel) : x - vel;
                break;
            case RIGHT:
                x = (x + vel >= GameView.MAP_SIZE * GameView.mBoxSize) ? vel : x + vel;
                break;
        }
    }

}
