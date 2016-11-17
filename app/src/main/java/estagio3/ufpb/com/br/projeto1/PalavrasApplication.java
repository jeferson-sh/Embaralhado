package estagio3.ufpb.com.br.projeto1;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jeferson on 10/11/2016.
 */

public class PalavrasApplication extends Application {

    private List<Palavra> palavras;

    @Override
    public void onCreate() {
        super.onCreate();
        palavras = new ArrayList<>();
        criarPalavras();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    public List<Palavra> getPalavras() {
        return palavras;
    }

    private void criarPalavras() {
        palavras.add(new Palavra(R.drawable.esquilo,"ESQUILO"));
        palavras.add(new Palavra(R.drawable.bulldog, "CACHORRO"));
        palavras.add(new Palavra(R.drawable.cat,"GATO"));
        palavras.add(new Palavra(R.drawable.hipopotamo,"HIPOPOTAMO"));
        palavras.add(new Palavra(R.drawable.boi,"BOI"));
        palavras.add(new Palavra(R.drawable.zebra,"ZEBRA"));
        palavras.add(new Palavra(R.drawable.coala,"COALA"));
        palavras.add(new Palavra(R.drawable.peixe,"PEIXE"));
        palavras.add(new Palavra(R.drawable.panda,"PANDA"));
        palavras.add(new Palavra(R.drawable.leao,"LEAO"));
        palavras.add(new Palavra(R.drawable.car,"CARRO"));
        palavras.add(new Palavra(R.drawable.borboleta,"BORBOLETA"));
        palavras.add(new Palavra(R.drawable.mesa,"MESA"));
        palavras.add(new Palavra(R.drawable.cadeira,"CADEIRA"));
        palavras.add(new Palavra(R.drawable.sofa,"SOFA"));
        palavras.add(new Palavra(R.drawable.clock,"RELOGIO"));
    }
}
