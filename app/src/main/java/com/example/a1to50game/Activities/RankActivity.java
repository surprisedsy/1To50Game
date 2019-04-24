package com.example.a1to50game.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;

import com.example.a1to50game.R;
import com.example.a1to50game.Ranking.RankAdapter;
import com.example.a1to50game.Ranking.RankInfo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class RankActivity extends AppCompatActivity {

    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    private List<RankInfo> rankInfoList = new ArrayList<RankInfo>();
    private List<RankInfo> copyList = new ArrayList<>();
    private RankAdapter rankAdapter;
    private ListView listView;

    private EditText nickName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);

        Init();
        getData();
    }

    public void Init() {
        nickName = (EditText) findViewById(R.id.idSearch);

        listView = (ListView) findViewById(R.id.rankList);
        rankAdapter = new RankAdapter(this, rankInfoList);
        listView.setAdapter(rankAdapter);
    }

    public void getData() {
        firestore.collection("recordData")
                .orderBy("Record")
                .limit(10)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String name = document.get("NickName").toString();
                            String record = document.get("Record").toString();

                            RankInfo info = new RankInfo();

                            info.setNameTxt(name + " /");
                            info.setRecordTxt(record);

                            for(int i = -1; i < rankInfoList.size(); i++)
                            {
                                info.setNumberTxt(String.valueOf(i+2) + "등");
                            }

                            rankInfoList.add(info);
                        }
                        copyList.addAll(rankInfoList);

                        searchName();

                        rankAdapter.notifyDataSetChanged();
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
        nickName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String searchTxt = nickName.getText().toString();

                rankInfoList.clear();

                if (searchTxt.length() == 0)
                    rankInfoList.addAll(copyList);
                else {
                    for (int i = 0; i < copyList.size(); i++) {
                        if (copyList.get(i).getNameTxt().contains(searchTxt))
                            rankInfoList.add(copyList.get(i));
                    }
                }
                rankAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent mainIntent = new Intent(RankActivity.this, MainActivity.class);
        startActivity(mainIntent);
        finish();
    }
}
