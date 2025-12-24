package com.example.tamagotchi.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tamagotchi.R;

public class EndingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ending);

        TextView tvReason = findViewById(R.id.tvEndingText);
        Button btnRestart = findViewById(R.id.btnRestart);
        Button btnExit = findViewById(R.id.btnExit);

        tvReason.setText(getIntent().getStringExtra("reason"));

        btnRestart.setOnClickListener(v ->
                startActivity(new Intent(this, MenuActivity.class)));

        btnExit.setOnClickListener(v -> finishAffinity());
    }
}
