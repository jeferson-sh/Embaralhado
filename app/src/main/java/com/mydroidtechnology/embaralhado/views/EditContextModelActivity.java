package com.mydroidtechnology.embaralhado.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.mydroidtechnology.embaralhado.exceptions.DatabaseException;
import com.mydroidtechnology.embaralhado.models.ContextModel;
import com.mydroidtechnology.embaralhado.services.BackgroundMusicService;

import java.io.IOException;


public class EditContextModelActivity extends InsertNewContextActivity {

    private ContextModel contextModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BackgroundMusicService.setStopBackgroundMusicEnable(true);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            int idContext = bundle.getInt("contextModelNameId");
            try {
                contextModel = super.dataBase.searchContextDatabase(idContext);
                super.toolbar.setTitle("Editar contexto " + contextModel.getName());
                super.image.setImageBitmap(contextModel.getImage());
                super.editText.setText(contextModel.getName());
                super.bitmapCaptured = contextModel.getImage();
            } catch (IOException e) {
                Toast.makeText(this,"Desculpe, a palavra selecionada não pode ser encontrada.",Toast.LENGTH_LONG).show();
                e.printStackTrace();
                super.startActivityOnBackPressed();
            }

        }

    }


    @Override
    public void saveData(View v) {
        try {
            super.validData(v);
            this.contextModel.setImage(super.bitmapCaptured);
            this.contextModel.setName(super.editText.getText().toString().toUpperCase());
            super.dataBase.updateContext(contextModel);
            BackgroundMusicService.setStopBackgroundMusicEnable(false);
            startActivity(new Intent(EditContextModelActivity.this, ContextsDataManagementActivity.class));
            finish();
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
    }
}
