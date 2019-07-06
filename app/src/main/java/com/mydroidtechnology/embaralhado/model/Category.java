package com.mydroidtechnology.embaralhado.model;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

public class Category extends GenericModel {

    private String haveElements; //Tag the category when has words.
    private String scores; ///Tag the category when has scores.

    public Category(Integer id, byte[] imageBytes, String name, String haveElements, String scores) { //This constructor is called in the Database.
        super(id,imageBytes,name);
        this.haveElements = haveElements;
        this.scores = scores;
    }

    public Category(Bitmap image, String name) { //This constructor is called when inserting new categories.
        super(image,name);
        this.haveElements = "false";
        this.scores = "false";
    }

    public Category(Drawable drawable, String name) { //This constructor is called when the app runs the first time.
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
