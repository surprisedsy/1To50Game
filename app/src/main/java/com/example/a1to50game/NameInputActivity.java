package com.example.a1to50game;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class NameInputActivity extends AppCompatActivity {

    private EditText name;
    private TextView record;
    private Button save, main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_input);

        Init();
//        saveData();
        backToMainActivity();
    }

    public void Init()
    {
        name = (EditText) findViewById(R.id.nameInput);
        record = (TextView) findViewById(R.id.record);

        save = (Button) findViewById(R.id.save);
        main = (Button) findViewById(R.id.main);
    }

    public void saveData()
    {
        // 1.record data intent로 받아오기

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 2. firestore 서버에 보내기
            }
        });
    }

    public void backToMainActivity()
    {
        Intent mainIntent = new Intent(NameInputActivity.this, MainActivity.class);
        startActivity(mainIntent);
    }
}
