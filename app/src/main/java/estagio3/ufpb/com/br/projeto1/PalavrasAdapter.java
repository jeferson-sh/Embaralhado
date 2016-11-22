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

import java.util.List;

public class PalavrasAdapter extends BaseAdapter{

    private List<Palavra> palavras;
    private Context context;
    private BD bd;

    public PalavrasAdapter(Context context) {
        this.context = context;
        this.bd = new BD(context);
        this.palavras = bd.buscarPalavras();
        this.notifyDataSetChanged();
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

        View layout;
        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layout = inflater.inflate(R.layout.palavra_item_list, null);
        }
        else{
            layout = view;
        }

        ImageView imageView = (ImageView) layout.findViewById(R.id.ic_item);
        TextView textView = (TextView) layout.findViewById(R.id.text_item);

        Palavra palavra = this.palavras.get(i);
        textView.setText(palavra.getPalavra());

        imageView.setImageBitmap(palavras.get(i).getImage());
        return layout;
    }
}
