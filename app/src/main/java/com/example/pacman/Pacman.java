package com.example.pacman;

import android.content.Context;
import android.widget.ImageView;

public class Pacman {
    private ImageView spriteView;
    private float x_location = 150.0f;
    private float y_location = 150.0f;
    private float vel = 2.0f;
    private int direction;
    private int next_direction;

    public Pacman(Context context, String sprite) {
        direction = 0;
        spriteView = new ImageView(context);
        spriteView.setImageResource(context.getResources().getIdentifier("@android:drawable/" + sprite, null, context.getPackageName()));
    }

    public void update() {
        spriteView.setX(x_location);
        spriteView.setY(y_location);
    }

    public ImageView getImageView() {
        return spriteView;
    }

    public float getXloc() {
        return x_location;
    }

    public float getYloc() {
        return y_location;
    }

    public void setX_location(float x) {
        this.x_location = x;
    }

    public void setY_location(float y_location) {
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

    public void move() {
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
}
