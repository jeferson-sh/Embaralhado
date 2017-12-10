package com.mydroidtechnology.embaralhado.adapter;

/*
 * Created by Jeferson on 17/11/2016.
 */

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mydroidtechnology.embaralhado.model.Categorie;
import com.mydroidtechnology.embaralhado.service.BackgroundMusicService;
import com.mydroidtechnology.embaralhado.view.EditCategorieActivity;
import com.mydroidtechnology.embaralhado.R;

public class EditCategorieAdapter extends CategorieAdapter {

    public EditCategorieAdapter(Context context) {
        super(context);
    }
    @SuppressLint("InflateParams")
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
        ImageButton editeCategorie = (ImageButton) layout.findViewById(R.id.edite_categorie);
        editeCategorie.setVisibility(View.VISIBLE);

        final Categorie categorie = (Categorie) getGenericModels().get(i);
        textView.setText(categorie.getName());

        imageView.setImageBitmap(categorie.getImage());

        removeCategorie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setCancelable(true);
                builder.setTitle("Deseja apagar o contexto "+categorie.getName()+"?");
                builder.setMessage("Todas as palavras e pontuações serão apagadas!");
                builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getGenericModels().remove(categorie);
                        getDataBase().deleteCategorie(categorie);
                        Toast.makeText(getContext(), "Contexto " + categorie.getName() + " apagado!", Toast.LENGTH_LONG).show();
                        notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        editeCategorie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("categorieName", categorie.getId());
                Intent intent = new Intent(getContext(), EditCategorieActivity.class);
                intent.putExtras(bundle);
                BackgroundMusicService.setStopBackgroundMusicEnable(false);
                getContext().startActivity(intent);
                ((Activity) getContext()).finish();

            }
        });
        return layout;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
}
