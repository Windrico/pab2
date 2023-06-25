package com.example.gotomalls.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.gotomalls.API.APIRequestData;
import com.example.gotomalls.API.RetroServer;
import com.example.gotomalls.Adapter.AdapterMall;
import com.example.gotomalls.Model.ModelMall;
import com.example.gotomalls.Model.ModelResponse;
import com.example.gotomalls.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rvMall;
    private ProgressBar pbMall;
    private FloatingActionButton fabMall;
    private RecyclerView.Adapter adMall;
    private RecyclerView.LayoutManager lmMall;
    private List<ModelMall> listMall = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvMall = findViewById(R.id.rv_mall);
        pbMall= findViewById(R.id.pb_mall);
        fabMall = findViewById(R.id.fab_tambah);

        lmMall = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rvMall.setLayoutManager(lmMall);

        fabMall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,TambahActivity.class));
            }
        });
    }
    public void retrieveMall(){
        pbMall.setVisibility(View.VISIBLE);

        APIRequestData API = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<ModelResponse> proses = API.ardRetrieve();

        proses.enqueue(new Callback<ModelResponse>() {
            @Override
            public void onResponse(Call<ModelResponse> call, Response<ModelResponse> response) {
                String kode = response.body().getKode();
                String pesan = response.body().getPesan();
                listMall = response.body().getData();

                adMall =new AdapterMall(MainActivity.this,listMall);
                rvMall.setAdapter(adMall);
                adMall.notifyDataSetChanged();

                pbMall.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ModelResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this,"Error : Gagal Menghubungi Server", Toast.LENGTH_SHORT).show();
                pbMall.setVisibility(View.GONE);

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        retrieveMall();
    }
}