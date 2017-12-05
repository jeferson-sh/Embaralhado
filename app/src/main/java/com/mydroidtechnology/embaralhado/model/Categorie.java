package com.mydroidtechnology.embaralhado.model;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.mydroidtechnology.embaralhado.util.ConvertImageUtil;

/*
 * Created by Jeferson on 10/11/2016.
 */
public class Categorie extends GenericModel {

    private String elements;
    private String scores;

    public Categorie(Integer id, byte[] imageBytes, String name, String elements, String scores) {
        super(id,imageBytes,name);
        this.elements = elements;
        this.scores = scores;
    }

    public Categorie(Bitmap image, String name) {
        super(image,name);
        this.elements = "false";
        this.scores = "false";
    }

    public Categorie(Drawable drawable, String name) {
        super(drawable,name);
        this.elements = "true";
        this.scores = "false";
    }

    public String getElements() {
        return elements;
    }

    public void setElements(String elements) {
        this.elements = elements;
    }

    public String getScores() {
        return scores;
    }

    public void setScores(String scores) {
        this.scores = scores;
    }
}
