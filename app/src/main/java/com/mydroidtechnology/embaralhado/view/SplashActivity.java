package com.mydroidtechnology.embaralhado.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mydroidtechnology.embaralhado.R;
import com.mydroidtechnology.embaralhado.model.Categorie;
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

    public void createContext() {
        Categorie animals = new Categorie(ContextCompat.getDrawable(this, R.drawable.animals), "ANIMAIS");
        dataBase.insertCategorie(animals);
        createAnimals(dataBase.searchCategorieDatabase(animals.getName()));

        Categorie fruits = new Categorie(ContextCompat.getDrawable(this, R.drawable.fruits), "FRUTAS");
        dataBase.insertCategorie(fruits);
        createFruits(dataBase.searchCategorieDatabase(fruits.getName()));

        Categorie circus = new Categorie(ContextCompat.getDrawable(this, R.drawable.circus), "CIRCO");
        dataBase.insertCategorie(circus);
        createCircus(dataBase.searchCategorieDatabase(circus.getName()));
    }

    private void createFruits(Categorie categorie) {
        //FRUITS
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.apple), "MAÇÃ", categorie.getId()));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.grapes), "UVA", categorie.getId()));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.strawberry), "MORANGO", categorie.getId()));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.pineapple), "ABACAXI", categorie.getId()));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.orange), "LARANJA", categorie.getId()));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.bananas), "BANANA", categorie.getId()));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.coconut), "COCO", categorie.getId()));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.pear), "PERA", categorie.getId()));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.watermelon), "MELANCIA", categorie.getId()));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.avocado), "ABACATE", categorie.getId()));

    }

    public void createAnimals(Categorie categorie) {
        //ANIMALS
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.esquilo), "ESQUILO", categorie.getId()));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.cat), "GATO", categorie.getId()));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.dog), "CACHORRO", categorie.getId()));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.panda), "PANDA", categorie.getId()));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.leao), "LEÃO", categorie.getId()));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.bird), "PÁSSARO", categorie.getId()));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.duck), "PATO", categorie.getId()));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.owl), "CORUJA", categorie.getId()));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.monkey), "MACACO", categorie.getId()));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.elephant), "ELEFANTE", categorie.getId()));

    }

    public void createCircus(Categorie categorie) {
        //CIRCUS
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.balloon), "BALÃO", categorie.getId()));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.clown), "PALHAÇO", categorie.getId()));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.magician), "MÁGICO", categorie.getId()));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.popcorn), "PIPOCA", categorie.getId()));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.icecream), "SORVETE", categorie.getId()));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this, R.drawable.ballet), "BAILARINA", categorie.getId()));


    }

    @Override
    protected void onResume() {
        super.onResume();
        if (sPreferences.getBoolean("firstRun", true)) {
            sPreferences.edit().putBoolean("firstRun", false).apply();
            createContext();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
