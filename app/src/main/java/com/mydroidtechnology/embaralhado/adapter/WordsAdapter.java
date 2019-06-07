package com.mydroidtechnology.embaralhado.adapter;

import android.content.Context;

import com.mydroidtechnology.embaralhado.persistence.DataBase;

public class WordsAdapter extends GenericAdapter{
    private Integer contextID;

    public WordsAdapter(Context context,Integer contextID) {
        super(context,new DataBase(context).searchWordsDatabase(contextID));
        this.contextID = contextID;
    }
    public Integer getContextID() {
        return contextID;
    }

    public void setContextID(Integer contextID) {
        this.contextID = contextID;
    }
}
