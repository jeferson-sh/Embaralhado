package estagio3.ufpb.com.br.projeto1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity implements Runnable {

    private static final long delay = 4000;
    private BD bd;
    private SharedPreferences sPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        bd = new BD(this);
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

    public void criarPalavras() {
        bd.inserirPalavra(new Palavra(ContextCompat.getDrawable(this,R.drawable.esquilo),"ESQUILO"));
        bd.inserirPalavra(new Palavra(ContextCompat.getDrawable(this,R.drawable.cat),"GATO"));//2
        bd.inserirPalavra(new Palavra(ContextCompat.getDrawable(this,R.drawable.hipopotamo),"HIPOPOTAMO"));//3
        bd.inserirPalavra(new Palavra(ContextCompat.getDrawable(this,R.drawable.coala),"COALA"));
        bd.inserirPalavra(new Palavra(ContextCompat.getDrawable(this,R.drawable.panda),"PANDA"));//6
        bd.inserirPalavra(new Palavra(ContextCompat.getDrawable(this,R.drawable.leao),"LEÃO"));
        bd.inserirPalavra(new Palavra(ContextCompat.getDrawable(this,R.drawable.car),"CARRO"));//10
        bd.inserirPalavra(new Palavra(ContextCompat.getDrawable(this,R.drawable.borboleta),"BORBOLETA"));
        bd.inserirPalavra(new Palavra(ContextCompat.getDrawable(this,R.drawable.sofa),"SOFÁ"));//11
        bd.inserirPalavra(new Palavra(ContextCompat.getDrawable(this,R.drawable.clock),"RELÓGIO"));//1
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (sPreferences.getBoolean("firstRun", true)) {
            sPreferences.edit().putBoolean("firstRun", false).apply();
            criarPalavras();
        }
    }
}
