package com.example.a1to50game;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.a1to50game.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setMainActivity(this);
    }

    public void start()
    {
        Intent startIntent = new Intent(getApplicationContext(), GameActivity.class);
        startActivity(startIntent);
        finish();
    }

    public void rank()
    {
        Intent rankIntent = new Intent(getApplicationContext(), RankActivity.class);
        startActivity(rankIntent);
        finish();
    }
}
