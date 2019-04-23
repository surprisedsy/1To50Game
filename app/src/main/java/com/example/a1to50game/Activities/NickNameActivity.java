package com.example.a1to50game.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

public class NickNameActivity extends AppCompatActivity {

    EditText name;
    TextView record;
    Button save, main;

    FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nick_name);

        Init();
        saveData();
        goToMain();
    }

    public void Init() {
        name = (EditText) findViewById(R.id.nameInput);
        record = (TextView) findViewById(R.id.record);

        save = (Button) findViewById(R.id.save);
        main = (Button) findViewById(R.id.main);
    }

    public void saveData()
    {
        final String recordData = getIntent().getStringExtra("Record");
        record.setText(recordData + "초");

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> data = new HashMap<>();

                String nickName = name.getText().toString();

                data.put("NickName", nickName);
                data.put("Record", recordData + "초");

                firestore.collection("recordData")
                        .add(data)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(NickNameActivity.this, "기록 저장 완료", Toast.LENGTH_SHORT).show();

                                Intent rankIntent = new Intent(NickNameActivity.this, MainActivity.class);
                                startActivity(rankIntent);
                                finish();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(NickNameActivity.this, "기록 저장에 실패했습니다.", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }

    public void goToMain()
    {
        main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(NickNameActivity.this, MainActivity.class);
                startActivity(mainIntent);
                finish();
            }
        });
    }
}
