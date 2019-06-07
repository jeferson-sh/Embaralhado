package com.mydroidtechnology.embaralhado.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.mydroidtechnology.embaralhado.model.Category;
import com.mydroidtechnology.embaralhado.service.BackgroundMusicService;


public class EditCategorieActivity extends InsertNewCategoriesActivity {

    private Category category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BackgroundMusicService.setStopBackgroundMusicEnable(true);
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        category = super.dataBase.searchCategoryDatabase(bundle.getInt("categorieName"));
        super.toolbar.setTitle("Editar contexto " + category.getName());
        super.image.setImageBitmap(category.getImage());
        super.editText.setText(category.getName());
        super.bitmapCaptured= category.getImage();

    }

    @Override
    public void saveData(View v) {
        if(super.isValidatedData(v)){
            this.category.setImage(super.bitmapCaptured);
            this.category.setName(super.editText.getText().toString().toUpperCase());
            super.dataBase.updateCategory(category);
            BackgroundMusicService.setStopBackgroundMusicEnable(false);
            startActivity(new Intent(EditCategorieActivity.this, CategoriesDataManagementActivity.class));
            finish();
        }
    }
}
