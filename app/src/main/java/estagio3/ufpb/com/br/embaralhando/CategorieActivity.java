package estagio3.ufpb.com.br.embaralhando;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class CategorieActivity extends AppCompatActivity{

    private ListView listView;
    private ImageButton soundbt;
    private ImageButton addCategoriebt;
    private ImageButton deleteCategoriebt;
    private ImageButton finishDeleteCategoriesbt;
    private TextView tooblbarTitle;
    private static final int MAX_COUNT_CONTEXTS = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        if(BackgroundSoundService.PLAYING)
            startService(new Intent(this,BackgroundSoundService.class));
        this.soundbt = (ImageButton) findViewById(R.id.soundButton);
        this.addCategoriebt = (ImageButton) findViewById(R.id.add_wordbt);
        this.deleteCategoriebt = (ImageButton) findViewById(R.id.edit_categorie_bt);
        this.finishDeleteCategoriesbt = (ImageButton) findViewById(R.id.finish_edit_categorie_bt);
        this.tooblbarTitle = (TextView) findViewById(R.id.toolbar_categorie_title);
        if(!BackgroundSoundService.PLAYING)
            this.soundbt.setBackgroundResource(R.drawable.ic_volume_mute_white);

        //toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.inflateMenu(R.menu.main_menu);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setOverflowIcon(getResources().getDrawable(R.drawable.menu_button_icon,getTheme()));
        }else{
            toolbar.setOverflowIcon(getResources().getDrawable(R.drawable.menu_button_icon));
        }
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                starMainActivity();
            }
        });

        listView = (ListView) findViewById(R.id.listViewWords);
        final CategorieAdapter categorieAdapter = new CategorieAdapter(this);
        listView.setAdapter(categorieAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CategorieAdapter categorieAdapter = new CategorieAdapter(CategorieActivity.this);
                Categorie categorie = (Categorie) categorieAdapter.getItem(position);
                String nameContext = categorie.getName();
                Bundle bundle = new Bundle();
                bundle.putString("nameContext",nameContext);
                Intent intent = new Intent(CategorieActivity.this,WordsActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        });

        this.soundbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controlMusic(v);

            }
        });
        this.addCategoriebt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listView.getAdapter().getCount() <= MAX_COUNT_CONTEXTS) {
                    Intent adicionarContext = new Intent(CategorieActivity.this, InsertNewContextActivity.class);
                    startActivity(adicionarContext);
                    finish();
                }else{
                    Toast.makeText(CategorieActivity.this,"Memoria Cheia!",Toast.LENGTH_LONG).show();
                }
            }
        });
        final EditCategorieAdapter editCategorieAdapter = new EditCategorieAdapter(CategorieActivity.this);
        this.deleteCategoriebt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteCategoriebt.setVisibility(View.GONE);
                deleteCategoriebt.setEnabled(false);
                addCategoriebt.setVisibility(View.GONE);
                addCategoriebt.setEnabled(false);
                tooblbarTitle.setText(R.string.deletar_contextos);
                finishDeleteCategoriesbt.setVisibility(View.VISIBLE);
                finishDeleteCategoriesbt.setEnabled(true);
                listView.setAdapter(editCategorieAdapter);
            }
        });
        this.finishDeleteCategoriesbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteCategoriebt.setVisibility(View.VISIBLE);
                deleteCategoriebt.setEnabled(true);
                addCategoriebt.setVisibility(View.VISIBLE);
                addCategoriebt.setEnabled(true);
                tooblbarTitle.setText(R.string.contextos_cadastrados_txt);
                finishDeleteCategoriesbt.setVisibility(View.INVISIBLE);
                finishDeleteCategoriesbt.setEnabled(false);
                listView.setAdapter(categorieAdapter);
            }
        });
    }

    private void starMainActivity() {
        Intent intent = new Intent(CategorieActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void controlMusic(View v) {
        if(BackgroundSoundService.PLAYING){
            this.soundbt.setBackgroundResource(R.drawable.ic_volume_mute_white);
            stopService(new Intent(CategorieActivity.this, BackgroundSoundService.class));
            BackgroundSoundService.PLAYING = false;
        }else{
            this.soundbt.setBackgroundResource(R.drawable.ic_volume_up_white);
            startService(new Intent(CategorieActivity.this, BackgroundSoundService.class));
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
    public void onBackPressed() {
        starMainActivity();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}