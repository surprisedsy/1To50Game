package com.example.a1to50game;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class NameInputActivity extends AppCompatActivity {

    private EditText name;
    private TextView record;
    private Button save, main;

    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_input);

        Init();
        saveData();
        backToMainActivity();
    }

    public void Init() {
        name = (EditText) findViewById(R.id.nameInput);
        record = (TextView) findViewById(R.id.record);

        save = (Button) findViewById(R.id.save);
        main = (Button) findViewById(R.id.main);
    }

    public void saveData() {
        // 1.record data intent로 받아오기
        Intent data = getIntent();
        final String recordData = data.getStringExtra("Record");

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 2. firestore 서버에 보내기
                Map<String, Object> data = new HashMap<>();

                String nickName = name.getText().toString();

                data.put("NickName", nickName);
                data.put("Record", recordData);

                firestore.collection("recordData")
                        .add(data)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(NameInputActivity.this, "기록 저장 완료", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(NameInputActivity.this, "기록 저장에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }

    public void backToMainActivity() {
        Intent mainIntent = new Intent(NameInputActivity.this, MainActivity.class);
        startActivity(mainIntent);
    }
}
