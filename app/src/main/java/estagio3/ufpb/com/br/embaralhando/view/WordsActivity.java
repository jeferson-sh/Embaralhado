package estagio3.ufpb.com.br.embaralhando.view;

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

import estagio3.ufpb.com.br.embaralhando.adapter.EditWordAdapter;
import estagio3.ufpb.com.br.embaralhando.R;
import estagio3.ufpb.com.br.embaralhando.adapter.WordsAdapter;
import estagio3.ufpb.com.br.embaralhando.util.BackgroundSoundService;

public class WordsActivity extends AppCompatActivity {

    private ListView listView;
    private ImageButton addWordbt;
    private static final int MAX_COUNT_WORDS = 200;
    private Toolbar toolbar;
    private Menu menu;
    private EditWordAdapter editWordAdapter;
    private WordsAdapter wordsAdapter;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_words);

        if (BackgroundSoundService.PLAYING)
            startService(new Intent(this, BackgroundSoundService.class));
        this.addWordbt = (ImageButton) findViewById(R.id.add_wordbt);

        //toolbar
        this.toolbar = (Toolbar) findViewById(R.id.toolbar_words);
        setSupportActionBar(toolbar);
        toolbar.inflateMenu(R.menu.delete_menu);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.registered_words);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        //adapters
        bundle = getIntent().getExtras();
        this.editWordAdapter = new EditWordAdapter(this, bundle.getString("nameContext"));
        this.wordsAdapter = new WordsAdapter(this,bundle.getString("nameContext"));

        listView = (ListView) findViewById(R.id.listViewWords);
        listView.setAdapter(wordsAdapter);
        this.addWordbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listView.getAdapter().getCount() <= MAX_COUNT_WORDS) {
                    Intent adicionarPalavra = new Intent(WordsActivity.this, InsertNewWordActivity.class);
                    adicionarPalavra.putExtras(bundle);
                    startActivity(adicionarPalavra);
                    finish();
                } else {
                    Toast.makeText(WordsActivity.this, "Memoria Cheia!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void startCategoriesActivity() {
        Intent intent = new Intent(WordsActivity.this, CategoriesActivity.class);
        startActivity(intent);
        finish();
    }

    private void controlMusic(MenuItem item) {
        if(BackgroundSoundService.PLAYING){
            item.setIcon(R.drawable.ic_volume_mute_white);
            stopService(new Intent(WordsActivity.this, BackgroundSoundService.class));
            BackgroundSoundService.PLAYING = false;
        }else{
            item.setIcon(R.drawable.ic_volume_up_white);
            startService(new Intent(WordsActivity.this, BackgroundSoundService.class));
            BackgroundSoundService.PLAYING = true;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.delete_menu, menu);
        this.menu = menu;
        if(!BackgroundSoundService.PLAYING) {
            menu.getItem(2).setIcon(R.drawable.ic_volume_mute_white);
        }
        menu.getItem(1).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startCategoriesActivity();
                return true;
            case R.id.delete_button:
                setEditWordAdapter(item);
                return true;
            case R.id.soundControl:
                controlMusic(item);
                return true;
            case R.id.finish_delete:
                setWordAdapter(item);
                return true;
            default:
                return false;
        }
    }

    private void setEditWordAdapter(MenuItem item) {
        item.setVisible(false);
        this.addWordbt.setVisibility(View.GONE);
        this.addWordbt.setEnabled(false);
        this.toolbar.setTitle(R.string.delete_word);
        this.menu.getItem(1).setVisible(true);
        this.listView.setAdapter(editWordAdapter);
    }

    private void setWordAdapter(MenuItem item) {
        item.setVisible(false);
        this.addWordbt.setVisibility(View.VISIBLE);
        this.addWordbt.setEnabled(true);
        this.toolbar.setTitle(R.string.registered_words);
        this.menu.getItem(0).setVisible(true);
        this.wordsAdapter = new WordsAdapter(this,bundle.getString("nameContext"));
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
}