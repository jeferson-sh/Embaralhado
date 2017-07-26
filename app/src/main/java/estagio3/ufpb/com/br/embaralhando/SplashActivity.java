package estagio3.ufpb.com.br.embaralhando;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

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
        handler.postDelayed(this,delay);
    }

    @Override
    public void run() {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void createContext() {
        dataBase.insertContext(new Categorie(ContextCompat.getDrawable(this,R.drawable.animals),"ANIMAIS"));
        dataBase.insertContext(new Categorie(ContextCompat.getDrawable(this,R.drawable.fruits),"FRUTAS"));
        dataBase.insertContext(new Categorie(ContextCompat.getDrawable(this,R.drawable.circus),"CIRCO"));
    }

    public void createWords() {
        //ANIMALS
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this,R.drawable.esquilo),"ESQUILO","ANIMAIS"));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this,R.drawable.cat),"GATO","ANIMAIS"));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this,R.drawable.coala),"COALA","ANIMAIS"));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this,R.drawable.panda),"PANDA","ANIMAIS"));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this,R.drawable.leao),"LEÃO","ANIMAIS"));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this,R.drawable.bird),"PÁSSARO","ANIMAIS"));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this,R.drawable.duck),"PATO","ANIMAIS"));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this,R.drawable.owl),"CORUJA","ANIMAIS"));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this,R.drawable.monkey),"MACACO","ANIMAIS"));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this,R.drawable.elephant),"ELEFANTE","ANIMAIS"));

        //FRUITS
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this,R.drawable.apple),"MAÇÃ","FRUTAS"));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this,R.drawable.grapes),"UVA","FRUTAS"));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this,R.drawable.strawberry),"MORANGO","FRUTAS"));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this,R.drawable.pineapple),"ABACAXI","FRUTAS"));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this,R.drawable.orange),"LARANJA","FRUTAS"));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this,R.drawable.bananas),"BANANA","FRUTAS"));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this,R.drawable.coconut),"COCO","FRUTAS"));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this,R.drawable.pear),"PÊRA","FRUTAS"));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this,R.drawable.watermelon),"MELANCIA","FRUTAS"));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this,R.drawable.avocado),"ABACATE","FRUTAS"));

        //CIRCUS
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this,R.drawable.balloon),"BALÃO","CIRCO"));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this,R.drawable.elephant),"ELEFANTE","CIRCO"));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this,R.drawable.leao),"LEÃO","CIRCO"));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this,R.drawable.clown),"PALHAÇO","CIRCO"));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this,R.drawable.carousel),"CARROCEL","CIRCO"));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this,R.drawable.monkey),"MACACO","CIRCO"));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this,R.drawable.magician),"MÁGICO","CIRCO"));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this,R.drawable.seal),"FOCA","CIRCO"));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this,R.drawable.bear),"URSO","CIRCO"));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this,R.drawable.popcorn),"PIPOCA","CIRCO"));
        dataBase.insertWord(new Word(ContextCompat.getDrawable(this,R.drawable.icecream),"SORVETE","CIRCO"));

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (sPreferences.getBoolean("firstRun", true)) {
            sPreferences.edit().putBoolean("firstRun", false).apply();
            createWords();
            createContext();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
