package estagio3.ufpb.com.br.embaralhando.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

import estagio3.ufpb.com.br.embaralhando.R;
import estagio3.ufpb.com.br.embaralhando.adapter.CategorieWordsAdapter;
import estagio3.ufpb.com.br.embaralhando.model.Categorie;
import estagio3.ufpb.com.br.embaralhando.persistence.DataBase;
import estagio3.ufpb.com.br.embaralhando.util.BackgroundSoundServiceUtil;

public class SelectCategoriesToPlayActivity extends AppCompatActivity {

    private DataBase dataBase;
    private Toolbar toolbar;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_contexts);

        BackgroundSoundServiceUtil.setStopBackgroundMusicEnable(true);


        //toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar_select_categorie);
        setSupportActionBar(toolbar);
        toolbar.inflateMenu(R.menu.main_menu);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.select_categorie);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_toolbar_home);
        }
        //end
        dataBase = new DataBase(this);
        listView = (ListView) findViewById(R.id.listViewWords);
        listView.setAdapter(new CategorieWordsAdapter(this, "true"));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Categorie categorie = (Categorie) listView.getAdapter().getItem(position);
                Integer contextID = categorie.getId();
                if (dataBase.searchWordsDatabase(contextID).isEmpty()) {
                    Snackbar.make(view, "Não Existem Palavras Cadastradas!", Snackbar.LENGTH_LONG).setAction("OR", null).show();
                } else {
                    startGame(contextID);
                }
            }
        });
    }

    private void startMainActivity() {
        Intent intent = new Intent(SelectCategoriesToPlayActivity.this, MainActivity.class);
        BackgroundSoundServiceUtil.setStopBackgroundMusicEnable(false);
        startActivity(intent);
        finish();
    }

    private void startGame(Integer contextID) {
        Bundle bundle = new Bundle();
        bundle.putInt("contextID", contextID);
        Intent intent = new Intent(SelectCategoriesToPlayActivity.this, ShuffleGameActivity.class);
        intent.putExtras(bundle);
        BackgroundSoundServiceUtil.setStopBackgroundMusicEnable(false);
        startActivity(intent);
        finish();
    }

    private void controlMusic(MenuItem item) {
        if (BackgroundSoundServiceUtil.isPlaying()) {
            item.setIcon(R.drawable.ic_volume_mute_white);
            BackgroundSoundServiceUtil.getMediaPlayer().pause();
            BackgroundSoundServiceUtil.setIsPlaying(false);
        } else {
            item.setIcon(R.drawable.ic_volume_up_white);
            BackgroundSoundServiceUtil.getMediaPlayer().start();
            BackgroundSoundServiceUtil.setIsPlaying(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        if (!BackgroundSoundServiceUtil.isPlaying()) {
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
            case R.id.exitGame:
                exitApp();
                return true;
            default:
                return false;
        }
    }

    private void exitApp() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle("Deseja sair do jogo?");
        builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                stopService(new Intent(SelectCategoriesToPlayActivity.this, BackgroundSoundServiceUtil.class));
                finish();
                System.exit(0);
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();

    }


    @Override
    public void onBackPressed() {
        startMainActivity();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public DataBase getDataBase() {
        return dataBase;
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    public ListView getListView() {
        return listView;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (BackgroundSoundServiceUtil.getMediaPlayer() != null && BackgroundSoundServiceUtil.isStopBackgroundMusicEnable())
            BackgroundSoundServiceUtil.getMediaPlayer().pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (BackgroundSoundServiceUtil.getMediaPlayer() != null && BackgroundSoundServiceUtil.isPlaying())
            BackgroundSoundServiceUtil.getMediaPlayer().start();
    }
}