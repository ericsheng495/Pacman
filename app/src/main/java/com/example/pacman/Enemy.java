package com.example.pacman;

public class Enemy {
    private int x_location;
    private int y_location;
    private Direction direction;
    private Direction next_direction;
    private Point location;

    public Enemy() {

    }

    public void setPoint(Point point) {
        location = point;
    }

    public Point getPoint() {
        return location;
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

    public void moveAlgo1() {

    }
}
