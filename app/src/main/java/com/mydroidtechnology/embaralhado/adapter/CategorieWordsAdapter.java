package com.mydroidtechnology.embaralhado.adapter;

import android.content.Context;

import com.mydroidtechnology.embaralhado.persistence.DataBase;

public class CategorieWordsAdapter extends GenericAdapter {
    public CategorieWordsAdapter(Context context, String elements) {
        super(context,new DataBase(context).searchMyCategoriesWithWordsDatabase(elements));
    }
}
