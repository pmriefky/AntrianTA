package com.example.splashscreen.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.splashscreen.R;
import com.example.splashscreen.model.ModelRambut;
import com.example.splashscreen.utils.apihelpers.UtilsApi;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ModelRambutAdapter extends RecyclerView.Adapter<ModelRambutAdapter.viewHolder> {

    private Context context;
    private List<ModelRambut.DataBean> modelBeans;

    public ModelRambutAdapter(Context context, List<ModelRambut.DataBean> modelBeans) {
        this.context = context;
        this.modelBeans = modelBeans;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_model_hair, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        holder.hairName.setText(modelBeans.get(position).getNama_rambut());
        holder.hairKeterangan.setText(modelBeans.get(position).getKeterangan());

        Glide
                .with(context)
                .load(UtilsApi.BASE_URL+modelBeans.get(position).getGambar())
                .centerCrop()
                .into(holder.imgHairModel);


    }

    @Override
    public int getItemCount() { return (modelBeans != null) ? modelBeans.size() : 0 ; }

    public class viewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imgHairModel)
        ImageView imgHairModel;
        @BindView(R.id.hair_name)
        TextView hairName;
        @BindView(R.id.hair_keterangan)
        TextView hairKeterangan;
        @BindView(R.id.cardViewModelHair)
        CardView cardViewModelHair;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
