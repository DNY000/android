package com.example.projectandroidbookingtour.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.projectandroidbookingtour.Model.Tour;
import com.example.projectandroidbookingtour.R;

import java.util.ArrayList;

public class TourAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<Tour> arrayList;

    public TourAdapter(Context context, int layout, ArrayList<Tour> arrayList) {
        this.context = context;
        this.layout = layout;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.layout_item_tour, viewGroup, false);
        }
        ImageView img = view.findViewById(R.id.img);
        TextView txtNameTour = view.findViewById(R.id.txtNameTour);
        TextView txtSoNgay = view.findViewById(R.id.txtSoNgay);
        TextView txtGia = view.findViewById(R.id.txtGia);

        img.setImageBitmap(arrayList.get(i).getImg());
        txtNameTour.setText(arrayList.get(i).getTentour());
        txtSoNgay.setText(arrayList.get(i).getSongay()+"");
        txtGia.setText(arrayList.get(i).getGia().toString());

        return view;
    }
}
