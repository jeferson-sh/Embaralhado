package estagio3.ufpb.com.br.embaralhando.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.AdapterView;

import estagio3.ufpb.com.br.embaralhando.model.Categorie;

/**
 * Created by Jeferson on 11/08/2017.
 */

public class SelectCategorieScoreActivity extends SelectCategoriesToPlayActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getToolbar().setTitle("Contextos pontuados");
        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Categorie categorie = (Categorie) getListView().getAdapter().getItem(position);
                Integer contextID = categorie.getId();
                if (getDataBase().searchScoresDatabase(contextID).isEmpty()) {
                    Snackbar.make(view, "Não existem pontuações no contexto selecionado!", Snackbar.LENGTH_LONG).setAction("OR", null).show();
                } else {
                    startScoreActivity(contextID);
                }
            }
        });
    }

    private void startScoreActivity(Integer contextID) {
        Bundle bundle = new Bundle();
        bundle.putInt("contextID", contextID);
        Intent intent = new Intent(SelectCategorieScoreActivity.this, ScoreActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }
}
