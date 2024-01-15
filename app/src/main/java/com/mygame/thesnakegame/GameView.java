package com.mygame.thesnakegame;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorSpace;
import android.os.Bundle;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.thesnakegame.R;

import java.util.ArrayList;

public class GameView extends View {
    private Bitmap bitmapGrass1, bitmapGrass2, bmSnake;
    public static int sizeOfMap = 75 * Constants.SCREEN_WIDTH / 1080;

    private int h = 21, w = 12;
    private ArrayList<Grass> arrGrass = new ArrayList<>();
    private Snake snake;
    private boolean move = false;
    private float mx, my;

    private Handler handler;

    private Runnable r;


    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        bitmapGrass1 = BitmapFactory.decodeResource(this.getResources(), R.drawable.grass);
        bitmapGrass1 = Bitmap.createScaledBitmap(bitmapGrass1, sizeOfMap, sizeOfMap, true);
        bitmapGrass2 = BitmapFactory.decodeResource(this.getResources(), R.drawable.grass2);
        bitmapGrass2 = Bitmap.createScaledBitmap(bitmapGrass2, sizeOfMap, sizeOfMap, true);
        bmSnake = BitmapFactory.decodeResource(this.getResources(), R.drawable.snake);
        bmSnake = Bitmap.createScaledBitmap(bmSnake, 14 * sizeOfMap, sizeOfMap, true);
        for (int i = 0; i < h; i++) {
            for (int a = 0; a < w; a++) {
                if ((i + a) % 2 == 0) {
                    arrGrass.add(new Grass(bitmapGrass1, a * sizeOfMap + Constants.SCREEN_WIDTH
                            / 2 - (w / 2) * sizeOfMap, i * sizeOfMap + 100 *
                            Constants.SCREEN_HEIGHT / 1920, sizeOfMap, sizeOfMap));
                } else {
                    arrGrass.add(new Grass(bitmapGrass2, a * sizeOfMap + Constants.SCREEN_WIDTH
                            / 2 - (w / 2) * sizeOfMap, i * sizeOfMap + 100 *
                            Constants.SCREEN_HEIGHT / 1920, sizeOfMap, sizeOfMap));
                }
            }
        }
        snake = new Snake(bmSnake, arrGrass.get(80).getX(), arrGrass.get(60).getY(), 4);
        handler = new Handler();
        r = new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        };
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int a = event.getActionMasked();
        switch (a) {
            case MotionEvent.ACTION_MOVE: {
                if (move == false) {
                    mx = event.getX();
                    my = event.getY();
                    move = true;
                } else {
                    if (mx - event.getX() > 100 * Constants.SCREEN_WIDTH / 580 &&
                            !snake.isMove_right()) {
                        mx = event.getX();
                        my = event.getY();
                        snake.setMove_left(true);
                    } else if (event.getX() - mx > 100 * Constants.SCREEN_WIDTH / 580 &&
                            !snake.isMove_left()) {
                        mx = event.getX();
                        my = event.getY();
                        snake.setMove_right(true);
                    } else if (my - event.getY() > 100 * Constants.SCREEN_WIDTH / 580 &&
                            !snake.isMove_bottom()) {
                        mx = event.getX();
                        my = event.getY();
                        snake.setMove_top(true);
                    } else if (event.getY() - mx > 100 * Constants.SCREEN_WIDTH / 580 &&
                            !snake.isMove_top()) {
                        mx = event.getX();
                        my = event.getY();
                        snake.setMove_bottom(true);
                    }
                }
                break;
            }
            case MotionEvent.ACTION_UP: {
                mx = 0;
                my = 0;
                move = false;
                break;
            }
        }
        return true;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawColor(0xFF01A024);
        for (int i = 0; i < arrGrass.size(); i++) {
            canvas.drawBitmap(arrGrass.get(i).getBm(), arrGrass.get(i).getX(),
                    arrGrass.get(i).getY(), null);
        }
        snake.update();
        snake.draw(canvas);
        handler.postDelayed(r, 100);
    }
}