package com.mydroidtechnology.embaralhado.adapter;

import android.content.Context;

import com.mydroidtechnology.embaralhado.persistence.DataBase;

// This class have responsibility of to instance Context objects in
// ListView of SelectContextsToPlayActivity.

public class CategoriesWordsAdapter extends GenericAdapter {
    public CategoriesWordsAdapter(Context context, String elements) {
        super(context,new DataBase(context).searchMyContextsWithWordsDatabase(elements));
    }
}
