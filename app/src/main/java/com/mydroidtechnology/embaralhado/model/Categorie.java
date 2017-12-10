package com.mydroidtechnology.embaralhado.model;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

/*
 * Created by Jeferson on 10/11/2016.
 */
public class Categorie extends GenericModel {

    private String haveElements;
    private String scores;

    public Categorie(Integer id, byte[] imageBytes, String name, String haveElements, String scores) {
        super(id,imageBytes,name);
        this.haveElements = haveElements;
        this.scores = scores;
    }

    public Categorie(Bitmap image, String name) {
        super(image,name);
        this.haveElements = "false";
        this.scores = "false";
    }

    public Categorie(Drawable drawable, String name) {
        super(drawable,name);
        this.haveElements = "true";
        this.scores = "false";
    }

    public String getHaveElements() {
        return haveElements;
    }

    public void setHaveElements(String elements) {
        this.haveElements = elements;
    }

    public String getScores() {
        return scores;
    }

    public void setScores(String scores) {
        this.scores = scores;
    }
}
