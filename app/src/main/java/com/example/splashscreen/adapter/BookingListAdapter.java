package com.example.splashscreen.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.splashscreen.R;
import com.example.splashscreen.model.BookingList;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BookingListAdapter extends RecyclerView.Adapter<BookingListAdapter.viewHolders> {

    private Context context;
    private List<BookingList.DataBean> dataBeans;

    public BookingListAdapter(Context context, List<BookingList.DataBean> dataBeans) {
        this.context = context;
        this.dataBeans = dataBeans;
    }

    @NonNull
    @Override
    public viewHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_booking_list, parent, false);
        return new viewHolders(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolders holder, int position) {
        holder.bookinglistNama.setText(dataBeans.get(position).getNama()+"");
        holder.bookinglistService.setText(dataBeans.get(position).getNama_service()+"");
        holder.bookinglistTanggal.setText(dataBeans.get(position).getDate()+"");
    }

    @Override
    public int getItemCount() {
        return (dataBeans != null) ? dataBeans.size() : 0;
    }

    public class viewHolders extends RecyclerView.ViewHolder {
        @BindView(R.id.bookinglistTanggal)
        TextView bookinglistTanggal;
        @BindView(R.id.bookinglistNama)
        TextView bookinglistNama;
        @BindView(R.id.bookinglistService)
        TextView bookinglistService;
        public viewHolders(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
