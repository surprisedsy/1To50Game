package com.example.a1to50game;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;

import com.example.a1to50game.GameMain.ButtonAdapter;
import com.example.a1to50game.GameMain.ButtonItems;

import java.util.ArrayList;

public class GameActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    public static ArrayList<ButtonItems> buttonItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Init();
    }

    public void Init()
    {
        recyclerView = (RecyclerView) findViewById(R.id.gameRecyclerView);

        buttonItems = getItems();
        adapter = new ButtonAdapter();

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
    }

    private ArrayList<ButtonItems> getItems()
    {
        ArrayList<ButtonItems> buttonItems = new ArrayList<>();

        for(int i = 0; i < 4; i++)
        {
            ButtonItems items = new ButtonItems();
            items.getButtonTxt();
            buttonItems.add(items);
        }
        return buttonItems;
    }
}
