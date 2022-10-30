package com.example.pacman;

public class Pacman {
    private int x_location;
    private int y_location;
    private int direction;
    private int next_direction;

    public Pacman() {
        this.x_location = 0;
        this.y_location = 0;
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

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public void setNext_direction(int next_direction) {
        this.next_direction = next_direction;
    }

    public int getDirection() {
        return direction;
    }

    public int getNext_direction(){
        return next_direction;
    }


}
