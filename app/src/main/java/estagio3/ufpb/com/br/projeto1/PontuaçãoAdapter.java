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

public class PontuaçãoAdapter extends BaseAdapter{

    private List<Pontuação> pontos;
    private Context context;
    private PalavrasApplication palavrasApplication;

    public PontuaçãoAdapter(Context context) {
        this.context = context;
        this.palavrasApplication = (PalavrasApplication) context.getApplicationContext();
        this.pontos = palavrasApplication.getPontuações();
    }

    @Override
    public int getCount() {
        return this.pontos.size();
    }

    @Override
    public Object getItem(int i) {
        return this.pontos.get(i);
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
            layout = inflater.inflate(R.layout.pontos_item_list, null);
        }
        else{
            layout = view;
        }

        ImageView imageView = (ImageView) layout.findViewById(R.id.ic_item);
        TextView textView = (TextView) layout.findViewById(R.id.text_item);

        Pontuação pontos = this.pontos.get(i);
        String p = pontos.getPontos()+" Estrelas.";
        textView.setText(p);

        imageView.setImageBitmap(this.pontos.get(i).getImage());

        return layout;
    }
}
