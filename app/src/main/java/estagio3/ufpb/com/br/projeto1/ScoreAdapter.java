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

public class ScoreAdapter extends BaseAdapter{

    private List<Score> scores;
    private Context context;
    private DataBase dataBase;

    public ScoreAdapter(Context context) {
        this.context = context;
        this.dataBase = new DataBase(context);
        this.scores = dataBase.searchScoresDatabase();
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return this.scores.size();
    }

    @Override
    public Object getItem(int i) {
        return this.scores.get(i);
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
            layout = inflater.inflate(R.layout.score_item_list, null);
        }
        else{
            layout = view;
        }

        ImageView imageView = (ImageView) layout.findViewById(R.id.image_item);
        TextView textView = (TextView) layout.findViewById(R.id.text_item);

        Score pontos = this.scores.get(i);
        String p = "";
        if(pontos.getScore()==1)
            p = pontos.getScore()+" Ponto.";
        else p = pontos.getScore()+ " Pontos.";
        textView.setText(p);

        imageView.setImageBitmap(this.scores.get(i).getImage());

        return layout;
    }
}
