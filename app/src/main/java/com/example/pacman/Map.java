package com.example.pacman;

import android.content.Context;
//map [y-coords][x-coords]
public class Map implements Runnable {
    private int[][] map_layout;
    Pacman pacman;
    public Map(Context context) {

    }
    @Override
    public void run() {
        /*while (isRunning() == true) {
            int curr_x = pacman.getXloc();
            int curr_y = pacman.getYloc();
            int direction = pacman.getDirection();
            int next_direction = pacman.getNext_direction();
            // For now we will say 1 is a wall.
            if (direction != next_direction && canMoveInDirection(next_direction, curr_x, curr_y)) {
                turn_direction();
            } else if (!canMoveInDirection(direction, curr_x, curr_y)){
                stopPacman();
            }
        }*/



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
        return true;
    }


}
