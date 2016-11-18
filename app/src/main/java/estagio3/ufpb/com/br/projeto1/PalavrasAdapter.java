package estagio3.ufpb.com.br.projeto1;

/**
 * Created by Jeferson on 17/11/2016.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class PalavrasAdapter extends BaseAdapter{

    private List<Palavra> palavras;
    private Context context;
    private PalavrasApplication palavrasApplication;

    public PalavrasAdapter(Context context) {
        this.context = context;
        this.palavrasApplication = (PalavrasApplication) context.getApplicationContext();
        this.palavras = palavrasApplication.getPalavras();
    }

    @Override
    public int getCount() {
        return this.palavras.size();
    }

    @Override
    public Object getItem(int i) {
        return this.palavras.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {// Inflar o layout da lista
        View v = LayoutInflater.from(context).inflate(R.layout.palavra_item_list, viewGroup, false);

        ImageView imageView = (ImageView) v.findViewById(R.id.ic_item);
        TextView textView = (TextView) v.findViewById(R.id.text_item);

        Palavra palavra = this.palavras.get(i);
        textView.setText(palavra.getPalavra());

        Picasso.with(this.context)// carrega o contexto onde sera exibida a imagem
                .load(palavra.getIdImage())// carrega a imagem
                .into(imageView); //aplica a transformação a imagem da view da imagem do xml

        return v;
    }
}
