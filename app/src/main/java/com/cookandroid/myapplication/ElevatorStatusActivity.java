package com.cookandroid.myapplication;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

public class ElevatorStatusActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elevator_status);

        // 1. 전달된 엘리베이터 번호 받기
        int elevatorNum = getIntent().getIntExtra("elevator", 1);

        // 2. UI 요소 연결
        TextView title = findViewById(R.id.elevatorTitle);
        ImageView signalImage = findViewById(R.id.signalImage);
        Button nfcButton = findViewById(R.id.nfcButton);

        // 3. 제목 설정 + 폰트 적용
        title.setText(elevatorNum + "번 엘리베이터");
        Typeface boldFont = ResourcesCompat.getFont(this, R.font.gmarketsans_bold);
        title.setTypeface(boldFont);

        // 4. 신호등 이미지 기본값 (off)
        signalImage.setImageResource(R.drawable.off);

        // 5. NFC 태깅 버튼 클릭 시 NFC 화면으로 이동
        nfcButton.setOnClickListener(v -> {
            Intent intent = new Intent(ElevatorStatusActivity.this, NfcActivity.class);
            startActivity(intent);
        });
    }
}
