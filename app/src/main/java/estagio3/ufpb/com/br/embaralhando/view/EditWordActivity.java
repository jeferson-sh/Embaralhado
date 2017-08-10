package estagio3.ufpb.com.br.embaralhando.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;

import estagio3.ufpb.com.br.embaralhando.model.Word;

/**
 * Created by Jeferson on 10/08/2017.
 */

public class EditWordActivity extends InsertNewWordActivity {
    private Word word;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        word = getDataBase().searchWordDatabase(bundle.getInt("wordID"));
        getToolbar().setTitle("Editar palavra " + word.getName());
        getImage().setImageBitmap(word.getImage());
        getWord().setText(word.getName());

    }
    @Override
    public void saveWord(View v) {
        boolean verify = verifyWord();
        Bundle bundle = getIntent().getExtras();
        if (getWord().getText().toString().length() >= 2 && getWord().getText().toString().length() <= 10 && verify) {
            if(getPicturePath()!= null){
                this.word.setImage(getBitmap());
            }
            this.word.setName(getWord().getText().toString().toUpperCase());
            getDataBase().updateWord(word);
            Intent intent = new Intent(EditWordActivity.this, WordsActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
            finish();
        } else if (getWord().getText().toString().length() > 10) {
            Snackbar.make(v, "Palavra muito grande!", Snackbar.LENGTH_LONG).setAction("OR", null).show();
        } else if (getWord().getText().toString().length() < 2) {
            Snackbar.make(v, "Palavra muito pequena!", Snackbar.LENGTH_LONG).setAction("OR", null).show();
        } else if (!verify) {
            Snackbar.make(v, "Por favor, cadastre palavras apenas com letras sem espaços ou números!", Snackbar.LENGTH_LONG).setAction("OR", null).show();
        }
    }

    private boolean verifyWord() {
        char[] word = this.word.getName().toCharArray();
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
