package com.mydroidtechnology.embaralhado.adapters;


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
import com.mydroidtechnology.embaralhado.exceptions.DatabaseException;
import com.mydroidtechnology.embaralhado.models.ContextModel;
import com.mydroidtechnology.embaralhado.services.BackgroundMusicService;
import com.mydroidtechnology.embaralhado.views.EditContextModelActivity;

import java.util.List;

// This class have responsibility of change layout of ListView and allow the edition
// of ContextModel objects in ListView of CategoriesActivity

public class EditContextModelAdapter extends ContextModelAdapter {

    public EditContextModelAdapter(Context context) {
        super(context);
    }

    // Override method getView in GenericModelAdapter for show options of edition of the ContextModel object.
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View layout = super.getView(i,view,viewGroup);
        ImageButton removeCategoryBtn = layout.findViewById(R.id.delete_data);
        removeCategoryBtn.setVisibility(View.VISIBLE);
        ImageButton editCategoryBtn = layout.findViewById(R.id.edite_data);
        editCategoryBtn.setVisibility(View.VISIBLE);
        final ContextModel contextModel = (ContextModel) getGenericModels().get(i);
        removeCategoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeCategory(contextModel); // Remove ContextModel object.
            }

        });

        editCategoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editCategory(contextModel); // Edit ContextModel object.

            }
        });
        return layout;
    }

    // Starts Activity EditContextModelActivity informing the id of the object in Database.
    private void editCategory(ContextModel contextModel) {
        Bundle bundle = new Bundle();
        // Puts id of the ContextModel Object for searching in Database.
        bundle.putInt("contextModelNameId", contextModel.getId());
        Intent intent = new Intent(getContext(), EditContextModelActivity.class);
        intent.putExtras(bundle);
        BackgroundMusicService.setStopBackgroundMusicEnable(false);
        getContext().startActivity(intent);
        ((Activity) getContext()).finish();
    }

    private void removeCategory(final ContextModel contextModel){
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(true);
        builder.setTitle("Deseja apagar o contexto "+ contextModel.getName()+"?");
        builder.setMessage("Todas as palavras e pontuações serão apagadas!");
        builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getGenericModels().remove(contextModel);  // Remove ContextModel in ListView
                try {
                    getDataBase().deleteContext(contextModel); // Remove ContextModel in Database
                } catch (DatabaseException e) {
                    e.printStackTrace();
                }
                Toast.makeText(getContext(), "Contexto " + contextModel.getName() + " apagado!", Toast.LENGTH_LONG).show();
                EditContextModelAdapter.super.notifyDataSetChanged(); //Notify ListView that was occurred changes.
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
