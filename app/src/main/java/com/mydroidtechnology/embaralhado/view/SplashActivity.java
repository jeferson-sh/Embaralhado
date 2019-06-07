package com.mydroidtechnology.embaralhado.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.mydroidtechnology.embaralhado.R;
import com.mydroidtechnology.embaralhado.model.Category;
import com.mydroidtechnology.embaralhado.model.Word;
import com.mydroidtechnology.embaralhado.persistence.DataBase;

public class SplashActivity extends AppCompatActivity implements Runnable {

    private static final long delay = 3000;
    private DataBase dataBase;
    private SharedPreferences sPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        dataBase = new DataBase(this);
        sPreferences = getSharedPreferences("firstRun", MODE_PRIVATE);
        Handler handler = new Handler();
        handler.postDelayed(this, delay);
    }

    @Override
    public void run() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void createCategories() {
        //Category Animals
        Category animals = new Category(ContextCompat.getDrawable(this, R.drawable.animals), "ANIMAIS");
        dataBase.insertCategory(animals);
        createAnimals(dataBase.searchCategoryDatabase(animals.getName()));

        //Category Fruits
        Category fruits = new Category(ContextCompat.getDrawable(this, R.drawable.fruits), "FRUTAS");
        dataBase.insertCategory(fruits);
        createFruits(dataBase.searchCategoryDatabase(fruits.getName()));

        //Category Circus
        Category circus = new Category(ContextCompat.getDrawable(this, R.drawable.circus), "CIRCO");
        dataBase.insertCategory(circus);
        createCircus(dataBase.searchCategoryDatabase(circus.getName()));

        //Cateorie Construction
        Category construction = new Category(ContextCompat.getDrawable(this,R.drawable.construction), "CONSTRUÇÃO");
        dataBase.insertCategory(construction);
        createConstruction(dataBase.searchCategoryDatabase(construction.getName()));

        //Cateorie Kitchen
        Category kitchen = new Category(ContextCompat.getDrawable(this,R.drawable.cozinha), "COZINHA");
        dataBase.insertCategory(kitchen);
        createKitchen(dataBase.searchCategoryDatabase(kitchen.getName()));

        //Cateorie Nature
        Category nature = new Category(ContextCompat.getDrawable(this,R.drawable.natureza), "NATUREZA");
        dataBase.insertCategory(nature);
        createNature(dataBase.searchCategoryDatabase(nature.getName()));

    }

    private void createNature(Category category) {
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.abelha), "ABELHA", category.getId()));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.acude), "AÇUDE", category.getId()));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.arvore), "ÁRVORE", category.getId()));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.cabra), "CABRA", category.getId()));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.flor), "FLOR", category.getId()));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.galo), "GALO", category.getId()));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.goiaba), "GOIABA", category.getId()));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.lua), "LUA", category.getId()));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.manga), "MANGA", category.getId()));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.nuvem), "NUVEM", category.getId()));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.rio), "RIO", category.getId()));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.sol), "SOL", category.getId()));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.vaca), "VACA", category.getId()));
    }

    private void createKitchen(Category category) {
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.fogao), "FOGÃO", category.getId()));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.geladeira), "GELADEIRA", category.getId()));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.colher), "COLHER", category.getId()));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.garfo), "GARFO", category.getId()));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.faca), "FACA", category.getId()));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.prato), "PRATO", category.getId()));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.mesa), "MESA", category.getId()));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.pia), "PIA", category.getId()));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.macarrao), "MACARRÃO", category.getId()));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.feijao), "FEIJÃO", category.getId()));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.arroz), "ARROZ", category.getId()));
    }

    private void createConstruction(Category category) {
        //CONSTRUCTION
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.andaime), "ANDAIME", category.getId()));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.areia), "AREIA", category.getId()));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.balde), "BALDE", category.getId()));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.cimento), "CIMENTO", category.getId()));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.escada), "ESCADA", category.getId()));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.espatula), "ESPÁTULA", category.getId()));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.tijolos), "TIJOLOS", category.getId()));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.torneira), "TORNEIRA", category.getId()));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.pa), "PÁ", category.getId()));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.peneira), "PENEIRA", category.getId()));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.luvas), "LUVAS", category.getId()));
    }

    private void createFruits(Category category) {
        //FRUITS
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.apple), "MAÇÃ", category.getId()));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.grapes), "UVA", category.getId()));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.strawberry), "MORANGO", category.getId()));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.pineapple), "ABACAXI", category.getId()));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.orange), "LARANJA", category.getId()));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.bananas), "BANANA", category.getId()));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.coconut), "COCO", category.getId()));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.pear), "PERA", category.getId()));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.watermelon), "MELANCIA", category.getId()));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.avocado), "ABACATE", category.getId()));

    }

    public void createAnimals(Category category) {
        //ANIMALS
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.esquilo), "ESQUILO", category.getId()));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.cat), "GATO", category.getId()));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.dog), "CACHORRO", category.getId()));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.panda), "PANDA", category.getId()));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.leao), "LEÃO", category.getId()));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.bird), "PÁSSARO", category.getId()));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.duck), "PATO", category.getId()));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.owl), "CORUJA", category.getId()));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.monkey), "MACACO", category.getId()));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.elephant), "ELEFANTE", category.getId()));

    }

    public void createCircus(Category category) {
        //CIRCUS
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.balloon), "BALÃO", category.getId()));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.clown), "PALHAÇO", category.getId()));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.magician), "MÁGICO", category.getId()));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.popcorn), "PIPOCA", category.getId()));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.icecream), "SORVETE", category.getId()));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.ballet), "BAILARINA", category.getId()));


    }

    @Override
    protected void onResume() {
        super.onResume();
        if (sPreferences.getBoolean("firstRun", true)) {
            sPreferences.edit().putBoolean("firstRun", false).apply();
            createCategories();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
