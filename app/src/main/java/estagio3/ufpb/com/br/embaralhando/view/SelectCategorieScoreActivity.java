package estagio3.ufpb.com.br.embaralhando.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.AdapterView;

import estagio3.ufpb.com.br.embaralhando.adapter.CategoriesScoresAdapter;
import estagio3.ufpb.com.br.embaralhando.model.Categorie;
import estagio3.ufpb.com.br.embaralhando.util.BackgroundSoundServiceUtil;

/**
 * Created by Jeferson on 11/08/2017.
 */

public class SelectCategorieScoreActivity extends SelectCategoriesToPlayActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BackgroundSoundServiceUtil.STOP_BACKGROUND_MUSIC_ENABLE = true;
        getToolbar().setTitle("Contextos pontuados");
        getListView().setAdapter(new CategoriesScoresAdapter(this, "true"));
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
        BackgroundSoundServiceUtil.STOP_BACKGROUND_MUSIC_ENABLE = false;
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (BackgroundSoundServiceUtil.MEDIA_PLAYER != null && BackgroundSoundServiceUtil.STOP_BACKGROUND_MUSIC_ENABLE)
            BackgroundSoundServiceUtil.MEDIA_PLAYER.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (BackgroundSoundServiceUtil.MEDIA_PLAYER != null && BackgroundSoundServiceUtil.ISPLAYNG)
            BackgroundSoundServiceUtil.MEDIA_PLAYER.start();
    }
}
