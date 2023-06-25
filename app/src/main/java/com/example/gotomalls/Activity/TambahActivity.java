package com.example.gotomalls.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gotomalls.API.APIRequestData;
import com.example.gotomalls.API.RetroServer;
import com.example.gotomalls.Model.ModelResponse;
import com.example.gotomalls.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahActivity extends AppCompatActivity {
    private EditText etnama,etlokasi,eturlmap,etjam_operasional;
    private Button btnsimpan;
    private String nama, lokasi,urlmap,jam_operasional;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah);

        etnama = findViewById(R.id.et_nama);
        etlokasi = findViewById(R.id.et_lokasi);
        eturlmap = findViewById(R.id.et_urlmap);
        etjam_operasional = findViewById(R.id.et_jam);
        btnsimpan = findViewById(R.id.btn_simpan);

        btnsimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nama = etnama.getText().toString();
                lokasi = etlokasi.getText().toString();
                urlmap = eturlmap.getText().toString();
                jam_operasional = etjam_operasional.getText().toString();

                if(nama.trim().isEmpty()){
                    etnama.setError("Nama Harus Diisi");
                }
                else if(lokasi.trim().isEmpty()){
                    etlokasi.setError("Lokasi Harus Diisi");
                }
                else if(urlmap.trim().isEmpty()){
                    eturlmap.setError("Url Map Harus Diisi");
                }
                else if(jam_operasional.trim().isEmpty()){
                    etjam_operasional.setError("Jam Operasional Harus Diisi");
                }
                else{
                    prosesSimpan();
                }
            }
        });

    }
    private void prosesSimpan(){
        APIRequestData API = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<ModelResponse> proses = API.ardCreate(nama,lokasi,urlmap,jam_operasional);

        proses.enqueue(new Callback<ModelResponse>() {
            @Override
            public void onResponse(Call<ModelResponse> call, Response<ModelResponse> response) {
                String kode = response.body().getKode();
                String pesan = response.body().getPesan();

                Toast.makeText(TambahActivity.this,pesan,Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<ModelResponse> call, Throwable t) {
                Toast.makeText(TambahActivity.this, "Gagal Menghubungi Server !",Toast.LENGTH_SHORT).show();


            }
        });
    }
}