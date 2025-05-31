package com.cookandroid.myapplication;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    LinearLayout btnBusan, btnSeomyeon, btnYeonsan, btnSasang, btnDadaepo, btnHaeundae;
    ApiService apiService;

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

        Typeface gmarketFont = ResourcesCompat.getFont(this, R.font.gmarketsans_medium);

        int[] textIds = {
                R.id.guideText, R.id.busanText, R.id.seomyeonText,
                R.id.yeonsanText, R.id.sasangText, R.id.dadaepoText, R.id.haeundaeText
        };

        for (int id : textIds) {
            TextView tv = findViewById(id);
            tv.setTypeface(gmarketFont);
        }

        btnBusan = findViewById(R.id.btnBusan);
        btnSeomyeon = findViewById(R.id.btnSeomyeon);
        btnYeonsan = findViewById(R.id.btnYeonsan);
        btnSasang = findViewById(R.id.btnSasang);
        btnDadaepo = findViewById(R.id.btnDadaepo);
        btnHaeundae = findViewById(R.id.btnHaeundae);

        //  RetrofitClient 통해 apiService 초기화
        apiService = RetrofitClient.getClient().create(ApiService.class);

        btnBusan.setOnClickListener(view -> moveToDetail("부산역"));
        btnSeomyeon.setOnClickListener(view -> moveToDetail("서면역"));
        btnYeonsan.setOnClickListener(view -> moveToDetail("연산역"));
        btnSasang.setOnClickListener(view -> moveToDetail("사상역"));
        btnDadaepo.setOnClickListener(view -> moveToDetail("다대포해수욕장역"));
        btnHaeundae.setOnClickListener(view -> moveToDetail("해운대역"));
    }

    private void moveToDetail(String stationName) {
        RequestData data = new RequestData(stationName);
        Call<Void> call = apiService.sendStation(data);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.d("RETROFIT", "서버 전송 성공");
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("RETROFIT", "서버 전송 실패: " + t.getMessage());
            }
        });

        Intent intent = new Intent(MainActivity.this, StationDetailActivity.class);
        intent.putExtra("station", stationName);
        startActivity(intent);
    }
}
