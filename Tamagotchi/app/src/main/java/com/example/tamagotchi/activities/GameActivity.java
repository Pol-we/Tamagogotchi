package com.example.tamagotchi.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tamagotchi.R;

import java.util.Random;

public class GameActivity extends AppCompatActivity {

    ProgressBar pbHunger, pbCare, pbMood;
    TextView tvName, tvDay, tvPhrase;
    ImageView pet, bowl, tray, ball;

    int hunger, care, mood, day;
    String name;

    boolean gameRunning = false;
    boolean gameFinished = false;
    boolean startedFromMenu = false;



    CountDownTimer dayTimer;
    Handler moveHandler = new Handler();
    int screenWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        pbHunger = findViewById(R.id.pbHunger);
        pbCare = findViewById(R.id.pbCare);
        pbMood = findViewById(R.id.pbMood);
        Button btStop = findViewById(R.id.btStop);

        btStop.setOnClickListener(v -> pauseAndGoToMenu());

        tvName = findViewById(R.id.tvPetName);
        tvDay = findViewById(R.id.tvDay);
        tvPhrase = findViewById(R.id.tvPhrase);

        pet = findViewById(R.id.ivPet);
        bowl = findViewById(R.id.ivBowl);
        tray = findViewById(R.id.ivTray);
        ball = findViewById(R.id.ivBall);


        loadGame();
        updateUI();

        gameRunning = true;
        startDayTimer();
        startPetMovement();

        bowl.setOnClickListener(v -> {
            hunger = Math.min(100, hunger + 25);
            showPhrase(getString(R.string.pet_hungry));
            updateUI();
        });

        tray.setOnClickListener(v -> {
            care = Math.min(100, care + 20);
            showPhrase(getString(R.string.pet_dirty));
            updateUI();
        });

        ball.setOnClickListener(v -> {
            mood = Math.min(100, mood + 20);
            showPhrase(getString(R.string.pet_play));
            updateUI();
        });

        pet.setOnClickListener(v -> showRandomPhrase());
    }

    void startDayTimer() {

        if (dayTimer != null) return; // защита от двойного запуска

        dayTimer = new CountDownTimer(120000, 20000) {

            @Override
            public void onTick(long millisUntilFinished) {

                if (!gameRunning || gameFinished) return;

                hunger -= 3;
                care -= 2;
                mood -= 2;

                updateUI();
                checkEndings();
                saveGame();
            }

            @Override
            public void onFinish() {

                if (!gameRunning || gameFinished) return;

                day++;

                if (day >= 3) {
                    endGame(getString(R.string.ending_happy));
                    return;
                }

                dayTimer = null;
                startDayTimer();
            }
        }.start();
    }


    void pauseAndGoToMenu() {
        gameRunning = false;
        startedFromMenu = true;


        if (dayTimer != null) {
            dayTimer.cancel();
            dayTimer = null;
        }

        moveHandler.removeCallbacksAndMessages(null);
        saveGame();

        startActivity(new Intent(this, MenuActivity.class));
        finish();
    }



    void startPetMovement() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        screenWidth = dm.widthPixels;

        moveHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                int x = new Random().nextInt(screenWidth - pet.getWidth());
                pet.animate().x(x).setDuration(2000);
                moveHandler.postDelayed(this, 5000);
            }
        }, 3000);
    }

    void checkEndings() {
        if (hunger <= 0) endGame(getString(R.string.ending_hunger));
        if (care <= 0) endGame(getString(R.string.ending_care));
        if (mood <= 0) endGame(getString(R.string.ending_mood));
    }

    void resetGameStats() {
        hunger = 100;
        care = 100;
        mood = 100;
        day = 1;
    }


    void endGame(String reason) {
        gameFinished = true;
        gameRunning = false;

        if(dayTimer != null) dayTimer.cancel();
        if(moveHandler != null) moveHandler.removeCallbacksAndMessages(null);

        resetGameStats();
        saveGame();

        Intent i = new Intent(this, EndingActivity.class);
        i.putExtra("reason", reason);
        startActivity(i);
        finish();
    }

    void updateUI() {
        pbHunger.setProgress(hunger);
        pbCare.setProgress(care);
        pbMood.setProgress(mood);
        tvName.setText(name);
        tvDay.setText(day + " day");
    }

    void showPhrase(String text) {
        tvPhrase.setText(text);
        tvPhrase.setVisibility(View.VISIBLE);
        tvPhrase.postDelayed(() -> tvPhrase.setVisibility(View.INVISIBLE), 2000);
    }

    void showRandomPhrase() {
        String[] phrases = getResources().getStringArray(R.array.pet_random_phrases);
        showPhrase(phrases[new Random().nextInt(phrases.length)]);
    }

    void saveGame() {
        SharedPreferences sp = getSharedPreferences("SAVE", MODE_PRIVATE);
        sp.edit()
                .putInt("hunger", hunger)
                .putInt("care", care)
                .putInt("mood", mood)
                .putInt("day", day)
                .apply();
    }

    void loadGame() {
        SharedPreferences sp = getSharedPreferences("SAVE", MODE_PRIVATE);
        hunger = sp.getInt("hunger", 100);
        care = sp.getInt("care", 100);
        mood = sp.getInt("mood", 100);
        day = sp.getInt("day", 1);
        name = sp.getString("name", "Pet");
    }

    @Override
    protected void onPause() {
        super.onPause();

        gameRunning = false;

        if (dayTimer != null) {
            dayTimer.cancel();
            dayTimer = null;
        }

        moveHandler.removeCallbacksAndMessages(null);
        saveGame();
    }


    @Override
    protected void onResume() {
        super.onResume();

        if (!startedFromMenu) {
            gameRunning = true;
            startDayTimer();
            startPetMovement();
        }
    }




}
