package com.example.gotomalls.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gotomalls.API.APIRequestData;
import com.example.gotomalls.API.RetroServer;
import com.example.gotomalls.Activity.DetailMallActivity;
import com.example.gotomalls.Activity.MainActivity;
import com.example.gotomalls.Activity.UbahActivity;
import com.example.gotomalls.Model.ModelMall;
import com.example.gotomalls.Model.ModelResponse;
import com.example.gotomalls.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterMall extends RecyclerView.Adapter<AdapterMall.VHMall>{
    private Context ctx;
    private List<ModelMall> listmall;

    public AdapterMall(Context ctx, List<ModelMall> listmall) {
        this.ctx = ctx;
        this.listmall = listmall;
    }

    @NonNull
    @Override
    public VHMall onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View varview = LayoutInflater.from(ctx).inflate(R.layout.list_item_mall,parent,false);
        return new VHMall(varview);
    }

    @Override
    public void onBindViewHolder(@NonNull VHMall holder, int position) {
        ModelMall MM = listmall.get(position);
        holder.tvId.setText(MM.getId());
        holder.tvNama.setText(MM.getNama());
        holder.tvLokasi.setText(MM.getLokasi());
        holder.tvUrlmap.setText(MM.getUrlmap());
        holder.tvJam.setText(MM.getJam());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nama = listmall.get(holder.getAdapterPosition()).getNama();
                String lokasi = listmall.get(holder.getAdapterPosition()).getLokasi();
                String urlmap = listmall.get(holder.getAdapterPosition()).getUrlmap();
                String jam = listmall.get(holder.getAdapterPosition()).getJam();

                Intent kirim = new Intent(holder.itemView.getContext(), DetailMallActivity.class);
                kirim.putExtra("varnama",nama);
                kirim.putExtra("varlokasi",lokasi);
                kirim.putExtra("varurlmap",urlmap);
                kirim.putExtra("varjam",jam);
                holder.itemView.getContext().startActivity(kirim);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listmall.size();
    }

    public class VHMall extends RecyclerView.ViewHolder{
        TextView tvId, tvNama, tvLokasi,tvUrlmap,tvJam;
        public VHMall(@NonNull View itemView) {
            super(itemView);

            tvId = itemView.findViewById(R.id.tv_id);
            tvNama = itemView.findViewById(R.id.tv_nama);
            tvLokasi = itemView.findViewById(R.id.tv_lokasi);
            tvUrlmap = itemView.findViewById(R.id.tv_urlmap);
            tvJam = itemView.findViewById(R.id.tv_jam_operasional);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    AlertDialog.Builder pesan= new AlertDialog.Builder(ctx);
                    pesan.setTitle("Perhatian");
                    pesan.setMessage("Anda memilih "+tvNama.getText().toString()+" Operasi apa yang akan dilakukan?");

                    pesan.setPositiveButton("Ubah", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent kirim = new Intent(ctx, UbahActivity.class);
                            kirim.putExtra("xId",tvId.getText().toString());
                            kirim.putExtra("xNama", tvNama.getText().toString());
                            kirim.putExtra("xLokasi", tvLokasi.getText().toString());
                            kirim.putExtra("xUrlmap", tvUrlmap.getText().toString());
                            kirim.putExtra("xjam_operasional", tvJam.getText().toString());
                            ctx.startActivity(kirim);

                        }
                    });
                    pesan.setNegativeButton("Hapus", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            prosesHapus(tvId.getText().toString());
                        }
                    });
                    pesan.show();
                    return false;
                }
            });
        }
        void prosesHapus(String id){
            APIRequestData API = RetroServer.konekRetrofit().create(APIRequestData.class);
            Call<ModelResponse> proses = API.ardDelete(id);

            proses.enqueue(new Callback<ModelResponse>() {
                @Override
                public void onResponse(Call<ModelResponse> call, Response<ModelResponse> response) {
                    String kode = response.body().getKode();
                    String pesan = response.body().getPesan();

                    Toast.makeText(ctx,pesan, Toast.LENGTH_SHORT).show();
                    ((MainActivity)ctx).retrieveMall();
                }

                @Override
                public void onFailure(Call<ModelResponse> call, Throwable t) {
                    Toast.makeText(ctx, "Gagal Menghubingi Server: ", Toast.LENGTH_SHORT).show();

                }
            });
        }
    }
}
