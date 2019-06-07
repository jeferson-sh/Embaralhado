package com.mydroidtechnology.embaralhado.adapter;

import android.content.Context;

import com.mydroidtechnology.embaralhado.persistence.DataBase;

public class ScoreAdapter extends GenericAdapter {

    public ScoreAdapter(Context context, Integer contextID) {
        super(context,new DataBase(context).searchScoresDatabase(contextID));
    }
}
