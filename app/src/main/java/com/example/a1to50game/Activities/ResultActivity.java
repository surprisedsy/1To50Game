package com.example.a1to50game.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a1to50game.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ResultActivity extends AppCompatActivity {

    private EditText nickNameInputEdTxt;
    private TextView recordTxt;
    private Button saveBtn, mainBtn;

    private String mRecordData;
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private BtnOnClickListener btnOnClickListener = new BtnOnClickListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        init();
        saveData();
    }

    public void init() {
        nickNameInputEdTxt = findViewById(R.id.nameInputEdTxt);
        recordTxt = findViewById(R.id.recordTxt);

        saveBtn = findViewById(R.id.saveBtn);
        mainBtn = findViewById(R.id.mainBtn);
    }

    public void saveData() {
        Intent data = getIntent();
        mRecordData = data.getStringExtra("Record");
        recordTxt.setText(mRecordData + "초");

        saveBtn.setOnClickListener(btnOnClickListener);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mainBtn.setOnClickListener(btnOnClickListener);
    }

    class BtnOnClickListener implements Button.OnClickListener {
        @Override
        public void onClick(View v) {
            int buttonId = v.getId();
            switch (buttonId) {
                case R.id.saveBtn:
                    Map<String, Object> saveData = new HashMap<>();

                    String nickName = nickNameInputEdTxt.getText().toString();

                    saveData.put("NickName", nickName);
                    saveData.put("Record", mRecordData + "초");

                    firestore.collection("recordData")
                            .add(saveData)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Toast.makeText(getApplicationContext(), "기록 저장 완료", Toast.LENGTH_SHORT).show();
                                    Intent mainIntent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(mainIntent);
                                    finish();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getApplicationContext(), "기록 저장에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                                }
                            });
                    break;
                case R.id.mainBtn:
                    Intent mainIntent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(mainIntent);
                    finish();
                    break;
            }
        }
    }
}
