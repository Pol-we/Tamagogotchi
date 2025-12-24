package com.example.tamagotchi.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tamagotchi.R;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Button btnNew = findViewById(R.id.btnNewGame);
        Button btnLoad = findViewById(R.id.btnLoad);
        Button btnExit = findViewById(R.id.btnExit);

        btnNew.setOnClickListener(v ->
                startActivity(new Intent(this, NewGameActivity.class)));

        btnLoad.setOnClickListener(v ->
                startActivity(new Intent(this, GameActivity.class)));

        btnExit.setOnClickListener(v -> finish());
    }
}
