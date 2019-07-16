package com.mydroidtechnology.embaralhado.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.mydroidtechnology.embaralhado.R;
import com.mydroidtechnology.embaralhado.model.Context;
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
        //Context Animals
        Context animals = new Context(ContextCompat.getDrawable(this, R.drawable.animals), "ANIMAIS");
        dataBase.insertContext(animals);
        createAnimals(dataBase.searchContextDatabase(animals.getName()));

        //Context Fruits
        Context fruits = new Context(ContextCompat.getDrawable(this, R.drawable.fruits), "FRUTAS");
        dataBase.insertContext(fruits);
        createFruits(dataBase.searchContextDatabase(fruits.getName()));

        //Context Circus
        Context circus = new Context(ContextCompat.getDrawable(this, R.drawable.circus), "CIRCO");
        dataBase.insertContext(circus);
        createCircus(dataBase.searchContextDatabase(circus.getName()));

        //Cateorie Construction
        Context construction = new Context(ContextCompat.getDrawable(this,R.drawable.construction), "CONSTRUÇÃO");
        dataBase.insertContext(construction);
        createConstruction(dataBase.searchContextDatabase(construction.getName()));

        //Cateorie Kitchen
        Context kitchen = new Context(ContextCompat.getDrawable(this,R.drawable.cozinha), "COZINHA");
        dataBase.insertContext(kitchen);
        createKitchen(dataBase.searchContextDatabase(kitchen.getName()));

        //Cateorie Nature
        Context nature = new Context(ContextCompat.getDrawable(this,R.drawable.natureza), "NATUREZA");
        dataBase.insertContext(nature);
        createNature(dataBase.searchContextDatabase(nature.getName()));

    }

    private void createNature(Context context) {
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.abelha), "ABELHA", context.getId()));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.acude), "AÇUDE", context.getId()));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.arvore), "ÁRVORE", context.getId()));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.cabra), "CABRA", context.getId()));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.flor), "FLOR", context.getId()));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.galo), "GALO", context.getId()));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.goiaba), "GOIABA", context.getId()));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.lua), "LUA", context.getId()));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.manga), "MANGA", context.getId()));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.nuvem), "NUVEM", context.getId()));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.rio), "RIO", context.getId()));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.sol), "SOL", context.getId()));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.vaca), "VACA", context.getId()));
    }

    private void createKitchen(Context context) {
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.fogao), "FOGÃO", context.getId()));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.geladeira), "GELADEIRA", context.getId()));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.colher), "COLHER", context.getId()));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.garfo), "GARFO", context.getId()));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.faca), "FACA", context.getId()));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.prato), "PRATO", context.getId()));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.mesa), "MESA", context.getId()));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.pia), "PIA", context.getId()));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.macarrao), "MACARRÃO", context.getId()));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.feijao), "FEIJÃO", context.getId()));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.arroz), "ARROZ", context.getId()));
    }

    private void createConstruction(Context context) {
        //CONSTRUCTION
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.andaime), "ANDAIME", context.getId()));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.areia), "AREIA", context.getId()));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.balde), "BALDE", context.getId()));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.cimento), "CIMENTO", context.getId()));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.escada), "ESCADA", context.getId()));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.espatula), "ESPÁTULA", context.getId()));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.tijolos), "TIJOLOS", context.getId()));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.torneira), "TORNEIRA", context.getId()));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.pa), "PÁ", context.getId()));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.peneira), "PENEIRA", context.getId()));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.luvas), "LUVAS", context.getId()));
    }

    private void createFruits(Context context) {
        //FRUITS
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.apple), "MAÇÃ", context.getId()));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.grapes), "UVA", context.getId()));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.strawberry), "MORANGO", context.getId()));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.pineapple), "ABACAXI", context.getId()));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.orange), "LARANJA", context.getId()));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.bananas), "BANANA", context.getId()));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.coconut), "COCO", context.getId()));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.pear), "PERA", context.getId()));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.watermelon), "MELANCIA", context.getId()));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.avocado), "ABACATE", context.getId()));

    }

    public void createAnimals(Context context) {
        //ANIMALS
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.esquilo), "ESQUILO", context.getId()));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.cat), "GATO", context.getId()));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.dog), "CACHORRO", context.getId()));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.panda), "PANDA", context.getId()));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.leao), "LEÃO", context.getId()));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.bird), "PÁSSARO", context.getId()));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.duck), "PATO", context.getId()));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.owl), "CORUJA", context.getId()));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.monkey), "MACACO", context.getId()));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.elephant), "ELEFANTE", context.getId()));

    }

    public void createCircus(Context context) {
        //CIRCUS
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.balloon), "BALÃO", context.getId()));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.clown), "PALHAÇO", context.getId()));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.magician), "MÁGICO", context.getId()));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.popcorn), "PIPOCA", context.getId()));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.icecream), "SORVETE", context.getId()));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.ballet), "BAILARINA", context.getId()));


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
