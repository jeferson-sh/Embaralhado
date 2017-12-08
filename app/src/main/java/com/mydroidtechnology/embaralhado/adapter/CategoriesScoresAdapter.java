package com.mydroidtechnology.embaralhado.adapter;

import android.content.Context;

import com.mydroidtechnology.embaralhado.model.Categorie;
import com.mydroidtechnology.embaralhado.model.GenericModel;
import com.mydroidtechnology.embaralhado.persistence.DataBase;

import java.util.List;

/*
 * Created by Jeferson on 12/08/2017.
 */

public class CategoriesScoresAdapter extends GenericAdapter {
    public CategoriesScoresAdapter(Context context, String score) {
        super(context, new DataBase(context).searchMyCategoriesScoresDatabase(score));
    }
}
