package com.cookandroid.myapplication;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.github.chrisbanes.photoview.PhotoView;

public class StationDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station_detail);

        String stationName = getIntent().getStringExtra("station");

        TextView tvStation = findViewById(R.id.tvStationName);
        PhotoView stationImage = findViewById(R.id.photo_view);
        LinearLayout buttonContainer = findViewById(R.id.elevatorButtonContainer);

        // G마켓 폰트 적용
        Typeface typeface = ResourcesCompat.getFont(this, R.font.gmarketsans_medium);
        tvStation.setTypeface(typeface);

        tvStation.setText(stationName);

        switch (stationName) {
            case "서면역":
                stationImage.setImageResource(R.drawable.seomyeon);
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
                addElevatorButtons(buttonContainer, 3);
                break;
            case "다대포해수욕장역":
                stationImage.setImageResource(R.drawable.dadaepo);
                addElevatorButtons(buttonContainer, 3);
                break;
            case "부산역":
                stationImage.setImageResource(R.drawable.busan);
                addElevatorButtons(buttonContainer, 3);
                break;
        }
    }

    // ✅ 여기에 붙여넣는 함수
    private void addElevatorButtons(LinearLayout container, int count) {
        Typeface typeface = ResourcesCompat.getFont(this, R.font.gmarketsans_medium);

        for (int i = 1; i <= count; i++) {
            Button btn = new Button(this);
            btn.setText(i + "번 엘리베이터");
            btn.setTypeface(typeface);
            btn.setBackgroundResource(R.drawable.rounded_border);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            float scale = getResources().getDisplayMetrics().density;
            int marginHorizontal = (int) (24 * scale + 0.5f);
            int marginTop = (int) (12 * scale + 0.5f);
            params.setMargins(marginHorizontal, marginTop, marginHorizontal, 0);
            btn.setLayoutParams(params);

            int finalI = i;
            btn.setOnClickListener(v -> {
                Intent intent = new Intent(StationDetailActivity.this, ElevatorStatusActivity.class);
                intent.putExtra("elevator", finalI);
                startActivity(intent);
            });

            container.addView(btn);
        }
    }
}
