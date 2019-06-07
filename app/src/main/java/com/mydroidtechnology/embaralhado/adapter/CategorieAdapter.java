package com.mydroidtechnology.embaralhado.adapter;

import android.content.Context;

import com.mydroidtechnology.embaralhado.persistence.DataBase;

public class CategorieAdapter extends GenericAdapter {

    public CategorieAdapter(Context context) {
        super(context, new DataBase(context).searchMyCategoriesDatabase());
    }
}