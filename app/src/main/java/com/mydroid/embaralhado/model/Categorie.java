package com.mydroid.embaralhado.model;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.mydroid.embaralhado.util.ConvertImageUtil;

/*
 * Created by Jeferson on 10/11/2016.
 */
public class Categorie {

    private byte[] imageBytes;
    private String name;
    private Bitmap image;
    private Integer id;
    private String elements;
    private String scores;

    public Categorie(Integer id, byte[] imageBytes, String name, String elements, String scores) {
        this.id = id;
        this.name = name;
        this.imageBytes = imageBytes;
        this.image = ConvertImageUtil.getImage(imageBytes);
        this.elements = elements;
        this.scores = scores;
    }

    public Categorie(Bitmap image, String name) {
        this.name = name;
        this.image = image;
        this.imageBytes = ConvertImageUtil.getBytes(this.image);
        this.elements = "false";
        this.scores = "false";
    }

    public Categorie(Drawable drawable, String name) {
        this.image = ((BitmapDrawable) drawable).getBitmap();
        this.name = name;
        this.imageBytes = ConvertImageUtil.getBytes(this.image);
        this.elements = "true";
        this.scores = "false";
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
        this.setImageBytes(ConvertImageUtil.getBytes(image));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getImageBytes() {
        return imageBytes;
    }

    private void setImageBytes(byte[] imageBytes) {
        this.imageBytes = imageBytes;
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
