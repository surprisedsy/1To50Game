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
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a1to50game.GameMain.GameAdapter;
import com.example.a1to50game.R;

import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    private long SECONDS = 0;
    private int mCurrentNum = 1;

    private RecyclerView recyclerView;
    private GestureDetector mGestureDetector;
    private GameAdapter mGameAdapter;

    private TextView timerTxt;
    private Button gameStartBtn;

    private TimerTask timerTask;
    private Timer timer = new Timer();

    Vector<Integer> group_1to25 = new Vector<>();
    Vector<Integer> group_26to50 = new Vector<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        init();
        clickStartBtn();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                gamePlay();
            }
        }, 3000);
    }

    public void init() {
        recyclerView = findViewById(R.id.gameRecyclerView);
        timerTxt = findViewById(R.id.timerTxt);
        gameStartBtn = findViewById(R.id.gameStartBtn);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 5);
        recyclerView.setLayoutManager(gridLayoutManager);

        mGameAdapter = new GameAdapter(this);
        recyclerView.setAdapter(mGameAdapter);

        mGestureDetector = new GestureDetector(getApplicationContext(), new GestureDetector.OnGestureListener() {
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
        gameStartBtn.setOnClickListener(this);
    }

    public void createRandomizeNums() {
        for (int i = 1; i <= 25; i++) {
            group_1to25.add(i);
            group_26to50.add(i + 25);
        }

        for (int i = 1; i <= 25; i++) {
            int random = (int) (Math.random() * group_1to25.size());
            mGameAdapter.init1to25(group_1to25.get(random));
            group_1to25.remove(random);
            mGameAdapter.notifyDataSetChanged();
        }
    }

    public void gamePlay() {
        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {
                View child = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
                int btnPosition = recyclerView.getChildAdapterPosition(child);

                if (child != null) {
                    if (btnPosition < 0)
                        ++btnPosition;

                    int clicked = mGameAdapter.getBtnNums(btnPosition);

                    if (clicked == mCurrentNum) {
                        int position = recyclerView.getChildAdapterPosition(child);

                        if (group_26to50 != null) {
                            int random = (int) (Math.random() * group_26to50.size());
                            mGameAdapter.updateNum(position, group_26to50.get(random));
                            group_26to50.remove(random);

                            if (group_26to50.size() == 0) {
                                group_26to50 = null;
                            }
                        }
                        if (clicked > 25 && clicked == mCurrentNum) {
                            mGameAdapter.setUpVisible(position);
                        }
                        mCurrentNum++;
                        mGameAdapter.notifyDataSetChanged();
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

        timerTask = new TimerTask() {
            @Override
            public void run() {
                SECONDS++;

                if (mCurrentNum == 51) {
                    try {
                        stopTimer();
                    } catch (Exception e) {
                        Log.e("timer exception", e.getMessage());
                    }
                    Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
                    intent.putExtra("Record", String.valueOf(SECONDS));
                    startActivity(intent);
                    finish();
                }

                Message msg = handler.obtainMessage();
                handler.sendMessage(msg);
            }
        };
        timer.schedule(timerTask, 3000, 1000);
    }

    public void stopTimer() {
        if (timerTask != null) {
            timerTask.cancel();
            timerTask = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopTimer();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent mainIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(mainIntent);
        finish();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.gameStartBtn:
                timerTxt.setText("기록: ");
                Toast.makeText(getApplicationContext(), "3초 후 시작됩니다.", Toast.LENGTH_SHORT).show();
                createRandomizeNums();
                timerSetting();
                break;
        }
    }
}
