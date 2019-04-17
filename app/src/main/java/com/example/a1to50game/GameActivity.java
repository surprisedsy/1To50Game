package com.example.a1to50game;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.example.a1to50game.GameMain.ButtonAdapter;
import com.example.a1to50game.GameMain.ButtonsNumInfo;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    private TextView timerTxt;
    private String runningTime;
    public static ArrayList<ButtonsNumInfo> buttonItems = new ArrayList<ButtonsNumInfo>();

    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Init();
    }

    public void Init()
    {
        recyclerView = (RecyclerView) findViewById(R.id.gameRecyclerView);
        timerTxt = (TextView) findViewById(R.id.timer);

        adapter = new ButtonAdapter(buttonItems);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(GameActivity.this));

        // guestureDetector 쓰는 이유 -> 누르고 뗄 때 한버만 인식하도록 하려고.
        gestureDetector = new GestureDetector(getApplicationContext(), new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                return true;
            }

            @Override
            public void onShowPress(MotionEvent e) {

            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) {

            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                return false;
            }
        });
        recyclerView.addOnItemTouchListener(onItemTouchListener);
    }

    RecyclerView.OnItemTouchListener onItemTouchListener = new RecyclerView.OnItemTouchListener() {
        @Override
        public boolean onInterceptTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent event) {
            View childView = recyclerView.findChildViewUnder(event.getX(), event.getY());

            if(childView != null && gestureDetector.onTouchEvent(event))
            {
                int currentPosition = recyclerView.getChildAdapterPosition(childView);

                ButtonsNumInfo currentItemInfo = buttonItems.get(currentPosition);
                Log.d("aaaaaa", "현재 클릭된 포지션 / " + currentItemInfo);
                return true;
            }
            return false;
        }

        @Override
        public void onTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean b) {

        }
    };
}
