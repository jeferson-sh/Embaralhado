package com.mydroidtechnology.embaralhado.adapter;

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

/**
 * Created by Jeferson Silva on 08/12/2017.
 */

public class GenericAdapter extends BaseAdapter {
    private List<GenericModel> genericModels;
    private Context context;
    private DataBase dataBase;

    GenericAdapter(Context context, List<? extends GenericModel> genericModels) {
        this.context = context;
        this.dataBase = new DataBase(context);
        try {
            this.genericModels = (List<GenericModel>) genericModels;
        } catch (Exception e) {
            this.genericModels = new ArrayList<>();
        }
    }
    @Override
    public int getCount() {
        return this.genericModels.size();
    }

    @Override
    public Object getItem(int i) {
        return this.genericModels.get(i);
    }

    @Override
    public long getItemId(int i) {
        return this.genericModels.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View layout;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(android.content.Context.LAYOUT_INFLATER_SERVICE);
            layout = inflater.inflate(R.layout.categorie_item_list, null);
        } else {
            layout = view;
        }

        ImageView imageView = (ImageView) layout.findViewById(R.id.image_item);
        TextView textView = (TextView) layout.findViewById(R.id.text_item);

        final GenericModel genericModel = this.genericModels.get(i);
        textView.setText(genericModel.getName());

        imageView.setImageBitmap(genericModel.getImage());

        return layout;
    }

    List<GenericModel> getGenericModels() {
        return genericModels;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    DataBase getDataBase() {
        return dataBase;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
}
