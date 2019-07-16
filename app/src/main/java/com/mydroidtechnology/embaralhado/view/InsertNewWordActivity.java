package com.mydroidtechnology.embaralhado.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.mydroidtechnology.embaralhado.R;
import com.mydroidtechnology.embaralhado.model.Context;
import com.mydroidtechnology.embaralhado.model.Word;
import com.mydroidtechnology.embaralhado.service.BackgroundMusicService;

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
        super.savePhotobt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData(v);
            }
        });
        this.bundle = getIntent().getExtras();
    }

    @Override
    public void saveData(View v) {
        if(super.isValidData(v)){
            super.dataBase.insertWord(new Word(bitmapCaptured, editText.getText().toString().toUpperCase(), bundle.getInt("contextID")));
            setCategorieHaveElements(this.bundle.getInt("contextID"));
            Intent intent = new Intent(InsertNewWordActivity.this, WordsDataManagementActivity.class);
            intent.putExtras(this.bundle);
            BackgroundMusicService.setStopBackgroundMusicEnable(false);
            startActivity(intent);
            finish();
        }
    }

    private void setCategorieHaveElements(int contextID) {
        Context context = super.dataBase.searchContextDatabase(contextID);
        if(context.getHasElements().equals("false")){
            context.setHaveElements("true");
            super.dataBase.updateContext(context);
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
