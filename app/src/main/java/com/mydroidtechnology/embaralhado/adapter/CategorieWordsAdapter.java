package com.mydroidtechnology.embaralhado.adapter;

import android.content.Context;

import com.mydroidtechnology.embaralhado.persistence.DataBase;

/*
 * Created by Jeferson on 12/08/2017.
 */

public class CategorieWordsAdapter extends CategorieAdapter {
    public CategorieWordsAdapter(Context context, String elements) {
        super(context,new DataBase(context).searchMyCategoriesWordsDatabase(elements));
    }
}
