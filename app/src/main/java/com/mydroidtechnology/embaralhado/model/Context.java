package com.mydroidtechnology.embaralhado.model;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

public class Context extends GenericModel {

    private String haveElements; //Tag the Context when has words.
    private String scores; ///Tag the Context when has scores.

    //This constructor is called in the Database.
    public Context(Integer id, byte[] imageBytes, String name, String haveElements, String scores) {
        super(id,imageBytes,name);
        this.haveElements = haveElements;
        this.scores = scores;
    }

    //This constructor is called when inserting new Contexts.
    public Context(Bitmap image, String name) {
        super(image,name);
        this.haveElements = "false";
        this.scores = "false";
    }

    //This constructor is called when the app runs the first time.
    public Context(Drawable drawable, String name) {
        super(drawable,name);
        this.haveElements = "true";
        this.scores = "false";
    }

    public String getHasElements() {
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
