package com.example.tamagotchi.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tamagotchi.R;

public class NewGameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game);

        EditText etName = findViewById(R.id.etPetName);
        Button btnStart = findViewById(R.id.btnStartGame);

        btnStart.setOnClickListener(v -> {
            SharedPreferences sp = getSharedPreferences("SAVE", MODE_PRIVATE);
            sp.edit()
                    .clear()
                    .putString("name", etName.getText().toString())
                    .putInt("hunger", 100)
                    .putInt("care", 100)
                    .putInt("mood", 100)
                    .putInt("day", 1)
                    .apply();

            startActivity(new Intent(this, GameActivity.class));
            finish();
        });
    }
}
