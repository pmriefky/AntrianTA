package com.example.splashscreen.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.splashscreen.R;
import com.example.splashscreen.model.HairCut;

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
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        holder.hairName.setText(dataBeans.get(position).getNama_service());
        holder.hairPrice.setText(dataBeans.get(position).getHarga());
        holder.hairKeterangan.setText(dataBeans.get(position).getKeterangan());
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
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
