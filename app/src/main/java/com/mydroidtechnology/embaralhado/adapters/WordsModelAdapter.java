package com.mydroidtechnology.embaralhado.adapters;

import android.content.Context;

import com.mydroidtechnology.embaralhado.exceptions.DatabaseException;

//This class have responsibility of to instance ScoreModel objects in ListViews of WordsDataManagementActivity

public class WordsModelAdapter extends GenericModelAdapter {
    private Integer contextID;  //Id references the id ContextModel that WordModel object is save.

    public WordsModelAdapter(Context context, Integer contextID) {
        super(context);
        try {
            GenericModelAdapter.setGenericModels(getDataBase().searchWordsDatabase(contextID));
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
        this.contextID = contextID;
    }

    public Integer getContextID() {
        return contextID;
    }

}
