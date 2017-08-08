package estagio3.ufpb.com.br.embaralhando.adapter;

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

import estagio3.ufpb.com.br.embaralhando.R;
import estagio3.ufpb.com.br.embaralhando.model.Word;
import estagio3.ufpb.com.br.embaralhando.persistence.DataBase;

public class WordsAdapter extends BaseAdapter{

    private List<Word> words;
    private Context context;
    private DataBase dataBase;

    public WordsAdapter(Context context,String nameContext) {
        this.context = context;
        this.dataBase = new DataBase(context);
        this.words = dataBase.searchWordsDatabase(nameContext);
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return this.words.size();
    }

    @Override
    public Object getItem(int i) {
        return this.words.get(i);
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
            layout = inflater.inflate(R.layout.word_item_list, null);
        }
        else{
            layout = view;
        }

        ImageView imageView = (ImageView) layout.findViewById(R.id.image_item);
        TextView textView = (TextView) layout.findViewById(R.id.text_item);

        Word word = this.words.get(i);
        textView.setText(word.getName());

        imageView.setImageBitmap(words.get(i).getImage());
        return layout;
    }

    public List<Word> getWords() {
        return words;
    }

    public void setWords(List<Word> words) {
        this.words = words;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public DataBase getDataBase() {
        return dataBase;
    }

    public void setDataBase(DataBase dataBase) {
        this.dataBase = dataBase;
    }
}
