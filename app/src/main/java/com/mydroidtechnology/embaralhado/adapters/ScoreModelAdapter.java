package com.mydroidtechnology.embaralhado.adapters;

import android.content.Context;

import com.mydroidtechnology.embaralhado.exceptions.DatabaseException;

//This class have responsibility of to instance ScoreModel objects in ListViews of ScoreModelActivity

public class ScoreModelAdapter extends GenericModelAdapter {

    public ScoreModelAdapter(Context context, Integer contextID) {
        super(context);
        try {
            GenericModelAdapter.setGenericModels(getDataBase().searchScoresDatabase(contextID));
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
    }
}
