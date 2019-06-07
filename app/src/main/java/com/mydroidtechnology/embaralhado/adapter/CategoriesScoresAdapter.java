package com.mydroidtechnology.embaralhado.adapter;

import android.content.Context;

import com.mydroidtechnology.embaralhado.persistence.DataBase;

public class CategoriesScoresAdapter extends GenericAdapter {
    public CategoriesScoresAdapter(Context context, String score) {
        super(context, new DataBase(context).searchMyCategoriesWithScoresDatabase(score));
    }
}
