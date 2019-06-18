package com.mydroidtechnology.embaralhado.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mydroidtechnology.embaralhado.R;
import com.mydroidtechnology.embaralhado.model.GenericModel;
import com.mydroidtechnology.embaralhado.persistence.DataBase;

import java.util.ArrayList;
import java.util.List;

public class GenericAdapter extends BaseAdapter {

    private static List<? extends GenericModel> GENERIC_MODELS;
    private Context context;
    private DataBase dataBase;

    GenericAdapter(Context context, List<? extends GenericModel> genericModels) {
        this.context = context;
        this.dataBase = new DataBase(context);
        try {
            GenericAdapter.GENERIC_MODELS =  genericModels;
        } catch (Exception e) {
            GenericAdapter.GENERIC_MODELS = new ArrayList<>();
        }
    }
    @Override
    public int getCount() {
        return GENERIC_MODELS.size();
    }

    @Override
    public Object getItem(int i) {
        return GENERIC_MODELS.get(i);
    }

    @Override
    public long getItemId(int i) {
        return GENERIC_MODELS.get(i).getId();
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View layout;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(android.content.Context.LAYOUT_INFLATER_SERVICE);
            layout = inflater.inflate(R.layout.data_item_list, null);
        } else {
            layout = view;
        }
        ImageView imageView = layout.findViewById(R.id.image_item);
        TextView textView = layout.findViewById(R.id.text_item);
        final GenericModel genericModel = GENERIC_MODELS.get(i);
        textView.setText(genericModel.getName());
        imageView.setImageBitmap(genericModel.getImage());
        return layout;
    }

    List<? extends GenericModel> getGenericModels() {
        return GENERIC_MODELS;
    }

    public Context getContext() {
        return context;
    }

    DataBase getDataBase() {
        return dataBase;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
}
