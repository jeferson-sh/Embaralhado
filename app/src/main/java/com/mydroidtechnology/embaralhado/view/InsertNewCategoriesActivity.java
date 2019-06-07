package com.mydroidtechnology.embaralhado.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.mydroidtechnology.embaralhado.R;
import com.mydroidtechnology.embaralhado.model.Category;
import com.mydroidtechnology.embaralhado.service.BackgroundMusicService;

public class InsertNewCategoriesActivity extends InsertNewGenericDataActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.register_new_categorie);
        }
        super.editText.setHint(R.string.hint_edittext_insert_new_categorie);
        super.savePhotobt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData(v);
            }
        });
    }

    @Override
    protected void saveData(View v) {
        if(super.isValidatedData(v)){
            dataBase.insertCategory(new Category(bitmapCaptured, editText.getText().toString().toUpperCase()));
            BackgroundMusicService.setStopBackgroundMusicEnable(false);
            startActivity(new Intent(InsertNewCategoriesActivity.this, CategoriesDataManagementActivity.class));
            finish();
        }
    }

    protected void startCategorieActivity() {
        Intent intent = new Intent(InsertNewCategoriesActivity.this, CategoriesDataManagementActivity.class);
        BackgroundMusicService.setStopBackgroundMusicEnable(false);
        startActivity(intent);
        finish();
    }

    @Override
    protected void startActivityOnBackPressed() {
        startCategorieActivity();
    }
}
