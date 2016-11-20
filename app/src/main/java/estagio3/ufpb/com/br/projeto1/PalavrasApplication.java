package estagio3.ufpb.com.br.projeto1;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jeferson on 10/11/2016.
 */

public class PalavrasApplication extends Application {

    private List<Palavra> palavras;
    private List<Pontuação> pontuações;

    @Override
    public void onCreate() {
        super.onCreate();
        palavras = new ArrayList<>();
        pontuações = new ArrayList<>();
        criarPalavras();
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

    public void addPalavras(byte[] byteImage, String text){
        palavras.add(new Palavra(byteImage,text));
    }

    public void addPontuação(byte[] byteImage, int pontuação){
        pontuações.add(new Pontuação(byteImage,pontuação));
    }

    private void criarPalavras() {
        palavras.add(new Palavra(getResources().getDrawable(R.drawable.esquilo),"ESQUILO"));
        palavras.add(new Palavra(getResources().getDrawable(R.drawable.bulldog), "CACHORRO"));//4
        palavras.add(new Palavra(getResources().getDrawable(R.drawable.cat),"GATO"));//2
        palavras.add(new Palavra(getResources().getDrawable(R.drawable.hipopotamo),"HIPOPOTAMO"));//3
        palavras.add(new Palavra(getResources().getDrawable(R.drawable.boi),"BOI"));//9
        palavras.add(new Palavra(getResources().getDrawable(R.drawable.zebra),"ZEBRA"));//5
        palavras.add(new Palavra(getResources().getDrawable(R.drawable.coala),"COALA"));
        palavras.add(new Palavra(getResources().getDrawable(R.drawable.peixe),"PEIXE"));//7
        palavras.add(new Palavra(getResources().getDrawable(R.drawable.panda),"PANDA"));//6
        palavras.add(new Palavra(getResources().getDrawable(R.drawable.leao),"LEAO"));
        palavras.add(new Palavra(getResources().getDrawable(R.drawable.car),"CARRO"));//10
        palavras.add(new Palavra(getResources().getDrawable(R.drawable.borboleta),"BORBOLETA"));
        palavras.add(new Palavra(getResources().getDrawable( R.drawable.mesa),"MESA"));//8
        palavras.add(new Palavra(getResources().getDrawable(R.drawable.cadeira),"CADEIRA"));
        palavras.add(new Palavra(getResources().getDrawable(R.drawable.sofa),"SOFA"));//11
        palavras.add(new Palavra(getResources().getDrawable(R.drawable.clock),"RELOGIO"));//1
    }
}
