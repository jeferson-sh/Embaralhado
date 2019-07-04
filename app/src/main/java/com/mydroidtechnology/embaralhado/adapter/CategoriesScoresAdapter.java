package com.mydroidtechnology.embaralhado.adapter;

import android.content.Context;

import com.mydroidtechnology.embaralhado.persistence.DataBase;

//This class have responsibility of to instance Category objects in ListView of SelectCategoriesScoresActivity

public class CategoriesScoresAdapter extends GenericAdapter {
    public CategoriesScoresAdapter(Context context, String score) {
        super(context, new DataBase(context).searchMyCategoriesWithScoresDatabase(score));
    }
}
