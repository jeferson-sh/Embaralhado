package com.mydroidtechnology.embaralhado.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.mydroidtechnology.embaralhado.adapter.EditWordAdapter;
import com.mydroidtechnology.embaralhado.R;
import com.mydroidtechnology.embaralhado.adapter.WordsAdapter;
import com.mydroidtechnology.embaralhado.service.BackgroundMusicService;

public class WordsActivity extends AppCompatActivity {

    private ListView listView;
    private ImageButton addWordbt;
    private static final int MAX_COUNT_WORDS = 200;
    private Toolbar toolbar;
    private EditWordAdapter editWordAdapter;
    private WordsAdapter wordsAdapter;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_words);
        BackgroundMusicService.setStopBackgroundMusicEnable(true);

        this.addWordbt = (ImageButton) findViewById(R.id.add_wordbt);

        //toolbar
        this.toolbar = (Toolbar) findViewById(R.id.toolbar_words);
        setSupportActionBar(toolbar);
        toolbar.inflateMenu(R.menu.edit_menu);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.registered_words);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_toolbar_home);
        }
        //adapters
        bundle = getIntent().getExtras();
        this.editWordAdapter = new EditWordAdapter(this, bundle.getInt("contextID"));
        this.wordsAdapter = new WordsAdapter(this, bundle.getInt("contextID"));

        listView = (ListView) findViewById(R.id.listViewWords);
        listView.setAdapter(wordsAdapter);
        this.addWordbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listView.getAdapter().getCount() <= MAX_COUNT_WORDS) {
                    Intent adicionarPalavra = new Intent(WordsActivity.this, InsertNewWordActivity.class);
                    adicionarPalavra.putExtras(bundle);
                    BackgroundMusicService.setStopBackgroundMusicEnable(false);
                    startActivity(adicionarPalavra);
                    finish();
                } else {
                    Toast.makeText(WordsActivity.this, "Memoria Cheia!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void startCategoriesActivity() {
        if (this.listView.getAdapter().getClass().equals(EditWordAdapter.class)) {
            setWordAdapter(toolbar.getMenu().getItem(1));
        } else {
            Intent intent = new Intent(WordsActivity.this, CategoriesActivity.class);
            BackgroundMusicService.setStopBackgroundMusicEnable(false);
            startActivity(intent);
            finish();
        }
    }

    private void startMainActivity() {
        Intent intent = new Intent(WordsActivity.this, MainActivity.class);
        BackgroundMusicService.setStopBackgroundMusicEnable(false);
        startActivity(intent);
        finish();
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
                startMainActivity();
                return true;
            case R.id.edite_categories:
                setEditWordAdapter(item);
                return true;
            case R.id.soundControl:
                controlMusic(item);
                return true;
            case R.id.finish_edite:
                setWordAdapter(item);
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
                finish();
                System.exit(0);
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();

    }


    private void setEditWordAdapter(MenuItem item) {
        item.setVisible(false);
        this.addWordbt.setVisibility(View.GONE);
        this.addWordbt.setEnabled(false);
        this.toolbar.setTitle(R.string.edit_word);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
        this.toolbar.getMenu().getItem(1).setVisible(true);
        this.listView.setAdapter(editWordAdapter);
    }

    private void setWordAdapter(MenuItem item) {
        item.setVisible(false);
        this.addWordbt.setVisibility(View.VISIBLE);
        this.addWordbt.setEnabled(true);
        this.toolbar.setTitle(R.string.registered_words);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        this.toolbar.getMenu().getItem(0).setVisible(true);
        this.wordsAdapter = new WordsAdapter(this, bundle.getInt("contextID"));
        this.listView.setAdapter(wordsAdapter);
    }

    @Override
    public void onBackPressed() {
        startCategoriesActivity();
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