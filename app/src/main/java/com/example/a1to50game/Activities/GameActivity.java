package com.example.a1to50game.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.a1to50game.GameMain.ButtonAdapter;
import com.example.a1to50game.GameMain.ButtonsNumInfo;
import com.example.a1to50game.R;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private GestureDetector gestureDetector;
    private ButtonAdapter buttonAdapter;

    private TextView timerTxt;
    private Button startBtn;
    private Timer timer = new Timer();
    private ButtonsNumInfo data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Init();
        clickStartBtn();
    }

    public void Init() {

        recyclerView = findViewById(R.id.gameRecyclerView);
        timerTxt = (TextView) findViewById(R.id.timer);
        startBtn = (Button) findViewById(R.id.gameStart);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 5);
        recyclerView.setLayoutManager(gridLayoutManager);

        buttonAdapter = new ButtonAdapter();
        recyclerView.setAdapter(buttonAdapter);

        gestureDetector = new GestureDetector(getApplicationContext(), new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                return false;
            }

            @Override
            public void onShowPress(MotionEvent e) {

            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
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

        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {
                View child = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
                child.getTag();
                int position = recyclerView.getChildAdapterPosition(child);

                Log.d("aaaaaaaaa", "클릭된 포지션 " + child.getTag());

                return false;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean b) {

            }
        });
    }

    public void createRandomizeButtons(int from, int to) {

        int values[] = new int[25];
        Random random = new Random();

        for(int i = 0; i < values.length; i++)
        {
            values[i] = random.nextInt(to) + from;
            for(int j = 0; j < i; j++)
            {
                if(values[i] == values[j])
                    i--;
            }
        }

        for(int k = 0; k < 25; k++)
        {
            data = new ButtonsNumInfo();
            data.setButtonTxt(String.valueOf(values[k]));
            buttonAdapter.addItem(data);
        }
        buttonAdapter.notifyDataSetChanged();
    }

    public void clickStartBtn() {
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createRandomizeButtons(1, 25);
                timer.schedule(new GameTimerTask(), 3000, 1000);
            }
        });
    }

    private void updateTime(long time)
    {
        timerTxt.setText("기록: " + String.valueOf(time) + "초");
    }

    private class GameTimerTask extends TimerTask {
        private long seconds = 0;

        @Override
        public void run() {
            if (seconds == 100000) {
                timer.cancel();

                Intent intent = new Intent(GameActivity.this, NameInputActivity.class);
//                intent.putExtra("Record", String.valueOf(seconds));
                startActivity(intent);
                finish();

                return;
            }
            seconds++;

            // ui 변경은 메인 쓰레드에서
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    updateTime(seconds);
                }
            });
        }
    }
}
