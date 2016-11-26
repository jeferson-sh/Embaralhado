package estagio3.ufpb.com.br.embaralhando;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    private ListView listView;
    private ImageButton menubt;
    private ImageButton soundbt;
    private ImageButton addWordbt;
    private DataBase dataBase;
    private static final int MAX_COUNT_WORDS = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        if(BackgroundSoundService.PLAYING)
            startService(new Intent(this,BackgroundSoundService.class));
        this.menubt = (ImageButton) findViewById(R.id.menuButton);
        this.dataBase = new DataBase(this);
        this.soundbt = (ImageButton) findViewById(R.id.soundButton);
        this.addWordbt = (ImageButton) findViewById(R.id.add_wordbt);
        if(!BackgroundSoundService.PLAYING)
            this.soundbt.setBackgroundResource(R.drawable.not_speaker);

        listView = (ListView) findViewById(R.id.listViewWords);
        listView.setAdapter(new WordsAdapter(this));
        registerForContextMenu(listView);

        this.soundbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopMusic(v);

            }
        });
        this.menubt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMenu(v);
            }
        });
        this.addWordbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listView.getAdapter().getCount() <= MAX_COUNT_WORDS) {
                    Intent adicionarPalavra = new Intent(SettingsActivity.this, InsertNewWordActivity.class);
                    startActivity(adicionarPalavra);
                    finish();
                }else{
                    Toast.makeText(SettingsActivity.this,"Memoria Cheia!",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.delete:
                WordsAdapter wordsAdapter = new WordsAdapter(this);
                dataBase.deleteWord((Word) wordsAdapter.getItem(info.position));
                Toast.makeText(this,"Palavra Removida",Toast.LENGTH_LONG).show();
                recreate();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }


    private void showMenu(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.main_menu);
        popup.show();
    }

    private void stopMusic(View v) {
        if(BackgroundSoundService.PLAYING){
            this.soundbt.setBackgroundResource(R.drawable.not_sound_button);
            stopService(new Intent(SettingsActivity.this, BackgroundSoundService.class));
            BackgroundSoundService.PLAYING = false;
        }else{
            this.soundbt.setBackgroundResource(R.drawable.sound_button);
            startService(new Intent(SettingsActivity.this, BackgroundSoundService.class));
            BackgroundSoundService.PLAYING = true;
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.op1:
                Intent intent = new Intent(this,MainActivity.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.op2:
                return true;
            case R.id.op3:
                intent = new Intent(this,ScoreActivity.class);
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
    protected void onDestroy() {
        super.onDestroy();
    }
}