package com.mydroidtechnology.embaralhado.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.mydroidtechnology.embaralhado.model.Word;
import com.mydroidtechnology.embaralhado.service.BackgroundMusicService;

/*
 * Created by Jeferson on 10/08/2017.
 */

public class EditWordActivity extends InsertNewContextActivity {
    private Word word;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BackgroundMusicService.setStopBackgroundMusicEnable(true);
        Bundle bundle = getIntent().getExtras();
        word = getDataBase().searchWordDatabase(bundle.getInt("wordID"));
        getToolbar().setTitle("Editar palavra " + word.getName());
        getImageView().setImageBitmap(word.getImage());
        getEditText().setText(word.getName());
        setBitmapCaptured(word.getImage());

    }

    @Override
    public void saveContext(View v) {
        boolean verify = verifyWord();
        Bundle bundle = getIntent().getExtras();
        if (getBitmapCaptured() != null && getEditText().getText().toString().length() >= 2 && getEditText().getText().toString().length() <= 10 && verify) {
            this.word.setImage(getBitmapCaptured());
            this.word.setName(getEditText().getText().toString().toUpperCase());
            getDataBase().updateWord(word);
            Intent intent = new Intent(EditWordActivity.this, WordsActivity.class);
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
        }
    }

    protected boolean verifyWord() {
        char[] word = this.word.getName().toCharArray();
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
    public void onBackPressed() {
        Bundle bundle = getIntent().getExtras();
        Intent intent = new Intent(EditWordActivity.this, WordsActivity.class);
        intent.putExtras(bundle);
        BackgroundMusicService.setStopBackgroundMusicEnable(false);
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
