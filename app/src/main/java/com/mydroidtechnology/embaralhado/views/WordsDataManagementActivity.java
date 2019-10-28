package com.mydroidtechnology.embaralhado.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.mydroidtechnology.embaralhado.R;
import com.mydroidtechnology.embaralhado.adapters.EditWordModelAdapter;
import com.mydroidtechnology.embaralhado.adapters.WordsModelAdapter;
import com.mydroidtechnology.embaralhado.services.BackgroundMusicService;

public class WordsDataManagementActivity extends GenericDataManagementActivity {

    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.registered_words);
        }
        super.maxData = 200;
        bundle = getIntent().getExtras();
        assert bundle != null;
        super.editDataAdapter = new EditWordModelAdapter(this, bundle.getInt("contextID"));
        super.adapter = new WordsModelAdapter(this, bundle.getInt("contextID"));
        listview.setAdapter(super.adapter);

    }

    @Override
    protected void startAddDataActivity() {
        if (listview.getAdapter().getCount() <= super.maxData) {
            Intent addWordIntent = new Intent(WordsDataManagementActivity.this, InsertNewWordActivity.class);
            addWordIntent.putExtras(bundle);
            BackgroundMusicService.setStopBackgroundMusicEnable(false);
            startActivity(addWordIntent);
            finish();
        } else {
            Toast.makeText(WordsDataManagementActivity.this,
                    "Desculpe, mas você não pode mais adicionar palavras nesse contexto.", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void startActivityOnBackPressed() {
        if (this.listview.getAdapter().getClass().equals(EditWordModelAdapter.class)) {
            super.setAdapter(toolbar.getMenu().getItem(1));
        } else {
            Intent intent = new Intent(WordsDataManagementActivity.this, ContextsDataManagementActivity.class);
            BackgroundMusicService.setStopBackgroundMusicEnable(false);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void setEditDataAdapter(MenuItem item) {
        super.toolbar.setTitle(R.string.edit_word);
        super.setEditDataAdapter(item);
    }

    @Override
    protected void setAdapter(MenuItem item) {
        super.toolbar.setTitle(R.string.registered_words);
        super.setAdapter(item);
    }
}