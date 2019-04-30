package com.example.a1to50game;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;

import com.example.a1to50game.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);

        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View actionbar = inflater.inflate(R.layout.custom_actionbar, null);

        actionBar.setCustomView(actionbar);

        ImageButton btnOff = findViewById(R.id.btnOff);

        actionbar.findViewById(R.id.btnBack).setVisibility(View.GONE);
        actionbar.findViewById(R.id.btnOff).setVisibility(View.VISIBLE);
        btnOff.setOnClickListener(v -> finish());

        return true;
    }
}
