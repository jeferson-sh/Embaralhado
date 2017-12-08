package com.mydroidtechnology.embaralhado.adapter;

/*
 * Created by Jeferson on 17/11/2016.
 */

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import com.mydroidtechnology.embaralhado.R;
import com.mydroidtechnology.embaralhado.model.Word;
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
