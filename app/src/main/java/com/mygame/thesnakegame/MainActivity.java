package com.mygame.thesnakegame;

import static androidx.core.content.ContextCompat.startActivity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.thesnakegame.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private GameView gameView;

    private Handler scoreUpdateHandler = new Handler();
    private Runnable scoreUpdateRunnable;

    private TextView scoreTextView;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        DisplayMetrics dm = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(dm);

        Constants.SCREEN_WIDTH = dm.widthPixels;
        Constants.SCREEN_HEIGHT = dm.heightPixels;
        setContentView(R.layout.game_screen);

        final GameView gameView = findViewById(R.id.game_view);

        ImageButton buttonUp = findViewById(R.id.buttonUp);
        ImageButton buttonDown = findViewById(R.id.buttonDown);
        ImageButton buttonLeft = findViewById(R.id.buttonLeft);
        ImageButton buttonRight = findViewById(R.id.buttonRight);

        scoreTextView = findViewById(R.id.textViewTitle);

        buttonUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameView.changeSnakeDirection("UP");
            }
        });
        buttonDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameView.changeSnakeDirection("DOWN");
            }
        });

        buttonLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameView.changeSnakeDirection("LEFT");
            }
        });
        buttonRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameView.changeSnakeDirection("RIGHT");
            }
        });
    }

    public void updateScore(int score) {
        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);

        int currentScore = score;
        int highScore = sharedPreferences.getInt("highScore", 0);

        if (currentScore > highScore) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("highScore", currentScore);
            editor.apply();
        }
        scoreTextView.setText("GAME SCORE:" + score);
    }
    public void goToHomeScreen() {
        Intent intent = new Intent(this, HomeScreenActivity.class);
        startActivity(intent);
        finish();
    }
}