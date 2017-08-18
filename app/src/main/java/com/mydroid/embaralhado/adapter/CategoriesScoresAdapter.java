package com.mydroid.embaralhado.adapter;

import android.content.Context;

import com.mydroid.embaralhado.persistence.DataBase;

/*
 * Created by Jeferson on 12/08/2017.
 */

public class CategoriesScoresAdapter extends CategorieAdapter {
    public CategoriesScoresAdapter(Context context, String score) {
        super(context,new DataBase(context).searchMyCategoriesScoresDatabase(score));
    }
}
