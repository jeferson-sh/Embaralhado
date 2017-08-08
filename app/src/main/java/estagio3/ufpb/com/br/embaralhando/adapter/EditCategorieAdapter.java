package estagio3.ufpb.com.br.embaralhando.adapter;

/**
 * Created by Jeferson on 17/11/2016.
 */

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import estagio3.ufpb.com.br.embaralhando.R;
import estagio3.ufpb.com.br.embaralhando.model.Categorie;

public class EditCategorieAdapter extends CategorieAdapter {

    public EditCategorieAdapter(Context context) {
        super(context);
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {// Inflar o layout da lista

        View layout;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(android.content.Context.LAYOUT_INFLATER_SERVICE);
            layout = inflater.inflate(R.layout.categorie_item_list, null);
        } else {
            layout = view;
        }

        ImageView imageView = (ImageView) layout.findViewById(R.id.image_item);
        TextView textView = (TextView) layout.findViewById(R.id.text_item);
        ImageButton removeCategorie = (ImageButton) layout.findViewById(R.id.delete_categorie);
        removeCategorie.setVisibility(View.VISIBLE);

        final Categorie categorie = getCategories().get(i);
        textView.setText(categorie.getName());

        imageView.setImageBitmap(getCategories().get(i).getImage());

        removeCategorie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setCancelable(true);
                builder.setTitle("Deseja apagar o contexto "+categorie.getName()+"?");
                builder.setMessage("Todas as palavras serão apagadas!");
                builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getCategories().remove(categorie);
                        getDataBase().deleteContext(categorie);
                        Toast.makeText(getContext(), "Contexto " + categorie.getName() + " apagado!", Toast.LENGTH_LONG).show();
                        notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        return layout;
    }
}
