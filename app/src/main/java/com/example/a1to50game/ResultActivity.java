package com.example.a1to50game;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.a1to50game.databinding.ActivityResultBinding;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ResultActivity extends AppCompatActivity {

    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private ActivityResultBinding binding;

    private Intent getData;
    private String recordData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_result);
        binding.setResultActivity(this);

        getData = getIntent();
        recordData = getData.getStringExtra("Record");
        binding.recordTxt.setText(recordData + "초");
    }

    public void saveData() {
        Map<String, Object> saveData = new HashMap<>();

        String nickName = binding.nameInputEdTxt.getText().toString();

        saveData.put("NickName", nickName);
        saveData.put("Record", recordData + "초");

        firestore.collection("recordData")
                .add(saveData)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(getApplicationContext(), "기록 저장 완료", Toast.LENGTH_SHORT).show();
                    Intent mainIntent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(mainIntent);
                    finish();
                })
                .addOnFailureListener(e -> Toast.makeText(getApplicationContext(), "기록 저장에 실패하였습니다.", Toast.LENGTH_SHORT).show());
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
}
