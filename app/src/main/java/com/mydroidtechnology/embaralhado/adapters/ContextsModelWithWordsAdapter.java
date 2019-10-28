package com.mydroidtechnology.embaralhado.adapters;

import android.content.Context;

import com.mydroidtechnology.embaralhado.exceptions.DatabaseException;

// This class have responsibility of to instance ContextModel objects in
// ListView of SelectContextsToPlayActivity.

public class ContextsModelWithWordsAdapter extends GenericModelAdapter {
    public ContextsModelWithWordsAdapter(Context context, String elements) {
        super(context);
        try {
            GenericModelAdapter.setGenericModels(getDataBase().searchMyContextsWithWordsDatabase(elements));
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
    }
}
