package com.cookandroid.myapplication;

import android.app.PendingIntent;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class NfcActivity extends AppCompatActivity {

    private NfcAdapter nfcAdapter;
    private TextView statusText;
    private ImageView signalImage; // 신호등 이미지도 표시하고 싶다면 추가

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc);

        statusText = findViewById(R.id.nfcStatus);
        signalImage = findViewById(R.id.signalImage); // 이미지뷰가 있다면 연결

        nfcAdapter = NfcAdapter.getDefaultAdapter(this);

        if (nfcAdapter == null) {
            statusText.setText("이 기기는 NFC를 지원하지 않습니다.");
            return;
        }

        if (!nfcAdapter.isEnabled()) {
            statusText.setText("NFC가 꺼져 있습니다. 설정에서 켜주세요.");
        } else {
            statusText.setText("📲 NFC 태그를 기기에 가까이 대세요.");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (nfcAdapter != null) {
            Intent intent = new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_MUTABLE);
            nfcAdapter.enableForegroundDispatch(this, pendingIntent, null, null);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (nfcAdapter != null) {
            nfcAdapter.disableForegroundDispatch(this);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);

        if (tag != null) {
            // ✅ 토스트 메시지
            Toast.makeText(this, "✅ NFC 태깅 완료!", Toast.LENGTH_SHORT).show();

            // (선택) 텍스트뷰에 상태 표시
            statusText.setText("✅ 태깅이 감지되었습니다!");
        }
    }
}
