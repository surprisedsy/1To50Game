package com.example.a1to50game.Activities;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a1to50game.GameMain.ButtonAdapter;
import com.example.a1to50game.GameMain.ButtonsNumInfo;
import com.example.a1to50game.R;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;
import java.util.zip.Inflater;

public class GameActivity extends AppCompatActivity {

    private static long SECONDS = 0;
    private static int mCurrentNum = 1;

    private RecyclerView recyclerView;
    private GestureDetector gestureDetector;
    private ButtonAdapter buttonAdapter;

    private TextView timerTxt;
    private Button startBtn;

    private TimerTask mTimerTask;
    private Timer timer = new Timer();

    Vector<Integer> _1to25 = new Vector<>();
    Vector<Integer> _26to50 = new Vector<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Init();
        clickStartBtn();

        gamePlay();
    }

    public void Init() {

        recyclerView = findViewById(R.id.gameRecyclerView);
        timerTxt = (TextView) findViewById(R.id.timer);
        startBtn = (Button) findViewById(R.id.gameStart);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 5);
        recyclerView.setLayoutManager(gridLayoutManager);

        buttonAdapter = new ButtonAdapter(this);
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
    }

    public void clickStartBtn() {
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createRandomizeNums();
                timerSetting();
            }
        });
    }

    public void createRandomizeNums() {
        for (int i = 1; i <= 25; i++) {
            _1to25.add(i);
            _26to50.add(i + 25);
        }

        for (int i = 1; i <= 25; i++) {
            int random = (int) (Math.random() * _1to25.size());
            buttonAdapter.init1to25(_1to25.get(random));
            _1to25.remove(random);
            buttonAdapter.notifyDataSetChanged();
        }
    }

    public void gamePlay() {
        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {
                View child = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());

                if (child != null) {
                    int clicked = buttonAdapter.getBtnNums(recyclerView.getChildAdapterPosition(child));

                    if (clicked == mCurrentNum) {
                        int position = recyclerView.getChildAdapterPosition(child);

                        if (_26to50 != null) {
                            int random = (int) (Math.random() * _26to50.size());
                            buttonAdapter.updateNum(position, _26to50.get(random));
                            _26to50.remove(random);

                            if (_26to50.size() == 0) {
                                _26to50 = null;
                            }
                        }
                        if (clicked > 25 && clicked == mCurrentNum) {
                            buttonAdapter.setUpVisible(position);
                        }

                        mCurrentNum++;
                        buttonAdapter.notifyDataSetChanged();
                    }
                }
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

    public void timerSetting() {
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                timerTxt.setText("기록: " + String.valueOf(SECONDS) + "초");
            }
        };

        mTimerTask = new TimerTask() {
            @Override
            public void run() {
                SECONDS++;

                if (mCurrentNum == 51) {
                    try {
                        stopTimer();
                    } catch (Exception e) {
                        Log.e("timer exception", e.getMessage());
                    }

                    Intent intent = new Intent(GameActivity.this, NickNameActivity.class);
                    intent.putExtra("Record", String.valueOf(SECONDS));
                    startActivity(intent);
                    finish();
                }

                Message msg = handler.obtainMessage();
                handler.sendMessage(msg);
            }
        };

        timer.schedule(mTimerTask, 3000, 1000);
    }

    public void stopTimer() {
        if (mTimerTask != null) {
            mTimerTask.cancel();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopTimer();
        finish();
    }
}
