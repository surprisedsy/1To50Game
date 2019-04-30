package com.example.a1to50game;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;

import com.example.a1to50game.Ranking.RankAdapter;
import com.example.a1to50game.Ranking.RankInfo;
import com.example.a1to50game.databinding.ActivityRankBinding;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.Vector;

public class RankActivity extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ActivityRankBinding binding;

    private Vector<RankInfo> rankInfoVector = new Vector<>();
    private Vector<RankInfo> copyVector = new Vector<>();
    private RankAdapter mRankAdapter;
    GestureDetector mGestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_rank);
        binding.setRankActivity(this);

        init();
        getData();
    }

    public void init() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        binding.rankRecyclerView.setLayoutManager(linearLayoutManager);
        /**
         * pool이 사라지기 전에 고정시키기 위해 max viewholder 값을 셋팅하는 함수.
         * 아마 object pool 아니면 recyclerview 말하는듯?
         */
        binding.rankRecyclerView.getRecycledViewPool().setMaxRecycledViews(0, 0);

        mRankAdapter = new RankAdapter(this, rankInfoVector);
        binding.rankRecyclerView.setAdapter(mRankAdapter);

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

    public void getData() {
        db.collection("recordData")
                .orderBy("Record")
                .get()
                .addOnCompleteListener(task -> {
                    try {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String userName = document.get("NickName").toString();
                            String userRecord = document.get("Record").toString();

                            RankInfo info = new RankInfo();

                            /**
                             * 왜 -1부터 시작하냐면...
                             * userName/Record 받아올때 i=0 으로 해도 첫번째 데이터를 가져오긴 하지만 for문 루프로 들어가지는 않음. 왜그런지는 모르겠음..
                             * 따라서 setText method들에 셋팅이 안되서 값이 아무것도 안나옴.
                             * 근데 -1부터 시작하면 i++ 이후 i=0 이 만족되서 0번 인덱스에 값이 들어감.
                             * 어쨌든 document.get("keys").toString(); 때문에 그런듯.
                             */
                            int i;
                            for (i = -1; i < rankInfoVector.size(); i++) {
                                info.setNameTxt(userName + " /");
                                info.setRecordTxt(userRecord);
                                info.setNumberTxt(String.valueOf(i + 2) + "등");
                            }
                            rankInfoVector.add(info);
                        }
                        copyVector.addAll(rankInfoVector);

                        searchName();

                        mRankAdapter.notifyDataSetChanged();

                    } catch (NullPointerException e) {
                        Log.e("get record error", e.getMessage());
                    }
                })
                .addOnFailureListener(e -> e.printStackTrace());
    }

    public void searchName() {
        binding.idSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                binding.rankSearchBtn.setOnClickListener(v -> {
                    rankSearch();
                });

                if (binding.idSearch.getText().toString().length() == 0)
                {
                    rankInfoVector.clear();
                    rankInfoVector.addAll(copyVector);
                }
                mRankAdapter.notifyDataSetChanged();
            }
        });
    }

    public void rankSearch() {
        String idSearchTxt = binding.idSearch.getText().toString();

        rankInfoVector.clear();

        if (idSearchTxt.length() == 0)
            rankInfoVector.addAll(copyVector);
        else {
            for (int i = 0; i < copyVector.size(); i++) {
                if (copyVector.get(i).getNameTxt().contains(idSearchTxt))
                    rankInfoVector.add(copyVector.get(i));
            }
        }
        mRankAdapter.notifyDataSetChanged();
    }

    public void goToMain()
    {
        Intent mainIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(mainIntent);
        finish();
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
