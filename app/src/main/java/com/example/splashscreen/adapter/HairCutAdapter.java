package com.example.splashscreen.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.splashscreen.AntrianBarberActivity2;
import com.example.splashscreen.OrderActivity;
import com.example.splashscreen.R;
import com.example.splashscreen.model.HairCut;
import com.example.splashscreen.utils.apihelpers.UtilsApi;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HairCutAdapter extends RecyclerView.Adapter<HairCutAdapter.viewHolder> {



    private Context context;
    private List<HairCut.DataBean> dataBeans;

    public HairCutAdapter(Context context, List<HairCut.DataBean> dataBeans) {
        this.context = context;
        this.dataBeans = dataBeans;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_hair_cut, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final viewHolder holder, final int position) {
        holder.hairName.setText(dataBeans.get(position).getNama_service());
        holder.hairPrice.setText(dataBeans.get(position).getHarga());
        holder.hairKeterangan.setText(dataBeans.get(position).getKeterangan());

        Glide
                .with(context)
                .load(UtilsApi.BASE_URL+dataBeans.get(position).getGambar())
                .centerCrop()
                .into(holder.imgHair);

        holder.cardViewHaircut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, OrderActivity.class);
                intent.putExtra("KODE", dataBeans.get(position).getKode());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (dataBeans != null) ? dataBeans.size() : 0;
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imgHair)
        ImageView imgHair;
        @BindView(R.id.hair_name)
        TextView hairName;
        @BindView(R.id.hair_price)
        TextView hairPrice;
        @BindView(R.id.hair_keterangan)
        TextView hairKeterangan;
        @BindView(R.id.cardViewHaircut)
        CardView cardViewHaircut;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
