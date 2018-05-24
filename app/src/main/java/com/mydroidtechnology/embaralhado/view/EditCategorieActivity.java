package com.mydroidtechnology.embaralhado.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.mydroidtechnology.embaralhado.model.Categorie;
import com.mydroidtechnology.embaralhado.service.BackgroundMusicService;

/*
 * Created by Jeferson on 08/08/2017.
 */

public class EditCategorieActivity extends InsertNewCategoriesActivity {

    private Categorie categorie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BackgroundMusicService.setStopBackgroundMusicEnable(true);
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        categorie = getDataBase().searchCategorieDatabase(bundle.getInt("categorieName"));
        getToolbar().setTitle("Editar contexto " + categorie.getName());
        getImageView().setImageBitmap(categorie.getImage());
        getEditText().setText(categorie.getName());
        setBitmapCaptured(categorie.getImage());

    }

    public void saveContext(View v) {
        boolean verify = verifyWord();
        if (getBitmapCaptured() != null &&getEditText().getText().toString().length() >= 2 && getEditText().getText().toString().length() <= 10 && verify) {
            this.categorie.setImage(getBitmapCaptured());
            this.categorie.setName(getEditText().getText().toString().toUpperCase());
            getDataBase().updateCategorie(categorie);
            BackgroundMusicService.setStopBackgroundMusicEnable(false);
            startActivity(new Intent(EditCategorieActivity.this, CategoriesActivity.class));
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
        if (BackgroundMusicService.getMediaPlayer() != null && BackgroundMusicService.isStopBackgroundMusicEnable())
            BackgroundMusicService.getMediaPlayer().pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (BackgroundMusicService.getMediaPlayer() != null && BackgroundMusicService.isPlaying())
            BackgroundMusicService.getMediaPlayer().start();
    }
}
