package com.example.splashscreen.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.splashscreen.R;
import com.example.splashscreen.model.Promo;
import com.example.splashscreen.utils.apihelpers.UtilsApi;

import java.util.ArrayList;

public class ViewPagerAdapter extends PagerAdapter {
    private Context context;
    private ArrayList<Promo.DataBean> beans;

    public ViewPagerAdapter(Context context, ArrayList<Promo.DataBean> beans) {
        this.context = context;
        this.beans = beans;
    }

    @Override
    public int getCount() {
        return (beans != null)? beans.size() : 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_item_viewpager, container, false);

        ImageView imageView;
        imageView = view.findViewById(R.id.gambar);

        Promo.DataBean dataBean = beans.get(position);
        Glide
                .with(context)
                .load(UtilsApi.BASE_URL+dataBean.getImages())
                .centerCrop()
                .into(imageView);
        container.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
