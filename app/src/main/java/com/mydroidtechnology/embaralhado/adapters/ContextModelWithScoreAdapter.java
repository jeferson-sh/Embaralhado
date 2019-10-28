package com.mydroidtechnology.embaralhado.adapters;

import android.content.Context;

import com.mydroidtechnology.embaralhado.exceptions.DatabaseException;

//This class has responsibility of to instance ContextModel objects in ListView of SelectCategoriesScoresActivity

public class ContextModelWithScoreAdapter extends GenericModelAdapter {
    public ContextModelWithScoreAdapter(Context context, String score) {
        super(context);
        try {
            GenericModelAdapter.setGenericModels(getDataBase().searchMyContextsWithScoresDatabase(score));
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
    }
}
