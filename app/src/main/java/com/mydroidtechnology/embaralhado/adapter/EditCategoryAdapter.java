package com.mydroidtechnology.embaralhado.adapter;


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
import com.mydroidtechnology.embaralhado.view.EditCategoryActivity;

// This class have responsibility of change layout of ListView and allow the edition
// of Category objects in ListView of CategoriesActivity

public class EditCategoryAdapter extends CategoryAdapter {

    public EditCategoryAdapter(Context context) {
        super(context);
    }

    // Override method getView in GenericAdapter for show options of edition of the Category object.
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View layout = super.getView(i,view,viewGroup);
        ImageButton removeCategoryBtn = layout.findViewById(R.id.delete_data);
        removeCategoryBtn.setVisibility(View.VISIBLE);
        ImageButton editCategoryBtn = layout.findViewById(R.id.edite_data);
        editCategoryBtn.setVisibility(View.VISIBLE);
        final Category category = (Category) getGenericModels().get(i);
        removeCategoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeCategory(category); // Remove Category object.
            }

        });

        editCategoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editCategory(category); // Edit Category object.

            }
        });
        return layout;
    }

    // Starts Activity EditCategoryActivity informing the id of the object in Database.
    private void editCategory(Category category) {
        Bundle bundle = new Bundle();
        // Puts id of the Category Object for searching in Database.
        bundle.putInt("categoryName", category.getId());
        Intent intent = new Intent(getContext(), EditCategoryActivity.class);
        intent.putExtras(bundle);
        BackgroundMusicService.setStopBackgroundMusicEnable(false);
        getContext().startActivity(intent);
        ((Activity) getContext()).finish();
    }

    private void removeCategory(final Category category){
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(true);
        builder.setTitle("Deseja apagar o contexto "+ category.getName()+"?");
        builder.setMessage("Todas as palavras e pontuações serão apagadas!");
        builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Remove Category in ListView
                getGenericModels().remove(category);
                // Remove Category in Database
                getDataBase().deleteCategory(category);
                Toast.makeText(getContext(), "Contexto " + category.getName() + " apagado!", Toast.LENGTH_LONG).show();
                //Notify ListView that was occurred changes.
                EditCategoryAdapter.super.notifyDataSetChanged();
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
