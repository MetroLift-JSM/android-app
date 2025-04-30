package com.cookandroid.myapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.github.chrisbanes.photoview.PhotoView;

public class StationDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station_detail);

        // 역 이름 받아오기
        String stationName = getIntent().getStringExtra("station");

        TextView tvStation = findViewById(R.id.tvStationName);
        PhotoView stationImage = findViewById(R.id.stationImage);
        LinearLayout buttonContainer = findViewById(R.id.elevatorButtonContainer);

        tvStation.setText(stationName);  // 타이틀에 역 이름 표시

        // 역별 이미지 및 버튼 처리
        switch (stationName) {
            case "서면역":
                stationImage.setImageResource(R.drawable.seomyeon);  // res/drawable/seomyeon.png
                addElevatorButtons(buttonContainer, 3);
                break;
            case "연산역":
                stationImage.setImageResource(R.drawable.yeonsan);
                addElevatorButtons(buttonContainer, 3);
                break;
            case "사상역":
                stationImage.setImageResource(R.drawable.sasang);
                addElevatorButtons(buttonContainer, 3);
                break;
            case "해운대역":
                stationImage.setImageResource(R.drawable.haeundae);
                addElevatorButtons(buttonContainer, 4);
                break;
            case "다대포해수욕장역":
                stationImage.setImageResource(R.drawable.dadaepo);
                addElevatorButtons(buttonContainer, 4);
                break;
            case "부산역":
                // 나중에 서버 데이터 표시
                stationImage.setImageResource(R.drawable.busan); // 임시 이미지
                addElevatorButtons(buttonContainer, 3); // 임시용
                break;
        }
    }

    // 엘리베이터 버튼 동적 생성
    private void addElevatorButtons(LinearLayout container, int count) {
        for (int i = 1; i <= count; i++) {
            Button btn = new Button(this);
            btn.setText(i + "번 엘리베이터");
            btn.setBackgroundResource(R.drawable.rounded_border);  // 테두리 있는 버튼 drawable
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 16, 0, 0);  // 버튼 간격
            btn.setLayoutParams(params);
            container.addView(btn);
        }
    }
}
