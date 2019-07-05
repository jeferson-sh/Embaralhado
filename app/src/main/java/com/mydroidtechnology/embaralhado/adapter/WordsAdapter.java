package com.mydroidtechnology.embaralhado.adapter;

import android.content.Context;

import com.mydroidtechnology.embaralhado.persistence.DataBase;

//This class have responsibility of to instance Score objects in ListViews of WordsDataManagementActivity

public class WordsAdapter extends GenericAdapter{
    private Integer contextID;  //Id references the id Category that Word object is save.

    public WordsAdapter(Context context,Integer contextID) {
        super(context,new DataBase(context).searchWordsDatabase(contextID));
        this.contextID = contextID;
    }
    public Integer getContextID() {
        return contextID;
    }

}
