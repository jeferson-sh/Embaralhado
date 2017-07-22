package estagio3.ufpb.com.br.embaralhando;

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

import java.util.ArrayList;
import java.util.List;

public class CategorieAdapter extends BaseAdapter{

    private List<Categorie> categories;
    private Context context;
    private DataBase dataBase;

    public CategorieAdapter(Context context) {
        this.context = context;
        this.dataBase = new DataBase(context);
        try {
            this.categories = dataBase.searchMyContextsDatabase();
        }catch (Exception e){
            this.categories = new ArrayList<Categorie>();
        }
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return this.categories.size();
    }

    @Override
    public Object getItem(int i) {
        return this.categories.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {// Inflar o layout da lista

        View layout;
        if(view == null){
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(android.content.Context.LAYOUT_INFLATER_SERVICE);
            layout = inflater.inflate(R.layout.context_item_list, null);
        }
        else{
            layout = view;
        }

        ImageView imageView = (ImageView) layout.findViewById(R.id.image_item);
        TextView textView = (TextView) layout.findViewById(R.id.text_item);

        Categorie categorie = this.categories.get(i);
        textView.setText(categorie.getName());

        imageView.setImageBitmap(categories.get(i).getImage());
        return layout;
    }
}
