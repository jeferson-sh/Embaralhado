package com.mydroidtechnology.embaralhado.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.mydroidtechnology.embaralhado.model.Word;
import com.mydroidtechnology.embaralhado.service.BackgroundMusicService;

/*
 * Created by Jeferson on 10/08/2017.
 */

public class EditWordActivity extends InsertNewWordActivity {

    private Word word;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BackgroundMusicService.setStopBackgroundMusicEnable(true);
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        this.word = super.dataBase.searchWordDatabase(bundle.getInt("wordID"));
        super.toolbar.setTitle("Editar palavra " + word.getName());
        super.image.setImageBitmap(word.getImage());
        super.editText.setText(word.getName());
        super.bitmapCaptured = word.getImage();

    }

    @Override
    public void saveData(View v) {
        if(super.isValidatedData(v)){
            this.word.setImage(super.bitmapCaptured);
            this.word.setName(super.editText.getText().toString().toUpperCase());
            super.dataBase.updateWord(word);
            Intent intent = new Intent(EditWordActivity.this, WordsDataManagementActivity.class);
            assert super.bundle != null;
            intent.putExtras(super.bundle);
            BackgroundMusicService.setStopBackgroundMusicEnable(false);
            startActivity(intent);
            finish();
        }
    }

    private void startWordsDataManagementActivity() {
        Intent intent = new Intent(EditWordActivity.this, WordsDataManagementActivity.class);
        assert super.bundle != null;
        intent.putExtras(super.bundle);
        BackgroundMusicService.setStopBackgroundMusicEnable(false);
        startActivity(intent);
        finish();
    }

    @Override
    protected void startActivityOnBackPressed(){
        startWordsDataManagementActivity();
    }
}
