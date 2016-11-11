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
        palavras = new ArrayList<Palavra>();
        criarPalavras();
    }

    private void criarPalavras() {
        palavras.add(new Palavra("ESQUILO",R.drawable.esquilo));
        palavras.add(new Palavra("CACHORRO",R.drawable.bulldog));
        palavras.add(new Palavra("GATO",R.drawable.cat));
        palavras.add(new Palavra("HIPOPOTAMO",R.drawable.hipopotamo));
        palavras.add(new Palavra("BOI",R.drawable.boi));
        palavras.add(new Palavra("ZEBRA",R.drawable.zebra));
        palavras.add(new Palavra("COALA",R.drawable.coala));
        palavras.add(new Palavra("PEIXE",R.drawable.peixe));
        palavras.add(new Palavra("PANDA",R.drawable.panda));
        palavras.add(new Palavra("LEAO",R.drawable.leao));
        palavras.add(new Palavra("CARRO",R.drawable.car));
        palavras.add(new Palavra("BORBOLETA",R.drawable.borboleta));
        palavras.add(new Palavra("MESA",R.drawable.mesa));
        palavras.add(new Palavra("CADEIRA",R.drawable.cadeira));
        palavras.add(new Palavra("SOFA",R.drawable.sofa));
        palavras.add(new Palavra("RELOGIO",R.drawable.clock));



    }

    public List<Palavra> getPalavras() {
        return palavras;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }


}
