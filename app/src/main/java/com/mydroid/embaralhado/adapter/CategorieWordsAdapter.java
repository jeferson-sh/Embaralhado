package com.mydroid.embaralhado.adapter;

import android.content.Context;

import com.mydroid.embaralhado.persistence.DataBase;

/*
 * Created by Jeferson on 12/08/2017.
 */

public class CategorieWordsAdapter extends CategorieAdapter {
    public CategorieWordsAdapter(Context context, String elements) {
        super(context,new DataBase(context).searchMyCategoriesWordsDatabase(elements));
    }
}
