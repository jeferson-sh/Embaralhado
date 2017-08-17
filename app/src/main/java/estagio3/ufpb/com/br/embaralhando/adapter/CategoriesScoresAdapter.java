package estagio3.ufpb.com.br.embaralhando.adapter;

import android.content.Context;

import estagio3.ufpb.com.br.embaralhando.persistence.DataBase;

/*
 * Created by Jeferson on 12/08/2017.
 */

public class CategoriesScoresAdapter extends CategorieAdapter {
    public CategoriesScoresAdapter(Context context, String score) {
        super(context,new DataBase(context).searchMyCategoriesScoresDatabase(score));
    }
}
