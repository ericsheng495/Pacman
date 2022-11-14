package com.example.pacman;

public class Map_Layout {
    private int[][] map_layout = new int[20][20];
    public Map_Layout() {
        this(2);
    }
    public Map_Layout(int level) {
        switch (level) {
            case 1:
                map_layout = new int[][]
                        {{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                        {1,3,0,2,0,0,0,0,0,0,0,0,0,0,0,0,2,0,3,1},
                        {1,0,1,1,2,0,2,0,1,1,1,1,0,2,0,2,1,1,0,1},
                        {1,2,1,0,0,0,0,2,0,1,1,0,2,0,0,0,0,1,2,1},
                        {1,0,1,0,1,1,1,0,0,1,1,0,0,1,1,1,0,1,0,1},
                        {1,0,0,0,1,0,1,2,0,0,0,0,2,1,0,1,0,0,0,1},
                        {1,0,0,0,1,1,1,0,0,0,0,0,0,1,1,1,0,0,0,1},
                        {1,0,0,0,0,3,0,0,0,0,0,0,0,0,3,0,0,0,0,1},
                        {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                        {0,0,0,0,0,0,0,1,1,1,1,1,1,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,1,0,0,0,0,1,0,0,0,0,0,0,0},
                        {1,0,0,0,0,0,0,1,1,1,1,1,1,0,0,0,0,0,0,1},
                        {1,0,0,0,0,3,0,0,0,0,0,0,0,0,3,0,0,0,0,1},
                        {1,0,0,0,1,1,1,0,0,0,0,0,0,1,1,1,0,0,0,1},
                        {1,0,0,0,1,0,1,0,0,0,0,0,0,1,0,1,0,0,0,1},
                        {1,0,1,0,1,1,1,0,0,1,1,0,0,1,1,1,0,1,0,1},
                        {1,2,1,0,0,0,0,0,0,1,1,0,0,0,0,0,0,1,2,1},
                        {1,0,1,1,0,0,0,0,1,1,1,1,0,0,0,0,1,1,0,1},
                        {1,3,0,2,0,0,0,0,0,0,0,0,0,0,0,0,2,0,3,1},
                        {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}};
                break;
            case 2:
                map_layout = new int[][]
                   {{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                    {1,3,0,2,0,2,0,2,0,1,1,0,2,0,2,0,2,0,3,1},
                    {1,0,1,1,0,1,1,1,2,1,1,2,1,1,1,0,1,1,0,1},
                    {1,2,0,2,0,2,0,1,0,1,1,0,1,0,2,0,2,0,2,1},
                    {1,0,1,0,1,0,2,0,2,0,0,2,0,2,0,1,0,1,0,1},
                    {1,2,1,2,1,0,1,1,1,1,1,1,1,1,0,1,2,1,2,1},
                    {1,0,2,0,1,0,0,0,0,1,1,0,0,0,0,1,0,2,0,1},
                    {1,1,1,0,1,1,1,1,0,1,1,0,1,1,1,1,0,1,1,1},
                    {1,1,1,0,1,0,0,0,0,0,0,0,0,0,0,1,0,1,1,1},
                    {0,0,0,0,1,1,1,0,1,0,0,1,0,1,1,1,0,0,0,0},
                    {0,0,0,0,0,0,0,0,1,0,0,1,0,0,0,0,0,0,0,0},
                    {1,1,1,0,1,0,1,1,1,1,1,1,1,1,0,1,0,1,1,1},
                    {1,1,1,2,1,0,0,0,0,0,0,0,0,0,0,1,2,1,1,1},
                    {1,0,0,0,2,0,2,0,1,1,1,1,0,2,0,2,0,0,0,1},
                    {1,0,0,2,1,1,1,0,0,0,1,0,0,1,1,1,2,0,0,1},
                    {1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1},
                    {1,2,0,2,0,2,1,0,1,1,1,1,0,1,2,0,2,0,2,1},
                    {1,0,1,1,1,1,1,0,0,1,1,0,0,1,1,1,1,1,0,1},
                    {1,3,0,2,0,2,0,2,0,0,0,0,4,0,2,0,0,0,3,1},
                    {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}};
        }
    }
    public int[][] getMap_layout() {
        return map_layout;
    }
}
