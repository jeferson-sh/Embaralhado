package com.mydroidtechnology.embaralhado.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mydroidtechnology.embaralhado.R;
import com.mydroidtechnology.embaralhado.models.GenericModel;
import com.mydroidtechnology.embaralhado.persistence.DataBase;

import java.util.ArrayList;
import java.util.List;

//This Class is a Generic Adapter for to instance Objects (Categories, Words and Scores) in ListViews.
public class GenericModelAdapter extends BaseAdapter {

    private static List<? extends GenericModel> GENERIC_MODELS;
    private Context context;
    private DataBase dataBase;

    GenericModelAdapter(Context context) {
        this.context = context;
        this.dataBase = new DataBase(context);
        GENERIC_MODELS = new ArrayList<>();
    }

    static void setGenericModels(List<? extends GenericModel> genericModels) {
        GENERIC_MODELS = genericModels;
    }

    //Return the count of instances in List.
    @Override
    public int getCount() {
        return GENERIC_MODELS.size();
    }

    //Return a GenericModel from index of List.
    @Override
    public GenericModel getItem(int i) {
        return GENERIC_MODELS.get(i);
    }

    //Return an id of GenericModel from index of List.
    @Override
    public long getItemId(int i) {
        return GENERIC_MODELS.get(i).getId();
    }

    //This method does inflate the layout of items (Categories, Words and Scores) in ListViews.
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

    Context getContext() {
        return context;
    }

    DataBase getDataBase() {
        return dataBase;
    }

    //Notify ListView that was occurred changes.
    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
}
