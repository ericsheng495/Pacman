package com.example.pacman;

import android.content.Context;
import android.widget.ImageView;

public class Pacman {
    private ImageView spriteView;
    private int x_location = 0;
    private int y_location = 0;
    private final int MOVE_VEL = 2;
    private int vel = 0;
    private int direction;
    private int next_direction;
    private int lives = 3;

    public Pacman(Context context, String sprite) {
        direction = 0;
        spriteView = new ImageView(context);
        spriteView.setImageResource(context.getResources().getIdentifier("@android:drawable/" + sprite, null, context.getPackageName()));
    }

    public ImageView getImageView() {
        return spriteView;
    }

    public void move() {
        vel = MOVE_VEL;
        switch (direction) {
            case 0:
                y_location -= vel;
                break;
            case 1:
                x_location += vel;
                break;
            case 2:
                x_location -= vel;
                break;
            case 3:
                y_location += vel;
                break;
        }
    }

    public void turnDirection() { direction = next_direction; }

    public void stop() {vel = 0;}

    public void update() {
        spriteView.setX(x_location);
        spriteView.setY(y_location);
    }


    public int getXloc() {
        return x_location;
    }

    public int getYloc() {
        return y_location;
    }

    public void setX_location(int x_location) {
        this.x_location = x_location;
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

    public int getLives() {
        return lives;
    }
}
