package com.example.pacman;

import android.content.Context;
//map [y-coords][x-coords]
public class Map implements Runnable {
    private int[][] map_layout;
    Pacman pacman;
    public Map(Context context, Pacman pacman) {
        this.pacman = pacman;
        map_layout = new int[20][20];
    }

    @Override
    public void run() {
        while (pacman.getLives() > 0) {
            int curr_x = pacman.getXloc();
            int curr_y = pacman.getYloc();
            int direction = pacman.getDirection();
            int next_direction = pacman.getNext_direction();
            // For now we will say 1 is a wall.
            if (direction != next_direction && canMoveInDirection(next_direction, curr_x, curr_y)) {
                pacman.turnDirection();
            } else if (!canMoveInDirection(direction, curr_x, curr_y)){
                pacman.stop();
            }
        }



    }

    public boolean canMoveInDirection(int direction, int x, int y) {
        if (direction == 0) {
            //UP
            if (map_layout[y - 1][x] == 0) {
                return true;
            } else {
                return false;
            }
        } else if (direction == 1) {
            //RIGHT
            if (map_layout[y][x + 1] == 0) {
                return true;
            } else {
                return false;
            }
        } else if (direction == 2) {
            //LEFT
            if (map_layout[y][x - 1] == 0) {
                return true;
            } else {
                return false;
            }
        } else if (direction == 3) {
            //DOWN
            if (map_layout[y + 1][x] == 0) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }


}