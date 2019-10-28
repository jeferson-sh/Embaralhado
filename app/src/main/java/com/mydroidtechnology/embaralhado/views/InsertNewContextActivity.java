package com.mydroidtechnology.embaralhado.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.mydroidtechnology.embaralhado.R;
import com.mydroidtechnology.embaralhado.exceptions.DatabaseException;
import com.mydroidtechnology.embaralhado.models.ContextModel;
import com.mydroidtechnology.embaralhado.services.BackgroundMusicService;

public class InsertNewContextActivity extends InsertNewGenericDataActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.register_new_categorie);
        }
        super.editText.setHint(R.string.hint_edittext_insert_new_categorie);
        super.savePhotoBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    saveData(v);
            }
        });
    }

    @Override
    protected void saveData(View v) {
        try{
            super.validData(v);
            dataBase.insertContext(new ContextModel(bitmapCaptured, editText.getText().toString().toUpperCase()));
            BackgroundMusicService.setStopBackgroundMusicEnable(false);
            startActivity(new Intent(InsertNewContextActivity.this, ContextsDataManagementActivity.class));
            finish();
        }catch (DatabaseException e){
            e.printStackTrace();
        }
    }

    protected void startCategoriesDataManagementActivity() {
        Intent intent = new Intent(InsertNewContextActivity.this, ContextsDataManagementActivity.class);
        BackgroundMusicService.setStopBackgroundMusicEnable(false);
        startActivity(intent);
        finish();
    }

    @Override
    protected void startActivityOnBackPressed() {
        startCategoriesDataManagementActivity();
    }
}
