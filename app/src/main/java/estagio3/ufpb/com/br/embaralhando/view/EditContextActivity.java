package estagio3.ufpb.com.br.embaralhando.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;

import estagio3.ufpb.com.br.embaralhando.model.Categorie;
import estagio3.ufpb.com.br.embaralhando.util.BackgroundSoundServiceUtil;

/**
 * Created by Jeferson on 08/08/2017.
 */

public class EditContextActivity extends InsertNewContextActivity {

    private Categorie categorie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BackgroundSoundServiceUtil.setStopBackgroundMusicEnable(true);
        Bundle bundle = getIntent().getExtras();
        categorie = getDataBase().searchCategorieDatabase(bundle.getInt("categorieName"));
        getToolbar().setTitle("Editar contexto " + categorie.getName());
        getImage().setImageBitmap(categorie.getImage());
        getEditText().setText(categorie.getName());

    }

    public void saveContext(View v) {
        boolean verify = verifyWord();
        if (getEditText().getText().toString().length() >= 2 && getEditText().getText().toString().length() <= 10 && verify) {
            this.categorie.setImage(getBitmapCaptured());
            this.categorie.setName(getEditText().getText().toString().toUpperCase());
            getDataBase().updateCategorie(categorie);
            BackgroundSoundServiceUtil.setStopBackgroundMusicEnable(false);
            startActivity(new Intent(EditContextActivity.this, CategoriesActivity.class));
            finish();
        } else if (getEditText().getText().toString().length() > 10) {
            Snackbar.make(v, "Palavra muito grande!", Snackbar.LENGTH_LONG).setAction("OR", null).show();
        } else if (getEditText().getText().toString().length() < 2) {
            Snackbar.make(v, "Palavra muito pequena!", Snackbar.LENGTH_LONG).setAction("OR", null).show();
        } else if (!verify) {
            Snackbar.make(v, "Por favor, cadastre palavras apenas com letras sem espaços ou números!", Snackbar.LENGTH_LONG).setAction("OR", null).show();
        }
    }

    protected boolean verifyWord() {
        char[] word = categorie.getName().toCharArray();
        boolean b = true;
        for (char aWord : word) {
            if (!Character.isLetter(aWord)) {
                b = false;
                break;
            }
        }
        return b;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (BackgroundSoundServiceUtil.getMediaPlayer() != null && BackgroundSoundServiceUtil.isStopBackgroundMusicEnable())
            BackgroundSoundServiceUtil.getMediaPlayer().pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (BackgroundSoundServiceUtil.getMediaPlayer() != null && BackgroundSoundServiceUtil.isPlaying())
            BackgroundSoundServiceUtil.getMediaPlayer().start();
    }
}
