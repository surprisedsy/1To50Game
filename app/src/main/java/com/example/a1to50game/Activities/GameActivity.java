package com.example.a1to50game.Activities;

import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a1to50game.GameMain.ButtonAdapter;
import com.example.a1to50game.GameMain.ButtonsNumInfo;
import com.example.a1to50game.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    private ButtonAdapter buttonAdapter;

    private TextView timerTxt;
    private Button startBtn;
    private String runningTime;
    private Timer timer;

    public static ArrayList<ButtonsNumInfo> buttonItems = new ArrayList<>();

    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Init();
        createButtons();
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

                // 버튼 클릭 되면 숫자 바뀜 이벤트 넣기
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

    public void createButtons() {

        // 랜덤함수 집어넣기 -> 생성되는 숫자 겹치지 않게
        List<String> stringList = Arrays.asList(
                "a", "b", "c", "d", "e",
                "a", "b", "c", "d", "e",
                "a", "b", "c", "d", "e",
                "a", "b", "c", "d", "e",
                "a", "b", "c", "d", "e");

        for (int i = 0; i < stringList.size(); i++) {
            ButtonsNumInfo data = new ButtonsNumInfo();
            data.setButtonTxt(stringList.get(i));

            buttonAdapter.addItem(data);
        }
        buttonAdapter.notifyDataSetChanged();
    }

    public void clickStartBtn() {
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                long startTime = System.currentTimeMillis();


                // 게임 플레이 하는 함수 넣으면 될듯;;;


                long currentTime = System.currentTimeMillis();

                long millis = currentTime - startTime;
                int seconds = (int) millis / 1000;
                seconds = seconds % 60;

                millis = (millis / 10) % 100;
                runningTime = String.format("%02d : %02d", seconds, millis);
                timerTxt.setText(runningTime);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        timerTxt.setText(null);
    }
}
