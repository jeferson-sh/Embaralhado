package com.mydroidtechnology.embaralhado.adapter;

import android.content.Context;

import com.mydroidtechnology.embaralhado.persistence.DataBase;

// This class have responsibility of to instance Category objects in
// ListView of SelectCategoriesToPlayActivity.

public class CategoriesWordsAdapter extends GenericAdapter {
    public CategoriesWordsAdapter(Context context, String elements) {
        super(context,new DataBase(context).searchMyCategoriesWithWordsDatabase(elements));
    }
}
