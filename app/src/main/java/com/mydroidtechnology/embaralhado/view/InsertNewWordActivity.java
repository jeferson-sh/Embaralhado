package com.mydroidtechnology.embaralhado.view;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.os.Bundle;
import android.view.View;

import com.mydroidtechnology.embaralhado.R;
import com.mydroidtechnology.embaralhado.model.Categorie;
import com.mydroidtechnology.embaralhado.model.Word;
import com.mydroidtechnology.embaralhado.service.BackgroundMusicService;

public class InsertNewWordActivity extends InsertNewContextActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        BackgroundMusicService.setStopBackgroundMusicEnable(true);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.register_new_word);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        getEditText().setHint("Digite a palavra aqui");


        getSavePhotobt().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveWord(v);
            }
        });
    }

    private void startWordsActivity() {
        Bundle bundle = getIntent().getExtras();
        Intent intent = new Intent(InsertNewWordActivity.this, WordsActivity.class);
        intent.putExtras(bundle);
        BackgroundMusicService.setStopBackgroundMusicEnable(false);
        startActivity(intent);
        finish();
    }

    public void saveWord(View v) {
        boolean verify = verifyWord();
        Bundle bundle = getIntent().getExtras();
        if (getBitmapCaptured() != null && getEditText().getText().toString().length() >= 2 && getEditText().getText().toString().length() <= 10 && verify) {
            getDataBase().insertWord(new Word(getBitmapCaptured(), getEditText().getText().toString().toUpperCase(), bundle.getInt("contextID")));
            updateCategorie(bundle.getInt("contextID"));
            Intent intent = new Intent(InsertNewWordActivity.this, WordsActivity.class);
            intent.putExtras(bundle);
            BackgroundMusicService.setStopBackgroundMusicEnable(false);
            startActivity(intent);
            finish();
        } else if (getEditText().getText().toString().length() > 10) {
            Snackbar.make(v, "Palavra muito grande!", Snackbar.LENGTH_LONG).setAction("OR", null).show();
        } else if (getEditText().getText().toString().length() < 2) {
            Snackbar.make(v, "Palavra muito pequena!", Snackbar.LENGTH_LONG).setAction("OR", null).show();
        } else if (!verify) {
            Snackbar.make(v, "Por favor, cadastre palavras apenas com letras sem espaços ou números!", Snackbar.LENGTH_LONG).setAction("OR", null).show();
        } else if (getBitmapCaptured() == null) {
            Snackbar.make(v, "Por favor, insira uma foto da galeria ou use a câmera!", Snackbar.LENGTH_LONG).setAction("OR", null).show();
        }


    }

    private void updateCategorie(int contextID) {
        Categorie categorie = getDataBase().searchCategorieDatabase(contextID);
        categorie.setElements("true");
        getDataBase().updateCategorie(categorie);
    }

    @Override
    public void onBackPressed() {
        startWordsActivity();
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
