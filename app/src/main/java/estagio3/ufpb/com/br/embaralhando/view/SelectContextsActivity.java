package estagio3.ufpb.com.br.embaralhando.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import estagio3.ufpb.com.br.embaralhando.adapter.CategorieAdapter;
import estagio3.ufpb.com.br.embaralhando.R;
import estagio3.ufpb.com.br.embaralhando.model.Categorie;
import estagio3.ufpb.com.br.embaralhando.persistence.DataBase;
import estagio3.ufpb.com.br.embaralhando.util.BackgroundSoundService;

public class SelectContextsActivity extends AppCompatActivity {

    private DataBase dataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_contexts);

        if (BackgroundSoundService.PLAYING)
            startService(new Intent(this, BackgroundSoundService.class));

        //toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_select_categorie);
        setSupportActionBar(toolbar);
        toolbar.inflateMenu(R.menu.main_menu);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.select_categorie);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        //end
        dataBase = new DataBase(this);
        ListView listView = (ListView) findViewById(R.id.listViewWords);
        listView.setAdapter(new CategorieAdapter(this));
        registerForContextMenu(listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CategorieAdapter categorieAdapter = new CategorieAdapter(SelectContextsActivity.this);
                Categorie categorie = (Categorie) categorieAdapter.getItem(position);
                String nameContext = categorie.getName();
                if (dataBase.searchWordsDatabase(nameContext).isEmpty()) {
                    Snackbar.make(view, "Não Existem Palavras Cadastradas!", Snackbar.LENGTH_LONG).setAction("OR", null).show();
                } else {
                    startGame(nameContext);
                }
            }
        });
    }

    private void startMainActivity() {
        Intent intent = new Intent(SelectContextsActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void startGame(String nameContext) {
        Bundle bundle = new Bundle();
        bundle.putString("nameContext", nameContext);
        Intent intent = new Intent(SelectContextsActivity.this, ShuffleGameActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

    private void controlMusic(MenuItem item) {
        if (BackgroundSoundService.PLAYING) {
            item.setIcon(R.drawable.ic_volume_mute_white);
            stopService(new Intent(SelectContextsActivity.this, BackgroundSoundService.class));
            BackgroundSoundService.PLAYING = false;
        } else {
            item.setIcon(R.drawable.ic_volume_up_white);
            startService(new Intent(SelectContextsActivity.this, BackgroundSoundService.class));
            BackgroundSoundService.PLAYING = true;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        if(!BackgroundSoundService.PLAYING) {
            menu.getItem(0).setIcon(R.drawable.ic_volume_mute_white);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startMainActivity();
                return true;
            case R.id.soundControl:
                controlMusic(item);
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onBackPressed() {
        startMainActivity();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}