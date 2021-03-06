package com.example.a1to50game;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.a1to50game.GameMain.GameAdapter;
import com.example.a1to50game.databinding.ActivityGameBinding;

import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

public class GameActivity extends AppCompatActivity {

    private long SECONDS = 0;
    private int mCurrentNum = 1;

    private ActivityGameBinding binding;

    GestureDetector mGestureDetector;
    private GameAdapter mGameAdapter;

    private TimerTask timerTask;
    private Timer timer = new Timer();

    Vector<Integer> group_1to25 = new Vector<>();
    Vector<Integer> group_26to50 = new Vector<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_game);
        binding.setGameActivity(this);

        init();

        new Handler().postDelayed(() -> gamePlay(), 3000);
    }

    public void init() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 5);
        binding.gameRecyclerView.setLayoutManager(gridLayoutManager);

        mGameAdapter = new GameAdapter(this);
        binding.gameRecyclerView.setAdapter(mGameAdapter);

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

    public void gameStart() {
        binding.timerTxt.setText("기록: ");
        Toast.makeText(getApplicationContext(), "3초 후 시작됩니다.", Toast.LENGTH_SHORT).show();
        createRandomizeNums();
        timerSetting();

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
        binding.gameRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
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

                binding.timerTxt.setText("기록: " + String.valueOf(SECONDS) + "초");
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

    public void goToMain()
    {
        Intent mainIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(mainIntent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopTimer();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        goToMain();
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

        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> goToMain());

        return true;
    }
}
