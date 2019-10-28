package com.mydroidtechnology.embaralhado.adapters;

import android.content.Context;

import com.mydroidtechnology.embaralhado.exceptions.DatabaseException;

public class ContextModelAdapter extends GenericModelAdapter {

    public ContextModelAdapter(Context context){
        super(context);
        try {
            GenericModelAdapter.setGenericModels(getDataBase().searchMyContextsDatabase());
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
    }

}