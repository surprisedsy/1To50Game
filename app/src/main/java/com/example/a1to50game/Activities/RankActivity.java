package com.example.a1to50game.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.a1to50game.R;
import com.example.a1to50game.Ranking.RankAdapter;
import com.example.a1to50game.Ranking.RankInfo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Vector;

public class RankActivity extends AppCompatActivity {

    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private BtnOnClickListener onClickListener = new BtnOnClickListener();

    private Vector<RankInfo> rankInfoVector = new Vector<>();
    private Vector<RankInfo> copyVector = new Vector<>();
    private RankAdapter mRankAdapter;
    private RecyclerView mRankRecyclerView;
    private GestureDetector mGestureDetector;

    private EditText idSearchEdTxt;
    private Button rankSearchBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);

        init();
        getData();
    }

    public void init() {
        idSearchEdTxt = findViewById(R.id.idSearch);
        rankSearchBtn = findViewById(R.id.rankSearchBtn);
        mRankRecyclerView = findViewById(R.id.rankRecyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRankRecyclerView.setLayoutManager(linearLayoutManager);

        mRankAdapter = new RankAdapter(this, rankInfoVector);
        mRankRecyclerView.setAdapter(mRankAdapter);

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
        firestore.collection("recordData")
                .orderBy("Record")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String userName = document.get("NickName").toString();
                            String userRecord = document.get("Record").toString();

                            RankInfo info = new RankInfo();

                            for (int i = -1; i < rankInfoVector.size(); i++) {
                                info.setNameTxt(userName + " /");
                                info.setRecordTxt(userRecord);
                                info.setNumberTxt(String.valueOf(i + 2) + "등");
                            }
                            rankInfoVector.add(info);
                        }
                        copyVector.addAll(rankInfoVector);

                        searchName();

                        mRankAdapter.notifyDataSetChanged();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    public void searchName() {
        idSearchEdTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                rankSearchBtn.setOnClickListener(onClickListener);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent mainIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(mainIntent);
        finish();
    }

    class BtnOnClickListener implements Button.OnClickListener
    {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            switch (id)
            {
                case R.id.rankSearchBtn:
                    String idSearchTxt = idSearchEdTxt.getText().toString();

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
                    break;
            }
        }
    }
}
