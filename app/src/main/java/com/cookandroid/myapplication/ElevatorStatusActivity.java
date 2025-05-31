package com.cookandroid.myapplication;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ElevatorStatusActivity extends AppCompatActivity {

    private ApiService apiService;
    private Handler handler = new Handler();
    private Runnable statusRunnable;
    private ImageView signalImage;
    private int elevatorNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elevator_status);

        // 1. 엘리베이터 번호 받기
        elevatorNum = getIntent().getIntExtra("elevator", 1);

        // 2. UI 요소 연결
        TextView title = findViewById(R.id.elevatorTitle);
        signalImage = findViewById(R.id.signalImage);
        Button nfcButton = findViewById(R.id.nfcButton);

        // 3. 제목 설정 + 폰트
        title.setText(elevatorNum + "번 엘리베이터");
        Typeface boldFont = ResourcesCompat.getFont(this, R.font.gmarketsans_bold);
        title.setTypeface(boldFont);

        // 4. 초기 이미지 안 보이게 설정
        signalImage.setVisibility(View.INVISIBLE);

        // 5. NFC 버튼
        nfcButton.setOnClickListener(v -> {
            Intent intent = new Intent(ElevatorStatusActivity.this, NfcActivity.class);
            startActivity(intent);
        });

        // 6. Retrofit 초기화
        apiService = RetrofitClient.getClient().create(ApiService.class);

        // 7. 5초마다 서버 상태 요청
        statusRunnable = new Runnable() {
            @Override
            public void run() {
                requestElevatorStatus();
                handler.postDelayed(this, 5000);
            }
        };
        handler.post(statusRunnable);
    }

    private void requestElevatorStatus() {
        String elevatorName = elevatorNum + "번엘리베이터";

        Call<StatusResponse> call = apiService.getElevatorStatusForElevator("1번엘리베이터");

        call.enqueue(new Callback<StatusResponse>() {
            @Override
            public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String status = response.body().getStatus();
                    Log.d("STATUS", "응답 상태: " + status);

                    signalImage.setVisibility(View.VISIBLE); // 성공 시 다시 보이게

                    switch (status) {
                        case "RED":
                            signalImage.setImageResource(R.drawable.red);
                            break;
                        case "YELLOW":
                            signalImage.setImageResource(R.drawable.yellow);
                            break;
                        case "GREEN":
                            signalImage.setImageResource(R.drawable.green);
                            break;
                        default:
                            signalImage.setVisibility(View.INVISIBLE); // 예상 외 값은 숨김
                    }
                } else {
                    Log.e("STATUS", "서버 응답 오류: " + response.code());
                    signalImage.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onFailure(Call<StatusResponse> call, Throwable t) {
                Log.e("STATUS", "서버 요청 실패: " + t.getMessage());
                signalImage.setVisibility(View.INVISIBLE); // 실패 시 숨김
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(statusRunnable);
    }
}
