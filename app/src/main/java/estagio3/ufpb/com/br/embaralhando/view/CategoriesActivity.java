package estagio3.ufpb.com.br.embaralhando.view;

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

import estagio3.ufpb.com.br.embaralhando.adapter.CategorieAdapter;
import estagio3.ufpb.com.br.embaralhando.adapter.EditCategorieAdapter;
import estagio3.ufpb.com.br.embaralhando.R;
import estagio3.ufpb.com.br.embaralhando.model.Categorie;
import estagio3.ufpb.com.br.embaralhando.util.BackgroundSoundService;

public class CategoriesActivity extends AppCompatActivity {

    private ListView listView;
    private ImageButton addCategoriebt;
    private static final int MAX_COUNT_CONTEXTS = 20;
    private Toolbar toolbar;
    private Menu menu;
    private EditCategorieAdapter editCategorieAdapter;
    private CategorieAdapter categorieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        if (BackgroundSoundService.PLAYING)
            startService(new Intent(this, BackgroundSoundService.class));
        this.addCategoriebt = (ImageButton) findViewById(R.id.add_categoriebt);

        //toolbar
        this.toolbar = (Toolbar) findViewById(R.id.toobar_categoie);
        setSupportActionBar(toolbar);
        toolbar.inflateMenu(R.menu.delete_menu);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.registered_categories);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        //Adapters
        this.editCategorieAdapter = new EditCategorieAdapter(this);
        ;
        this.categorieAdapter = new CategorieAdapter(this);
        //end
        this.listView = (ListView) findViewById(R.id.listViewWords);
        this.listView.setAdapter(categorieAdapter);
        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CategorieAdapter categorieAdapter = new CategorieAdapter(CategoriesActivity.this);
                Categorie categorie = (Categorie) categorieAdapter.getItem(position);
                String nameContext = categorie.getName();
                Bundle bundle = new Bundle();
                bundle.putString("nameContext", nameContext);
                Intent intent = new Intent(CategoriesActivity.this, WordsActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        });

        this.addCategoriebt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listView.getAdapter().getCount() <= MAX_COUNT_CONTEXTS) {
                    Intent adicionarContext = new Intent(CategoriesActivity.this, InsertNewContextActivity.class);
                    startActivity(adicionarContext);
                    finish();
                } else {
                    Toast.makeText(CategoriesActivity.this, "Memoria Cheia!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void starMainActivity() {
        Intent intent = new Intent(CategoriesActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void controlMusic(MenuItem item) {
        if (BackgroundSoundService.PLAYING) {
            item.setIcon(R.drawable.ic_volume_mute_white);
            stopService(new Intent(CategoriesActivity.this, BackgroundSoundService.class));
            BackgroundSoundService.PLAYING = false;
        } else {
            item.setIcon(R.drawable.ic_volume_up_white);
            startService(new Intent(CategoriesActivity.this, BackgroundSoundService.class));
            BackgroundSoundService.PLAYING = true;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.delete_menu, menu);
        this.menu = menu;
        if (!BackgroundSoundService.PLAYING) {
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
            case R.id.delete_button:
                setEditCategorieAdapter(item);
                return true;
            case R.id.soundControl:
                controlMusic(item);
                return true;
            case R.id.finish_delete:
                setCategorieAdapter(item);
                return true;
            default:
                return false;
        }
    }

    private void setEditCategorieAdapter(MenuItem item) {
        item.setVisible(false);
        this.addCategoriebt.setVisibility(View.GONE);
        this.addCategoriebt.setEnabled(false);
        this.toolbar.setTitle(R.string.delete_categorie);
        this.menu.getItem(1).setVisible(true);
        this.listView.setAdapter(editCategorieAdapter);
    }

    private void setCategorieAdapter(MenuItem item) {
        item.setVisible(false);
        this.addCategoriebt.setVisibility(View.VISIBLE);
        this.addCategoriebt.setEnabled(true);
        this.toolbar.setTitle("Contextos cadastrados");
        this.menu.getItem(0).setVisible(true);
        this.categorieAdapter = new CategorieAdapter(this);
        this.listView.setAdapter(categorieAdapter);
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