package com.mydroidtechnology.embaralhado.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.mydroidtechnology.embaralhado.exceptions.DatabaseException;
import com.mydroidtechnology.embaralhado.models.WordModel;
import com.mydroidtechnology.embaralhado.services.BackgroundMusicService;


public class EditWordModelActivity extends InsertNewWordActivity {

    private WordModel wordModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BackgroundMusicService.setStopBackgroundMusicEnable(true);
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        try {
            this.wordModel = super.dataBase.searchWordDatabase(bundle.getInt("wordID"));
            super.toolbar.setTitle("Editar palavra " + wordModel.getName());
            super.image.setImageBitmap(wordModel.getImage());
            super.editText.setText(wordModel.getName());
            super.bitmapCaptured = wordModel.getImage();
        } catch (DatabaseException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void saveData(View v) {
        try {
            super.validData(v);
            this.wordModel.setImage(super.bitmapCaptured);
            this.wordModel.setName(super.editText.getText().toString().toUpperCase());
            super.dataBase.updateWord(wordModel);
            Intent intent = new Intent(EditWordModelActivity.this, WordsDataManagementActivity.class);
            assert super.bundle != null;
            intent.putExtras(super.bundle);
            BackgroundMusicService.setStopBackgroundMusicEnable(false);
            startActivity(intent);
            finish();
        }catch (DatabaseException e){
            e.printStackTrace();
        }

    }

    private void startWordsDataManagementActivity() {
        Intent intent = new Intent(EditWordModelActivity.this, WordsDataManagementActivity.class);
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
