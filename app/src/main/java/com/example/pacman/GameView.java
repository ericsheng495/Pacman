package com.example.pacman;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.LinkedList;
import java.util.Random;

public class GameView extends View {
    public GameView(Context context) {
        super(context);
    }
    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
    public GameView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private static final String TAG = "GameView";

    private static final int MAP_SIZE = 20;
    private static final int START_X = 5;
    private static final int START_Y = 10;

    private final Point[][] mPoints = new Point[MAP_SIZE][MAP_SIZE];
    private final LinkedList<Point> mPacMan = new LinkedList<>();
    private Direction mDir;

    private boolean mGameOver = false;
    private int mBoxSize;
    private int mBoxPadding;

    private Paint mPaint = new Paint();

    public void init() {
        mGameOver = false;
        mBoxSize = getContext().getResources().getDimensionPixelSize(R.dimen.game_size) / MAP_SIZE;
        mDir = Direction.RIGHT;
        mBoxPadding = mBoxSize / 20;
        initMap();
    }
    private void initMap() {
        for (int i = 0; i < MAP_SIZE; i++) {
            for (int j = 0; j < MAP_SIZE; j++) {
                mPoints[i][j] = new Point(j, i);
            }
        }

        int[][] map_layout = new int[][]
                {{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                 {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                 {1,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,1},
                 {1,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,1},
                 {1,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,1},
                 {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                 {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                 {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                 {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                 {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                 {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                 {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                 {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                 {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                 {1,0,0,0,0,0,0,0,0,0,4,0,0,0,0,0,0,0,0,1},
                 {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                 {1,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,1},
                 {1,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,1},
                 {1,3,2,2,2,0,0,0,0,0,0,0,0,0,0,2,2,2,3,1},
                 {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}};

        for (int i = 0; i < MAP_SIZE; i++) {
            for (int j = 0; j < MAP_SIZE; j++) {
                if (map_layout[i][j] == 0) {
                    Point point = getPoint(j, i);
                    point.type = PointType.EMPTY;
                }
                if (map_layout[i][j] == 1) {
                    Point point = getPoint(j, i);
                    point.type = PointType.WALL;
                }
                if (map_layout[i][j] == 2) {
                    Point point = getPoint(j, i);
                    point.type = PointType.PELLET;
                }
                if (map_layout[i][j] == 3) {
                    Point point = getPoint(j, i);
                    point.type = PointType.POWER_PELLET;
                }
                if (map_layout[i][j] == 4) {
                    Point point = getPoint(j, i);
                    point.type = PointType.PACMAN;
                    mPacMan.add(point);
                }
            }
        }

    }

    private Point getPoint(int x, int y) {
        return mPoints[y][x];
    }

    public void next() {
        Point first = mPacMan.getFirst();
        Point next = getNext(first);

        switch (next.type) {
            case EMPTY:
                next.type = PointType.PACMAN;
                mPacMan.addFirst(next);
                mPacMan.getLast().type = PointType.EMPTY;
                mPacMan.removeLast();
                break;
            case PELLET:
                next.type = PointType.PACMAN;
                mPacMan.addFirst(next);
                mPacMan.getLast().type = PointType.EMPTY;
                mPacMan.removeLast();
                //Add Points
                break;
            case POWER_PELLET:
                next.type = PointType.PACMAN;
                mPacMan.addFirst(next);
                mPacMan.getLast().type = PointType.EMPTY;
                mPacMan.removeLast();
                //Add Points + Super
            case WALL:
                break;
        }
    }

    public void setDirection(Direction dir) {
        if ((dir == Direction.LEFT || dir == Direction.RIGHT) && (mDir == Direction.LEFT || mDir == Direction.RIGHT)) {
            return;
        }
        if ((dir == Direction.UP || dir == Direction.DOWN) && (mDir == Direction.UP || mDir == Direction.DOWN)) {
            return;
        }
        mDir = dir;
    }


    private Point getNext(Point point) {
        int x = point.x;
        int y = point.y;

        switch (mDir) {
            case UP:
                y = y == 0 ? MAP_SIZE - 1 : y - 1;
                break;
            case DOWN:
                y = y == MAP_SIZE - 1 ? 0 : y + 1;
                break;
            case LEFT:
                x = x == 0 ? MAP_SIZE - 1 : x - 1;
                break;
            case RIGHT:
                x = x == MAP_SIZE - 1 ? 0 : x + 1;
                break;
        }
        return getPoint(x, y);
    }

    public boolean isGameOver() {
        return mGameOver;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int y = 0; y < MAP_SIZE; y++) {
            for (int x = 0; x < MAP_SIZE; x++) {
                switch (getPoint(x, y).type) {
                    case PELLET:
                        mPaint.setColor(0xffa500); //orange
                        break;
                    case POWER_PELLET:
                        mPaint.setColor(Color.RED);
                        break;
                    case PACMAN:
                        mPaint.setColor(Color.YELLOW);
                        break;
                    case EMPTY:
                        mPaint.setColor(Color.BLACK);
                        break;
                    case WALL:
                        mPaint.setColor(Color.BLUE);
                        break;
                }
                int left = mBoxSize * x;
                int right = left + mBoxSize;
                int top = mBoxSize * y;
                int bottom = top + mBoxSize;
                canvas.drawRect(left, top, right, bottom, mPaint);
            }

        }
    }
}
