package com.example.pacman;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Pair;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.LinkedList;
import java.util.PriorityQueue;
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
    private static final String TAG = "GameView";

    //Tiles and Pacman
    Map_Layout map_layout = new Map_Layout();
    private final Point[][] mPoints = new Point[MAP_SIZE][MAP_SIZE];
    private Pacman mPacMan;
    public int pelletCount = 0;
    private Direction mDir;
    private boolean mGameOver = false;

    //Enemies
    private GreenGhost mGreen = new GreenGhost();
    private MagentaGhost mMagenta = new MagentaGhost();
    private RedGhost mRed= new RedGhost();
    private boolean spawnable = true;


    private boolean mGameWin = false;
    //private PriorityQueue<Point> pelletQueue;
    private LinkedList<PointType> enemyQueue;
    //Sizing
    private int mBoxSize;
    private int mBoxPadding;

    private Paint mPaint = new Paint();

    //private Handler mHandler;
    public void init(Pacman pacman) {
        mBoxSize = getContext().getResources().getDimensionPixelSize(R.dimen.game_size) / MAP_SIZE;
        mDir = Direction.RIGHT;
        mBoxPadding = mBoxSize / 20;
        mPacMan = pacman;
        //mHandler = handler;
        //pelletQueue = new PriorityQueue<>((a, b) -> (a.x - b.x + b.y - a.y)%10);
        enemyQueue = new LinkedList<PointType>();
        enemyQueue.add(PointType.ENEMYMAG);
        enemyQueue.add(PointType.ENEMYGREEN);
        enemyQueue.add(PointType.ENEMYRED);
        initMap();
    }
    private void initMap() {
        for (int i = 0; i < MAP_SIZE; i++) {
            for (int j = 0; j < MAP_SIZE; j++) {
                mPoints[i][j] = new Point(j, i);
            }
        }

        int[][] mLayout = map_layout.getMap_layout();

        for (int i = 0; i < MAP_SIZE; i++) {
            for (int j = 0; j < MAP_SIZE; j++) {
                Point point = getPoint(j, i);
                switch (mLayout[i][j]) {
                    case 0:
                        point.type = PointType.EMPTY;
                        break;
                    case 1:
                        point.type = PointType.WALL;
                        break;
                    case 2:
                        point.type = PointType.PELLET;
                        pelletCount++;
                        break;
                    case 3:
                        point.type = PointType.POWER_PELLET;
                        pelletCount++;
                        break;
                    case 4:
                        point.type = PointType.PACMAN;
                        mPacMan.setPoint(point);
                        mPacMan.setSpawnPoint(point);
                        break;
                    //Ghost IDS:
                    // 5: Green
                    // 6: Magenta
                    // 7: Red
                    /*case 5:
                        point.type = PointType.ENEMYGREEN;
                        mGreen.setPoint(point);
                        break;
                    case 6:
                        point.type = PointType.ENEMYMAG;
                        mMagenta.setPoint(point);
                        break;
                    case 7:
                        point.type = PointType.ENEMYRED;
                        mRed.setPoint(point);
                        break;*/
                }
            }
        }

    }

    public void spawnGhost(int i, int j) {
        //
        Log.d(TAG, "" + spawnable);
        new Thread(() -> {
            try {
                if (!enemyQueue.isEmpty() && spawnable) {
                    Point point = getPoint(j, i);
                    if (point.type == PointType.EMPTY) {
                        point.type = enemyQueue.remove();
                        Thread.sleep(1000);
                        switch (point.type) {
                            case ENEMYGREEN:
                                mGreen.setPoint(point);
                                mGreen.setVisible(true);
                                break;
                            case ENEMYRED:
                                mRed.setPoint(point);
                                mRed.setVisible(true);
                                break;
                            case ENEMYMAG:
                                mMagenta.setPoint(point);
                                mMagenta.setVisible(true);
                                break;
                        }
                    }
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private Point getPoint(int x, int y) {
        return mPoints[y][x];
    }

    public void next(Direction inputDirection) {
        mPacMan.next(inputDirection);

        if (mPacMan.lives <= 0) {
            mGameOver = true;
        } else if (pelletCount <= 0) {
            mGameWin = true;
        }
    }

    public void enemyNext(Enemy enemy) {
        Point enemyFirst = enemy.getPoint();
        Random r = new Random();
        int randomNum = r.nextInt((4 - 1) + 1) + 1;
        Direction nextEnemyDir = Direction.UP;
        switch (randomNum) {
            case 1:
                nextEnemyDir = Direction.RIGHT;
                break;
            case 2:
                nextEnemyDir = Direction.LEFT;
                break;
            case 3:
                nextEnemyDir = Direction.UP;
                break;
            case 4:
                nextEnemyDir = Direction.DOWN;
                break;

        }
        //Direction nextEnemyDir = enemy.getNext_direction();
        Point enemyNext = getNext(enemyFirst, nextEnemyDir);

        if (nextEnemyDir != enemy.getDirection() && enemyNext.type != PointType.WALL) {
            //refactor
            enemy.setDirection(nextEnemyDir);
        }
        enemyNext = getNext(enemyFirst, enemy.getDirection());
        if (enemyNext.type == PointType.PELLET) {
            //landedOnPellet = 1;
            enemy.setLandedOnPellet(1);
            enemyNext.type = enemy.getEnemyType();
            enemyFirst.type = PointType.EMPTY;
            enemy.setPoint(enemyNext);
        } else if (enemyNext.type == PointType.POWER_PELLET) {
            //landedOnPellet = 2;
            enemy.setLandedOnPellet(2);
            enemyNext.type = enemy.getEnemyType();
            enemyFirst.type = PointType.EMPTY;
            enemy.setPoint(enemyNext);
        } else {
            if (enemyNext.type == PointType.PACMAN) {
                mPacMan.lives -= 1;
            } else if (enemyNext.type != PointType.WALL
                    && enemyNext.type != PointType.ENEMYGREEN
                    && enemyNext.type != PointType.ENEMYMAG
                    && enemyNext.type != PointType.ENEMYRED) {
                enemyNext.type = enemy.getEnemyType();
                if (enemy.getLandedOnPellet() == 1) {
                    enemyFirst.type = PointType.PELLET;
                    //landedOnPellet = 0;
                    enemy.setLandedOnPellet(0);
                } else if (enemy.getLandedOnPellet() == 2) {
                    enemyFirst.type = PointType.POWER_PELLET;
                    //landedOnPellet = 0;
                    enemy.setLandedOnPellet(0);
                } else {
                    enemyFirst.type = PointType.EMPTY;
                }
                enemy.setPoint(enemyNext);
            }
        }

    }

    public void enemyNext() {
        if (mGreen.getVisible()) {
            enemyNext(mGreen);
        }
        if (mRed.getVisible()) {
            enemyNext(mRed);
        }
        if (mMagenta.getVisible()) {
            enemyNext(mMagenta);
        }
        //enemyNext(mMagenta);
    }

    public void setDirection(Direction dir) {
        mDir = dir;
    }

    public Point getNext(Point point, Direction nextDirection) {
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

    public boolean isGameWin() {
        return mGameWin;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int y = 0; y < MAP_SIZE; y++) {
            for (int x = 0; x < MAP_SIZE; x++) {
                Point cur = getPoint(x, y);
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
                    case ENEMYGREEN:
                        mPaint.setColor(Color.GREEN);
                        break;
                    case ENEMYMAG:
                        mPaint.setColor(Color.MAGENTA);
                        break;
                    case ENEMYRED:
                        mPaint.setColor(Color.RED);
                        break;
                }
                int left = mBoxSize * x;
                int right = left + mBoxSize;
                int top = mBoxSize * y;
                int bottom = top + mBoxSize;
                canvas.drawRect(left, top, right, bottom, mPaint);
            }

        }
        //Text Display
        TextView scoreText = (TextView) ((GameActivity) getContext()).findViewById(R.id.scoreText);
        scoreText.setText("Score: " + mPacMan.score);
        TextView livesText = (TextView) ((GameActivity) getContext()).findViewById(R.id.livesText);
        livesText.setText("Lives: " + mPacMan.lives);
    }
}
