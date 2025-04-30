package com.cookandroid.myapplication;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    LinearLayout btnBusan, btnSeomyeon, btnYeonsan, btnSasang, btnDadaepo, btnHaeundae;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 💡 GmarketSansMedium 폰트 적용
        Typeface gmarketFont = ResourcesCompat.getFont(this,R.font.gmarketsans_medium);

        int[] textIds = {
                R.id.guideText, R.id.busanText, R.id.seomyeonText, R.id.yeonsanText,
                R.id.sasangText, R.id.dadaepoText, R.id.haeundaeText
        };

        for (int id : textIds) {
            TextView tv = findViewById(id);
            tv.setTypeface(gmarketFont);
        }

        // 버튼 연결
        btnBusan = findViewById(R.id.btnBusan);
        btnSeomyeon = findViewById(R.id.btnSeomyeon);
        btnYeonsan = findViewById(R.id.btnYeonsan);
        btnSasang = findViewById(R.id.btnSasang);
        btnDadaepo = findViewById(R.id.btnDadaepo);
        btnHaeundae = findViewById(R.id.btnHaeundae);

        // 클릭 리스너
        btnBusan.setOnClickListener(view -> moveToDetail("부산역"));
        btnSeomyeon.setOnClickListener(view -> moveToDetail("서면역"));
        btnYeonsan.setOnClickListener(view -> moveToDetail("연산역"));
        btnSasang.setOnClickListener(view -> moveToDetail("사상역"));
        btnDadaepo.setOnClickListener(view -> moveToDetail("다대포해수욕장역"));
        btnHaeundae.setOnClickListener(view -> moveToDetail("해운대역"));
    }

    private void moveToDetail(String stationName) {
        Intent intent = new Intent(MainActivity.this, StationDetailActivity.class);
        intent.putExtra("station", stationName);
        startActivity(intent);
    }
}
