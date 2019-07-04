package com.mydroidtechnology.embaralhado.adapter;

import android.content.Context;

import com.mydroidtechnology.embaralhado.persistence.DataBase;

public class CategoryAdapter extends GenericAdapter {

    public CategoryAdapter(Context context) {
        super(context, new DataBase(context).searchMyCategoriesDatabase());
    }
}