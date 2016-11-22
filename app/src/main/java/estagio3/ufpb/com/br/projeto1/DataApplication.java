package estagio3.ufpb.com.br.projeto1;

import android.app.Activity;
import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jeferson on 10/11/2016.
 */

public class DataApplication extends Application {

    private List<Palavra> palavras;
    private List<Pontuação> pontuações;
    private BD bd;

    @Override
    public void onCreate() {
        super.onCreate();
        bd = new BD(this);
        palavras = bd.buscarPalavras();
        pontuações = bd.buscarPontos();
        if (palavras.isEmpty()) {
            criarPalavras();
            onCreate();
        }


    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    public List<Pontuação> getPontuações() {
        return pontuações;
    }

    public List<Palavra> getPalavras() {
        return palavras;
    }


    public void criarPalavras() {
        bd.inserirPalavra(new Palavra(ContextCompat.getDrawable(this,R.drawable.esquilo),"ESQUILO"));
        bd.inserirPalavra(new Palavra(ContextCompat.getDrawable(this,R.drawable.bulldog), "CACHORRO"));//4
        bd.inserirPalavra(new Palavra(ContextCompat.getDrawable(this,R.drawable.cat),"GATO"));//2
        bd.inserirPalavra(new Palavra(ContextCompat.getDrawable(this,R.drawable.hipopotamo),"HIPOPOTAMO"));//3
        bd.inserirPalavra(new Palavra(ContextCompat.getDrawable(this,R.drawable.boi),"BOI"));//9
        bd.inserirPalavra(new Palavra(ContextCompat.getDrawable(this,R.drawable.zebra),"ZEBRA"));//5
        bd.inserirPalavra(new Palavra(ContextCompat.getDrawable(this,R.drawable.coala),"COALA"));
        bd.inserirPalavra(new Palavra(ContextCompat.getDrawable(this,R.drawable.peixe),"PEIXE"));//7
        bd.inserirPalavra(new Palavra(ContextCompat.getDrawable(this,R.drawable.panda),"PANDA"));//6
        bd.inserirPalavra(new Palavra(ContextCompat.getDrawable(this,R.drawable.leao),"LEÃO"));
        bd.inserirPalavra(new Palavra(ContextCompat.getDrawable(this,R.drawable.car),"CARRO"));//10
        bd.inserirPalavra(new Palavra(ContextCompat.getDrawable(this,R.drawable.borboleta),"BORBOLETA"));
        bd.inserirPalavra(new Palavra(ContextCompat.getDrawable(this,R.drawable.mesa),"MESA"));//8
        bd.inserirPalavra(new Palavra(ContextCompat.getDrawable(this,R.drawable.cadeira),"CADEIRA"));
        bd.inserirPalavra(new Palavra(ContextCompat.getDrawable(this,R.drawable.sofa),"SOFÁ"));//11
        bd.inserirPalavra(new Palavra(ContextCompat.getDrawable(this,R.drawable.clock),"RELÓGIO"));//1

    }




}
