package com.mydroidtechnology.embaralhado.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.mydroidtechnology.embaralhado.model.Context;
import com.mydroidtechnology.embaralhado.service.BackgroundMusicService;


public class EditContextActivity extends InsertNewContextActivity {

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BackgroundMusicService.setStopBackgroundMusicEnable(true);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            context = super.dataBase.searchContextDatabase(bundle.getInt("categoryName"));
        }
        super.toolbar.setTitle("Editar contexto " + context.getName());
        super.image.setImageBitmap(context.getImage());
        super.editText.setText(context.getName());
        super.bitmapCaptured= context.getImage();

    }

    @Override
    public void saveData(View v) {
        if(super.isValidData(v)){
            this.context.setImage(super.bitmapCaptured);
            this.context.setName(super.editText.getText().toString().toUpperCase());
            super.dataBase.updateContext(context);
            BackgroundMusicService.setStopBackgroundMusicEnable(false);
            startActivity(new Intent(EditContextActivity.this, ContextsDataManagementActivity.class));
            finish();
        }
    }
}
