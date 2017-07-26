package estagio3.ufpb.com.br.embaralhando;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class WordsActivity extends AppCompatActivity {

    private ListView listView;
    private ImageButton soundbt;
    private ImageButton addWordbt;
    private DataBase dataBase;
    private ImageButton deleteWordbt;
    private ImageButton finishDeleteCategoriesbt;
    private TextView tooblbarTitle;
    private static final int MAX_COUNT_WORDS = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_words);
        if (BackgroundSoundService.PLAYING)
            startService(new Intent(this, BackgroundSoundService.class));
        this.dataBase = new DataBase(this);
        this.soundbt = (ImageButton) findViewById(R.id.soundButton);
        this.addWordbt = (ImageButton) findViewById(R.id.add_wordbt);
        this.deleteWordbt = (ImageButton) findViewById(R.id.edit_word_bt);
        this.finishDeleteCategoriesbt = (ImageButton) findViewById(R.id.finish_edit_word_bt);
        this.tooblbarTitle = (TextView) findViewById(R.id.toolbar_word_title);
        if (!BackgroundSoundService.PLAYING)
            this.soundbt.setBackgroundResource(R.drawable.ic_volume_mute_white);

        //toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.inflateMenu(R.menu.main_menu);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setOverflowIcon(getResources().getDrawable(R.drawable.menu_button_icon, getTheme()));
        } else {
            toolbar.setOverflowIcon(getResources().getDrawable(R.drawable.menu_button_icon));
        }
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WordsActivity.this, CategorieActivity.class);
                startActivity(intent);
                finish();
            }
        });

        listView = (ListView) findViewById(R.id.listViewWords);
        final Bundle bundle = getIntent().getExtras();
        final WordsAdapter wordsAdapter = new WordsAdapter(this, bundle.getString("nameContext"));
        listView.setAdapter(wordsAdapter);
        this.soundbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controlMusic(v);

            }
        });
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
        final EditWordAdapter editWordAdapter = new EditWordAdapter(WordsActivity.this, bundle.getString("nameContext"));
        this.deleteWordbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteWordbt.setVisibility(View.GONE);
                deleteWordbt.setEnabled(false);
                addWordbt.setVisibility(View.GONE);
                addWordbt.setEnabled(false);
                tooblbarTitle.setText(R.string.deletar_palavras);
                finishDeleteCategoriesbt.setVisibility(View.VISIBLE);
                finishDeleteCategoriesbt.setEnabled(true);
                listView.setAdapter(editWordAdapter);
            }
        });
        this.finishDeleteCategoriesbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteWordbt.setVisibility(View.VISIBLE);
                deleteWordbt.setEnabled(true);
                addWordbt.setVisibility(View.VISIBLE);
                addWordbt.setEnabled(true);
                tooblbarTitle.setText(R.string.contextos_cadastrados_txt);
                finishDeleteCategoriesbt.setVisibility(View.INVISIBLE);
                finishDeleteCategoriesbt.setEnabled(false);
                listView.setAdapter(wordsAdapter);
            }
        });
    }

    private void controlMusic(View v) {
        if (BackgroundSoundService.PLAYING) {
            this.soundbt.setBackgroundResource(R.drawable.ic_volume_mute_white);
            stopService(new Intent(WordsActivity.this, BackgroundSoundService.class));
            BackgroundSoundService.PLAYING = false;
        } else {
            this.soundbt.setBackgroundResource(R.drawable.ic_volume_up_white);
            startService(new Intent(WordsActivity.this, BackgroundSoundService.class));
            BackgroundSoundService.PLAYING = true;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.op1:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.op2:
                return true;
            case R.id.op3:
                intent = new Intent(this, ScoreActivity.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.op4:
                finish();
                System.exit(0);
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onBackPressed() {
        super.moveTaskToBack(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}