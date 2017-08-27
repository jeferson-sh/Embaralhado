package com.mydroidtechnology.embaralhado.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.mydroidtechnology.embaralhado.adapter.CategorieAdapter;
import com.mydroidtechnology.embaralhado.adapter.EditCategorieAdapter;
import com.mydroidtechnology.embaralhado.R;
import com.mydroidtechnology.embaralhado.model.Categorie;
import com.mydroidtechnology.embaralhado.service.BackgroundMusicService;

public class CategoriesActivity extends AppCompatActivity {

    private ListView listView;
    private ImageButton addCategoriebt;
    private static final int MAX_COUNT_CONTEXTS = 20;
    private Toolbar toolbar;
    private EditCategorieAdapter editCategorieAdapter;
    private CategorieAdapter categorieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        this.addCategoriebt = (ImageButton) findViewById(R.id.add_categoriebt);
        BackgroundMusicService.setStopBackgroundMusicEnable(true);
        //toolbar
        this.toolbar = (Toolbar) findViewById(R.id.toobar_categoie);
        setSupportActionBar(toolbar);
        toolbar.inflateMenu(R.menu.edit_menu);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.registered_categories);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_toolbar_home);
        }
        //Adapters
        this.editCategorieAdapter = new EditCategorieAdapter(this);

        this.categorieAdapter = new CategorieAdapter(this);
        //end
        this.listView = (ListView) findViewById(R.id.listViewWords);
        this.listView.setAdapter(categorieAdapter);
        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CategorieAdapter categorieAdapter = new CategorieAdapter(CategoriesActivity.this);
                Categorie categorie = (Categorie) categorieAdapter.getItem(position);
                Integer contextID = categorie.getId();
                Bundle bundle = new Bundle();
                bundle.putInt("contextID", contextID);
                Intent intent = new Intent(CategoriesActivity.this, WordsActivity.class);
                intent.putExtras(bundle);
                BackgroundMusicService.setStopBackgroundMusicEnable(false);
                startActivity(intent);
                finish();
            }
        });

        this.addCategoriebt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listView.getAdapter().getCount() <= MAX_COUNT_CONTEXTS) {
                    Intent adicionarContext = new Intent(CategoriesActivity.this, InsertNewContextActivity.class);
                    BackgroundMusicService.setStopBackgroundMusicEnable(false);
                    startActivity(adicionarContext);
                    finish();
                } else {
                    Toast.makeText(CategoriesActivity.this, "Memoria Cheia!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void starMainActivity() {
        if (this.listView.getAdapter().getClass().equals(EditCategorieAdapter.class)) {
            setCategorieAdapter(toolbar.getMenu().getItem(1));
        } else {
            Intent intent = new Intent(CategoriesActivity.this, MainActivity.class);
            BackgroundMusicService.setStopBackgroundMusicEnable(false);
            startActivity(intent);
            finish();
        }
    }

    private void controlMusic(MenuItem item) {
        if (BackgroundMusicService.isPlaying()) {
            item.setIcon(R.drawable.ic_volume_mute_white);
            BackgroundMusicService.getMediaPlayer().pause();
            BackgroundMusicService.setIsPlaying(false);
        } else {
            item.setIcon(R.drawable.ic_volume_up_white);
            BackgroundMusicService.getMediaPlayer().start();
            BackgroundMusicService.setIsPlaying(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_menu, menu);
        if (!BackgroundMusicService.isPlaying()) {
            menu.getItem(2).setIcon(R.drawable.ic_volume_mute_white);
        }
        menu.getItem(1).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                starMainActivity();
                return true;
            case R.id.edite_categories:
                setEditCategorieAdapter(item);
                return true;
            case R.id.soundControl:
                controlMusic(item);
                return true;
            case R.id.finish_edite:
                setCategorieAdapter(item);
                return true;
            case R.id.exitGame:
                exitApp();
                return true;
            default:
                return false;
        }
    }

    private void setEditCategorieAdapter(MenuItem item) {
        item.setVisible(false);
        this.addCategoriebt.setVisibility(View.GONE);
        this.addCategoriebt.setEnabled(false);
        this.toolbar.setTitle(R.string.edite_categorie);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
        this.toolbar.getMenu().getItem(1).setVisible(true);
        this.listView.setAdapter(editCategorieAdapter);
    }

    private void setCategorieAdapter(MenuItem item) {
        item.setVisible(false);
        this.addCategoriebt.setVisibility(View.VISIBLE);
        this.addCategoriebt.setEnabled(true);
        this.toolbar.setTitle(R.string.registered_categories);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        this.toolbar.getMenu().getItem(0).setVisible(true);
        this.categorieAdapter = new CategorieAdapter(this);
        this.listView.setAdapter(categorieAdapter);
    }

    private void exitApp() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle("Deseja sair do jogo?");
        builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                stopService(new Intent(CategoriesActivity.this,BackgroundMusicService.class));
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
        starMainActivity();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (BackgroundMusicService.getMediaPlayer() != null && BackgroundMusicService.isStopBackgroundMusicEnable())
            BackgroundMusicService.getMediaPlayer().pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (BackgroundMusicService.getMediaPlayer() != null && BackgroundMusicService.isPlaying())
            BackgroundMusicService.getMediaPlayer().start();
    }
}