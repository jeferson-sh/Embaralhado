package com.mydroidtechnology.embaralhado.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.mydroidtechnology.embaralhado.R;
import com.mydroidtechnology.embaralhado.exceptions.DatabaseException;
import com.mydroidtechnology.embaralhado.models.ContextModel;
import com.mydroidtechnology.embaralhado.models.WordModel;
import com.mydroidtechnology.embaralhado.services.BackgroundMusicService;

public class InsertNewWordActivity extends InsertNewGenericDataActivity {

    protected Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BackgroundMusicService.setStopBackgroundMusicEnable(true);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.register_new_word);
        }
        super.editText.setHint(R.string.hint_edittext_insert_new_word);
        super.savePhotoBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData(v);
            }
        });
        this.bundle = getIntent().getExtras();
    }

    @Override
    public void saveData(View v) {
        try {
            super.validData(v);
            super.dataBase.insertWord(new WordModel(bitmapCaptured, editText.getText().toString().toUpperCase(), bundle.getInt("contextID")));
            setContextModelHaveElements(this.bundle.getInt("contextID"));
            Intent intent = new Intent(InsertNewWordActivity.this, WordsDataManagementActivity.class);
            intent.putExtras(this.bundle);
            BackgroundMusicService.setStopBackgroundMusicEnable(false);
            startActivity(intent);
            finish();
        }catch (DatabaseException e){
            e.printStackTrace();
        }
    }

    private void setContextModelHaveElements(int contextID) {
        try {
            ContextModel contextModel = super.dataBase.searchContextDatabase(contextID);
            if(contextModel.getHasElements().equals("false")){
                contextModel.setHaveElements("true");
                super.dataBase.updateContext(contextModel);
            }

        } catch (DatabaseException e) {
            e.printStackTrace();
        }

    }

    private void startWordsActivity() {
        Intent intent = new Intent(this, WordsDataManagementActivity.class);
        assert this.bundle != null;
        intent.putExtras(this.bundle);
        BackgroundMusicService.setStopBackgroundMusicEnable(false);
        startActivity(intent);
        finish();
    }

    @Override
    protected void startActivityOnBackPressed() {
        startWordsActivity();
    }
}
