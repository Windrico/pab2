package com.example.gotomalls.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.gotomalls.R;

public class DetailMallActivity extends AppCompatActivity {
    private TextView tvNama, tvLokasi,tvUrlmap,tvJam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_mall);

        tvNama = findViewById(R.id.tv_nama);
        tvLokasi = findViewById(R.id.tv_lokasi);
        tvUrlmap = findViewById(R.id.tv_urlmap);
        tvJam =findViewById(R.id.tv_jam_operasional);

        Intent tangkap = getIntent();
        String nama = tangkap.getStringExtra("varnama");
        String lokasi= tangkap.getStringExtra("varlokasi");
        String urlmap = tangkap.getStringExtra("varurlmap");
        String jam = tangkap.getStringExtra("varjam");

        tvNama.setText(nama);
        tvLokasi.setText(lokasi);
        tvUrlmap.setText(urlmap);
        tvJam.setText(jam);
    }
}