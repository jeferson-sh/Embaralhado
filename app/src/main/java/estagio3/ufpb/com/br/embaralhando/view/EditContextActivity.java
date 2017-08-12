package estagio3.ufpb.com.br.embaralhando.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;

import estagio3.ufpb.com.br.embaralhando.model.Categorie;

/**
 * Created by Jeferson on 08/08/2017.
 */

public class EditContextActivity extends InsertNewContextActivity {

    private Categorie categorie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        categorie = getDataBase().searchCategorieDatabase(bundle.getInt("categorieName"));
        getToolbar().setTitle("Editar contexto " + categorie.getName());
        getImage().setImageBitmap(categorie.getImage());
        getContextName().setText(categorie.getName());

    }

    public void saveContext(View v) {
        boolean verify = verifyWord();
        if (getContextName().getText().toString().length() >= 2 && getContextName().getText().toString().length() <= 10 && verify) {
            if(getPicturePath()!= null){
                this.categorie.setImage(getBitmapCaptured());
            }
            this.categorie.setName(getContextName().getText().toString().toUpperCase());
            getDataBase().updateCategorie(categorie);
            startActivity(new Intent(EditContextActivity.this, CategoriesActivity.class));
            finish();
        } else if (getContextName().getText().toString().length() > 10) {
            Snackbar.make(v, "Palavra muito grande!", Snackbar.LENGTH_LONG).setAction("OR", null).show();
        } else if (getContextName().getText().toString().length() < 2) {
            Snackbar.make(v, "Palavra muito pequena!", Snackbar.LENGTH_LONG).setAction("OR", null).show();
        } else if (!verify) {
            Snackbar.make(v, "Por favor, cadastre palavras apenas com letras sem espaços ou números!", Snackbar.LENGTH_LONG).setAction("OR", null).show();
        }
    }

    private boolean verifyWord() {
        char[] word = categorie.getName().toCharArray();
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
    protected void onDestroy() {
        super.onDestroy();
    }
}
