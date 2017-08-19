package com.mydroidtechnology.embaralhado.adapter;

import android.content.Context;

import com.mydroidtechnology.embaralhado.persistence.DataBase;

/*
 * Created by Jeferson on 12/08/2017.
 */

public class CategoriesScoresAdapter extends CategorieAdapter {
    public CategoriesScoresAdapter(Context context, String score) {
        super(context,new DataBase(context).searchMyCategoriesScoresDatabase(score));
    }
}
