package com.mydroidtechnology.embaralhado.adapter;

/*
 * Created by Jeferson on 17/11/2016.
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.mydroidtechnology.embaralhado.R;
import com.mydroidtechnology.embaralhado.model.Category;
import com.mydroidtechnology.embaralhado.service.BackgroundMusicService;
import com.mydroidtechnology.embaralhado.view.EditCategorieActivity;

public class EditCategorieAdapter extends CategorieAdapter {

    public EditCategorieAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View layout = super.getView(i,view,viewGroup);
        ImageButton removeCategorie = layout.findViewById(R.id.delete_data);
        removeCategorie.setVisibility(View.VISIBLE);
        ImageButton editeCategorie = layout.findViewById(R.id.edite_data);
        editeCategorie.setVisibility(View.VISIBLE);
        final Category category = (Category) getGenericModels().get(i);
        removeCategorie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setCancelable(true);
                builder.setTitle("Deseja apagar o contexto "+ category.getName()+"?");
                builder.setMessage("Todas as palavras e pontuações serão apagadas!");
                builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getGenericModels().remove(category);
                        getDataBase().deleteCategory(category);
                        Toast.makeText(getContext(), "Contexto " + category.getName() + " apagado!", Toast.LENGTH_LONG).show();
                        EditCategorieAdapter.super.notifyDataSetChanged();
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
                bundle.putInt("categorieName", category.getId());
                Intent intent = new Intent(getContext(), EditCategorieActivity.class);
                intent.putExtras(bundle);
                BackgroundMusicService.setStopBackgroundMusicEnable(false);
                getContext().startActivity(intent);
                ((Activity) getContext()).finish();

            }
        });
        return layout;
    }
}
