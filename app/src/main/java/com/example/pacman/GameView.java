package com.example.pacman;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.LinkedList;
import java.util.Random;
import android.os.Handler;
import android.widget.TextView;

/*
NOTE: This code was refactored and adapted from a youtube snake tutorial.
Link: https://www.youtube.com/watch?v=edUrUoZHouY&ab_channel=HDROIDStudio
 */

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

    //Constants
    private static final int MAP_SIZE = 20;
    private static final int START_X = 5;
    private static final int START_Y = 10;

    //Tiles and Pacman
    private final Point[][] mPoints = new Point[MAP_SIZE][MAP_SIZE];
    private Pacman mPacMan;
    private Direction mDir;
    private boolean mGameOver = false;

    //Enemies
    private Enemy mGreen = new Enemy();
    private int landedOnPellet = 0;

    //Sizing
    private int mBoxSize;
    private int mBoxPadding;

    private Paint mPaint = new Paint();

    private Handler mHandler;
    public void init(Pacman pacman) {
        mGameOver = false;
        mBoxSize = getContext().getResources().getDimensionPixelSize(R.dimen.game_size) / MAP_SIZE;
        mDir = Direction.RIGHT;
        mBoxPadding = mBoxSize / 20;
        mPacMan = pacman;
        //mHandler = handler;
        initMap();
    }
    private void initMap() {
        for (int i = 0; i < MAP_SIZE; i++) {
            for (int j = 0; j < MAP_SIZE; j++) {
                mPoints[i][j] = new Point(j, i);
            }
        }

        Map_Layout map_layout = new Map_Layout();

        int[][] mLayout = map_layout.getMap_layout();

        for (int i = 0; i < MAP_SIZE; i++) {
            for (int j = 0; j < MAP_SIZE; j++) {
                if (mLayout[i][j] == 0) {
                    Point point = getPoint(j, i);
                    point.type = PointType.EMPTY;
                }
                if (mLayout[i][j] == 1) {
                    Point point = getPoint(j, i);
                    point.type = PointType.WALL;
                }
                if (mLayout[i][j] == 2) {
                    Point point = getPoint(j, i);
                    point.type = PointType.PELLET;
                }
                if (mLayout[i][j] == 3) {
                    Point point = getPoint(j, i);
                    point.type = PointType.POWER_PELLET;
                }
                if (mLayout[i][j] == 4) {
                    Point point = getPoint(j, i);
                    point.type = PointType.PACMAN;
                    mPacMan.setPoint(point);
                }
                //Ghost IDS:
                // 5: Green
                // 6: Cyan
                // 7: Magenta
                if (mLayout[i][j] == 5) {
                    Point point = getPoint(j, i);
                    point.type = PointType.ENEMY;
                    mGreen.setPoint(point);
                }
            }
        }

    }

    private Point getPoint(int x, int y) {
        return mPoints[y][x];
    }

    public void next(Direction nextDirection) {
        Point pacmanFirst = mPacMan.getPoint();
        Point pacmanNext = getNext(pacmanFirst, nextDirection);

        if (nextDirection != mPacMan.getDirection() && pacmanNext.type != PointType.WALL) {
            mPacMan.setDirection(nextDirection);
        }

        pacmanNext = getCurrNext(pacmanFirst);
        switch (pacmanNext.type) {
            case PELLET:
                //Add Points
                mPacMan.score += 50;
                break;
            case POWER_PELLET:
                //Add Points + Super
                mPacMan.score += 100;
                mPacMan.setSuper(true);
                mPacMan.superTimer = 20;
                break;
            case ENEMY:
                mPacMan.setLives(mPacMan.getLives() - 1);
                break;
        }
        if (pacmanNext.type != PointType.WALL) {
            pacmanNext.type = PointType.PACMAN;
            pacmanFirst.type = PointType.EMPTY;
            mPacMan.setPoint(pacmanNext);
        }
    }

    public void enemyNext() {
        Point greenFirst = mGreen.getPoint();
        Direction nextGreenDir = mGreen.getNext_direction();
        Point greenNext = getNext(greenFirst, nextGreenDir);

        if (nextGreenDir != mGreen.getDirection() && greenNext.type != PointType.WALL) {
            //needs to be refactored
            mGreen.setDirection(nextGreenDir);
        }

        greenNext = getCurrNext(greenFirst);

        //Needs to be refactored, moves pellets one back instead of the same place after landing on
        //time
        if (greenNext.type == PointType.PELLET) {
            landedOnPellet = 1;
        } else if (greenNext.type == PointType.POWER_PELLET) {
            landedOnPellet = 2;
        }
        if (greenNext.type != PointType.WALL) {
            greenNext.type = PointType.ENEMY;
            if (landedOnPellet == 1) {
                greenFirst.type = PointType.PELLET;
                landedOnPellet = 0;
            } else if (landedOnPellet == 2) {
                greenFirst.type = PointType.POWER_PELLET;
                landedOnPellet = 0;
            } else {
                greenFirst.type = PointType.EMPTY;
            }
            mGreen.setPoint(greenNext);

        }
    }

    public void setDirection(Direction dir) {
        mDir = dir;
    }

    private Point getCurrNext(Point point) {
        int x = point.x;
        int y = point.y;

        switch (mPacMan.getDirection()) {
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

    private Point getNext(Point point, Direction nextDirection) {
        int x = point.x;
        int y = point.y;

        switch (nextDirection) {
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
                        mPaint.setColor(Color.WHITE);
                        break;
                    case POWER_PELLET:
                        mPaint.setColor(Color.parseColor("#FC9D03"));//orange
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
                    case ENEMY:
                        mPaint.setColor(Color.MAGENTA);
                }
                int left = mBoxSize * x;
                int right = left + mBoxSize;
                int top = mBoxSize * y;
                int bottom = top + mBoxSize;
                canvas.drawRect(left, top, right, bottom, mPaint);
            }

        }
        //TextView scoreText = findViewById(R.id.scoreText);
        //scoreText.setText("" + mPacMan.score);
        TextView scoreText = (TextView) ((GameActivity) getContext()).findViewById(R.id.scoreText);
        scoreText.setText("Score: " + mPacMan.score);
    }
}
