package com.mydroidtechnology.embaralhado.views;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.mydroidtechnology.embaralhado.R;
import com.mydroidtechnology.embaralhado.exceptions.DatabaseException;
import com.mydroidtechnology.embaralhado.models.ContextModel;
import com.mydroidtechnology.embaralhado.models.WordModel;
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
        try {
            //ContextModel Animals
            ContextModel animals = new ContextModel(ContextCompat.getDrawable(this, R.drawable.animals), "ANIMAIS");
            dataBase.insertContext(animals);
            createAnimals(dataBase.searchContextDatabase(animals.getName()));

            //ContextModel Fruits
            ContextModel fruits = new ContextModel(ContextCompat.getDrawable(this, R.drawable.fruits), "FRUTAS");
            dataBase.insertContext(fruits);
            createFruits(dataBase.searchContextDatabase(fruits.getName()));

            //ContextModel Circus
            ContextModel circus = new ContextModel(ContextCompat.getDrawable(this, R.drawable.circus), "CIRCO");
            dataBase.insertContext(circus);
            createCircus(dataBase.searchContextDatabase(circus.getName()));

            //Cateorie Construction
            ContextModel construction = new ContextModel(ContextCompat.getDrawable(this, R.drawable.construction), "CONSTRUÇÃO");
            dataBase.insertContext(construction);
            createConstruction(dataBase.searchContextDatabase(construction.getName()));

            //Cateorie Kitchen
            ContextModel kitchen = new ContextModel(ContextCompat.getDrawable(this, R.drawable.cozinha), "COZINHA");
            dataBase.insertContext(kitchen);
            createKitchen(dataBase.searchContextDatabase(kitchen.getName()));

            //Cateorie Nature
            ContextModel nature = new ContextModel(ContextCompat.getDrawable(this, R.drawable.natureza), "NATUREZA");
            dataBase.insertContext(nature);
            createNature(dataBase.searchContextDatabase(nature.getName()));

        } catch (DatabaseException e) {
            e.printStackTrace();
        }
    }

    private void createNature(ContextModel contextModel) {
        try {
            dataBase.insertWord(new WordModel(ContextCompat.getDrawable(this, R.drawable.abelha), "ABELHA", contextModel.getId()));
            dataBase.insertWord(new WordModel(ContextCompat.getDrawable(this, R.drawable.acude), "AÇUDE", contextModel.getId()));
            dataBase.insertWord(new WordModel(ContextCompat.getDrawable(this, R.drawable.arvore), "ÁRVORE", contextModel.getId()));
            dataBase.insertWord(new WordModel(ContextCompat.getDrawable(this, R.drawable.cabra), "CABRA", contextModel.getId()));
            dataBase.insertWord(new WordModel(ContextCompat.getDrawable(this, R.drawable.flor), "FLOR", contextModel.getId()));
            dataBase.insertWord(new WordModel(ContextCompat.getDrawable(this, R.drawable.galo), "GALO", contextModel.getId()));
            dataBase.insertWord(new WordModel(ContextCompat.getDrawable(this, R.drawable.goiaba), "GOIABA", contextModel.getId()));
            dataBase.insertWord(new WordModel(ContextCompat.getDrawable(this, R.drawable.lua), "LUA", contextModel.getId()));
            dataBase.insertWord(new WordModel(ContextCompat.getDrawable(this, R.drawable.manga), "MANGA", contextModel.getId()));
            dataBase.insertWord(new WordModel(ContextCompat.getDrawable(this, R.drawable.nuvem), "NUVEM", contextModel.getId()));
            dataBase.insertWord(new WordModel(ContextCompat.getDrawable(this, R.drawable.rio), "RIO", contextModel.getId()));
            dataBase.insertWord(new WordModel(ContextCompat.getDrawable(this, R.drawable.sol), "SOL", contextModel.getId()));
            dataBase.insertWord(new WordModel(ContextCompat.getDrawable(this, R.drawable.vaca), "VACA", contextModel.getId()));
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
    }

    private void createKitchen(ContextModel contextModel) {
        try {
            dataBase.insertWord(new WordModel(ContextCompat.getDrawable(this, R.drawable.fogao), "FOGÃO", contextModel.getId()));
            dataBase.insertWord(new WordModel(ContextCompat.getDrawable(this, R.drawable.geladeira), "GELADEIRA", contextModel.getId()));
            dataBase.insertWord(new WordModel(ContextCompat.getDrawable(this, R.drawable.colher), "COLHER", contextModel.getId()));
            dataBase.insertWord(new WordModel(ContextCompat.getDrawable(this, R.drawable.garfo), "GARFO", contextModel.getId()));
            dataBase.insertWord(new WordModel(ContextCompat.getDrawable(this, R.drawable.faca), "FACA", contextModel.getId()));
            dataBase.insertWord(new WordModel(ContextCompat.getDrawable(this, R.drawable.prato), "PRATO", contextModel.getId()));
            dataBase.insertWord(new WordModel(ContextCompat.getDrawable(this, R.drawable.mesa), "MESA", contextModel.getId()));
            dataBase.insertWord(new WordModel(ContextCompat.getDrawable(this, R.drawable.pia), "PIA", contextModel.getId()));
            dataBase.insertWord(new WordModel(ContextCompat.getDrawable(this, R.drawable.macarrao), "MACARRÃO", contextModel.getId()));
            dataBase.insertWord(new WordModel(ContextCompat.getDrawable(this, R.drawable.feijao), "FEIJÃO", contextModel.getId()));
            dataBase.insertWord(new WordModel(ContextCompat.getDrawable(this, R.drawable.arroz), "ARROZ", contextModel.getId()));
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
    }

    private void createConstruction(ContextModel contextModel) {
        try {
            //CONSTRUCTION
            dataBase.insertWord(new WordModel(ContextCompat.getDrawable(this, R.drawable.andaime), "ANDAIME", contextModel.getId()));
            dataBase.insertWord(new WordModel(ContextCompat.getDrawable(this, R.drawable.areia), "AREIA", contextModel.getId()));
            dataBase.insertWord(new WordModel(ContextCompat.getDrawable(this, R.drawable.balde), "BALDE", contextModel.getId()));
            dataBase.insertWord(new WordModel(ContextCompat.getDrawable(this, R.drawable.cimento), "CIMENTO", contextModel.getId()));
            dataBase.insertWord(new WordModel(ContextCompat.getDrawable(this, R.drawable.escada), "ESCADA", contextModel.getId()));
            dataBase.insertWord(new WordModel(ContextCompat.getDrawable(this, R.drawable.espatula), "ESPÁTULA", contextModel.getId()));
            dataBase.insertWord(new WordModel(ContextCompat.getDrawable(this, R.drawable.tijolos), "TIJOLOS", contextModel.getId()));
            dataBase.insertWord(new WordModel(ContextCompat.getDrawable(this, R.drawable.torneira), "TORNEIRA", contextModel.getId()));
            dataBase.insertWord(new WordModel(ContextCompat.getDrawable(this, R.drawable.pa), "PÁ", contextModel.getId()));
            dataBase.insertWord(new WordModel(ContextCompat.getDrawable(this, R.drawable.peneira), "PENEIRA", contextModel.getId()));
            dataBase.insertWord(new WordModel(ContextCompat.getDrawable(this, R.drawable.luvas), "LUVAS", contextModel.getId()));
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
    }

    private void createFruits(ContextModel contextModel) {
        //FRUITS
        try {
            dataBase.insertWord(new WordModel(ContextCompat.getDrawable(this, R.drawable.apple), "MAÇÃ", contextModel.getId()));
            dataBase.insertWord(new WordModel(ContextCompat.getDrawable(this, R.drawable.grapes), "UVA", contextModel.getId()));
            dataBase.insertWord(new WordModel(ContextCompat.getDrawable(this, R.drawable.strawberry), "MORANGO", contextModel.getId()));
            dataBase.insertWord(new WordModel(ContextCompat.getDrawable(this, R.drawable.pineapple), "ABACAXI", contextModel.getId()));
            dataBase.insertWord(new WordModel(ContextCompat.getDrawable(this, R.drawable.orange), "LARANJA", contextModel.getId()));
            dataBase.insertWord(new WordModel(ContextCompat.getDrawable(this, R.drawable.bananas), "BANANA", contextModel.getId()));
            dataBase.insertWord(new WordModel(ContextCompat.getDrawable(this, R.drawable.coconut), "COCO", contextModel.getId()));
            dataBase.insertWord(new WordModel(ContextCompat.getDrawable(this, R.drawable.pear), "PERA", contextModel.getId()));
            dataBase.insertWord(new WordModel(ContextCompat.getDrawable(this, R.drawable.watermelon), "MELANCIA", contextModel.getId()));
            dataBase.insertWord(new WordModel(ContextCompat.getDrawable(this, R.drawable.avocado), "ABACATE", contextModel.getId()));

        } catch (DatabaseException e) {
            e.printStackTrace();
        }
    }

    public void createAnimals(ContextModel contextModel) {
        //ANIMALS
        try {
            dataBase.insertWord(new WordModel(ContextCompat.getDrawable(this, R.drawable.esquilo), "ESQUILO", contextModel.getId()));
            dataBase.insertWord(new WordModel(ContextCompat.getDrawable(this, R.drawable.cat), "GATO", contextModel.getId()));
            dataBase.insertWord(new WordModel(ContextCompat.getDrawable(this, R.drawable.dog), "CACHORRO", contextModel.getId()));
            dataBase.insertWord(new WordModel(ContextCompat.getDrawable(this, R.drawable.panda), "PANDA", contextModel.getId()));
            dataBase.insertWord(new WordModel(ContextCompat.getDrawable(this, R.drawable.leao), "LEÃO", contextModel.getId()));
            dataBase.insertWord(new WordModel(ContextCompat.getDrawable(this, R.drawable.bird), "PÁSSARO", contextModel.getId()));
            dataBase.insertWord(new WordModel(ContextCompat.getDrawable(this, R.drawable.duck), "PATO", contextModel.getId()));
            dataBase.insertWord(new WordModel(ContextCompat.getDrawable(this, R.drawable.owl), "CORUJA", contextModel.getId()));
            dataBase.insertWord(new WordModel(ContextCompat.getDrawable(this, R.drawable.monkey), "MACACO", contextModel.getId()));
            dataBase.insertWord(new WordModel(ContextCompat.getDrawable(this, R.drawable.elephant), "ELEFANTE", contextModel.getId()));
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
    }

    public void createCircus(ContextModel contextModel) {
        //CIRCUS
        try {
            dataBase.insertWord(new WordModel(ContextCompat.getDrawable(this, R.drawable.balloon), "BALÃO", contextModel.getId()));
            dataBase.insertWord(new WordModel(ContextCompat.getDrawable(this, R.drawable.clown), "PALHAÇO", contextModel.getId()));
            dataBase.insertWord(new WordModel(ContextCompat.getDrawable(this, R.drawable.magician), "MÁGICO", contextModel.getId()));
            dataBase.insertWord(new WordModel(ContextCompat.getDrawable(this, R.drawable.popcorn), "PIPOCA", contextModel.getId()));
            dataBase.insertWord(new WordModel(ContextCompat.getDrawable(this, R.drawable.icecream), "SORVETE", contextModel.getId()));
            dataBase.insertWord(new WordModel(ContextCompat.getDrawable(this, R.drawable.ballet), "BAILARINA", contextModel.getId()));
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
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
