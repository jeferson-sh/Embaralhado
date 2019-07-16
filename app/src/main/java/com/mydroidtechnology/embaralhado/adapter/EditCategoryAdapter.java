package com.mydroidtechnology.embaralhado.adapter;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.mydroidtechnology.embaralhado.R;
import com.mydroidtechnology.embaralhado.model.Context;
import com.mydroidtechnology.embaralhado.service.BackgroundMusicService;
import com.mydroidtechnology.embaralhado.view.EditContextActivity;

// This class have responsibility of change layout of ListView and allow the edition
// of Context objects in ListView of CategoriesActivity

public class EditCategoryAdapter extends CategoryAdapter {

    public EditCategoryAdapter(android.content.Context context) {
        super(context);
    }

    // Override method getView in GenericAdapter for show options of edition of the Context object.
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View layout = super.getView(i,view,viewGroup);
        ImageButton removeCategoryBtn = layout.findViewById(R.id.delete_data);
        removeCategoryBtn.setVisibility(View.VISIBLE);
        ImageButton editCategoryBtn = layout.findViewById(R.id.edite_data);
        editCategoryBtn.setVisibility(View.VISIBLE);
        final Context context = (Context) getGenericModels().get(i);
        removeCategoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeCategory(context); // Remove Context object.
            }

        });

        editCategoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editCategory(context); // Edit Context object.

            }
        });
        return layout;
    }

    // Starts Activity EditContextActivity informing the id of the object in Database.
    private void editCategory(Context context) {
        Bundle bundle = new Bundle();
        // Puts id of the Context Object for searching in Database.
        bundle.putInt("categoryName", context.getId());
        Intent intent = new Intent(getContext(), EditContextActivity.class);
        intent.putExtras(bundle);
        BackgroundMusicService.setStopBackgroundMusicEnable(false);
        getContext().startActivity(intent);
        ((Activity) getContext()).finish();
    }

    private void removeCategory(final Context context){
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(true);
        builder.setTitle("Deseja apagar o contexto "+ context.getName()+"?");
        builder.setMessage("Todas as palavras e pontuações serão apagadas!");
        builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getGenericModels().remove(context);  // Remove Context in ListView
                getDataBase().deleteContext(context); // Remove Context in Database
                Toast.makeText(getContext(), "Contexto " + context.getName() + " apagado!", Toast.LENGTH_LONG).show();
                EditCategoryAdapter.super.notifyDataSetChanged(); //Notify ListView that was occurred changes.
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
}
