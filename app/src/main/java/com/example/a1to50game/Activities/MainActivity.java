package com.example.a1to50game.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.a1to50game.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button startBtn, rankBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        buttons();
    }

    public void init() {
        startBtn = findViewById(R.id.startBtn);
        rankBtn = findViewById(R.id.rankBtn);
    }

    public void buttons() {
        startBtn.setOnClickListener(this);
        rankBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int buttonsId = view.getId();
        switch (buttonsId) {
            case R.id.startBtn:
                Intent startIntent = new Intent(getApplicationContext(), GameActivity.class);
                startActivity(startIntent);
                finish();
                break;
            case R.id.rankBtn:
                Intent rankIntent = new Intent(getApplicationContext(), RankActivity.class);
                startActivity(rankIntent);
                finish();
                break;
        }
    }
}
