package estagio3.ufpb.com.br.embaralhando;

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

/**
 * Created by Jeferson on 25/07/2017.
 */

public class EditWordAdapter extends WordsAdapter {
    public EditWordAdapter(Context context, String nameContext) {
        super(context, nameContext);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {// Inflar o layout da lista

        View layout;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(android.content.Context.LAYOUT_INFLATER_SERVICE);
            layout = inflater.inflate(R.layout.word_item_list, null);
        } else {
            layout = view;
        }

        ImageView imageView = (ImageView) layout.findViewById(R.id.image_item);
        TextView textView = (TextView) layout.findViewById(R.id.text_item);
        ImageButton removeWord = (ImageButton) layout.findViewById(R.id.delete_word);
        removeWord.setVisibility(View.VISIBLE);

        final Word word = getWords().get(i);
        textView.setText(word.getName());

        imageView.setImageBitmap(getWords().get(i).getImage());

        removeWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setCancelable(true);
                builder.setTitle("Apagar Palavra");
                builder.setMessage("Tem certeza que deseja apagar a palavra " + word.getName() + "?");
                builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getWords().remove(word);
                        getDataBase().deleteWord(word);
                        Toast.makeText(getContext(), "Palavra " +word.getName() + " apagada!", Toast.LENGTH_LONG).show();
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

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

}
