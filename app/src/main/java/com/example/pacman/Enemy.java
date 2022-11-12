package com.example.pacman;

public abstract class Enemy {
    private Direction direction;
    private Direction next_direction;
    private Point location;
    private int landedOnPellet;
    private boolean visible = false;
    public Enemy() {
        landedOnPellet = 0;
    }

    public void setPoint(Point point) {
        direction = Direction.LEFT;
        next_direction = Direction.UP;
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

    public abstract PointType getEnemyType();

    public void setVisible(boolean visibility) {
        visible = visibility;
    }

    public boolean getVisible() {
        return visible;
    }

    public void moveAlgo1() {

    }

}
