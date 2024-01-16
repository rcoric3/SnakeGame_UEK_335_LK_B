package com.mygame.thesnakegame;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorSpace;
import android.graphics.Rect;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.ims.ImsManager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.thesnakegame.R;

import java.util.ArrayList;
import java.util.Random;

public class GameView extends View {
    private Bitmap bitmapGrass1, bitmapGrass2, bmSnake, bmApple;
    public static int sizeOfMap = 75 * Constants.SCREEN_WIDTH / 1080;

    private int h = 21, w = 12;
    private ArrayList<Grass> arrGrass = new ArrayList<>();
    private Snake snake;
    private boolean move = false;
    private float mx, my;

    private Handler handler;

    private Runnable r;

    private Apple apple;

    private int score = 0;

    private MainActivity mainActivity;


    public GameView(Context context, @Nullable AttributeSet attrs) {

        super(context, attrs);

        bitmapGrass1 = BitmapFactory.decodeResource(this.getResources(), R.drawable.grass);
        bitmapGrass1 = Bitmap.createScaledBitmap(bitmapGrass1, sizeOfMap, sizeOfMap, true);
        bitmapGrass2 = BitmapFactory.decodeResource(this.getResources(), R.drawable.grass2);
        bitmapGrass2 = Bitmap.createScaledBitmap(bitmapGrass2, sizeOfMap, sizeOfMap, true);
        bmSnake = BitmapFactory.decodeResource(this.getResources(), R.drawable.snake);
        bmSnake = Bitmap.createScaledBitmap(bmSnake, 14 * sizeOfMap, sizeOfMap, true);
        bmApple = BitmapFactory.decodeResource(this.getResources(), R.drawable.apple);
        bmApple = Bitmap.createScaledBitmap(bmApple, sizeOfMap, sizeOfMap, true);
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
        apple = new Apple(bmApple, arrGrass.get(randomApple()[0]).getX(),
                arrGrass.get(randomApple()[1]).getY());
        handler = new Handler();
        r = new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        };

        if (context instanceof MainActivity) {
            mainActivity = (MainActivity) context;
        }

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
                    if (mx - event.getX() > 100 * Constants.SCREEN_WIDTH / 1080 &&
                            !snake.isMove_right()) {
                        mx = event.getX();
                        my = event.getY();
                        snake.setMove_left(true);
                    } else if (event.getX() - mx > 100 * Constants.SCREEN_WIDTH / 1080 &&
                            !snake.isMove_left()) {
                        mx = event.getX();
                        my = event.getY();
                        snake.setMove_right(true);
                    } else if (my - event.getY() > 100 * Constants.SCREEN_WIDTH / 1080 &&
                            !snake.isMove_bottom()) {
                        mx = event.getX();
                        my = event.getY();
                        snake.setMove_top(true);
                    } else if (event.getY() - mx > 100 * Constants.SCREEN_WIDTH / 1080 &&
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
        apple.draw(canvas);
        if (snake.getArrSnakeParts().get(0).getrBody().intersect(apple.getR())) {
            int[] newApplePosition = randomApple();
            apple.reset(arrGrass.get(newApplePosition[0]).getX(), arrGrass.get(newApplePosition[1]).getY());
            snake.addPart();
            score++;
            if (mainActivity != null) {
                mainActivity.updateScore(score);
            }
        }
        handler.postDelayed(r, 150);
    }

    public int[] randomApple() {
        int[] xy = new int[2];
        Random r = new Random();

        boolean check = true;
        while (check) {
            xy[0] = r.nextInt(w);
            xy[1] = r.nextInt(h);

            Rect rect = new Rect(
                    arrGrass.get(xy[0] + xy[1] * w).getX(),
                    arrGrass.get(xy[0] + xy[1] * w).getY(),
                    arrGrass.get(xy[0] + xy[1] * w).getX() + sizeOfMap,
                    arrGrass.get(xy[0] + xy[1] * w).getY() + sizeOfMap
            );

            check = false;
            for (PartSnake snakePart : snake.getArrSnakeParts()) {
                if (rect.intersect(snakePart.getrBody())) {
                    check = true;
                    break;
                }
            }
        }

        return xy;
    }


    public void changeSnakeDirection(String direction) {
// Zurücksetzen der aktuellen Bewegungsrichtung
        snake.setMove_left(false);
        snake.setMove_right(false);
        snake.setMove_top(false);
        snake.setMove_bottom(false);

        switch (direction) {
            case "UP":
                snake.setMove_top(true);
                break;
            case "DOWN":
                snake.setMove_bottom(true);
                break;
            case "LEFT":
                snake.setMove_left(true);
                break;
            case "RIGHT":
                snake.setMove_right(true);
                break;
            default:
                break;
        }
    }
}