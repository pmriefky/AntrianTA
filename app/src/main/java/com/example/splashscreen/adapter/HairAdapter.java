package com.example.splashscreen.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.splashscreen.R;
import com.example.splashscreen.model.HairCut;
import com.example.splashscreen.utils.apihelpers.UtilsApi;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HairAdapter extends RecyclerView.Adapter<HairAdapter.viewHolder> {

    private Context context;
    private List<HairCut.DataBean> dataBeans;

    public HairAdapter(Context context, List<HairCut.DataBean> dataBeans) {
        this.context = context;
        this.dataBeans = dataBeans;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_recycler_main, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        Glide
                .with(context)
                .load(UtilsApi.BASE_URL+dataBeans.get(position).getGambar())
                .centerCrop()
                .into(holder.imgMainService);
    }

    @Override
    public int getItemCount() {
        return (dataBeans != null)? dataBeans.size() : 0;
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imgMainService)
        ImageView imgMainService;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
