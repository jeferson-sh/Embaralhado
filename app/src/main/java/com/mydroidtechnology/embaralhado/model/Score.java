package com.mydroidtechnology.embaralhado.model;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.mydroidtechnology.embaralhado.util.ConvertImageUtil;

/*
 * Created by Jeferson on 10/11/2016.
 */
public class Score {

    private byte[] imageScoreBytes;
    private int score;
    private Bitmap image;
    private Integer id;
    private String user;
    private Integer contextId;
    private Integer answerCount;
    private Integer answerTotal;

    public Score(Drawable drawable, int score, String name, Integer contextId, Integer answerCount, Integer answerTotal) {
        this.contextId = contextId;
        this.user = name;
        this.image = ((BitmapDrawable) drawable).getBitmap();
        this.score = score;
        this.imageScoreBytes = ConvertImageUtil.getBytes(this.image);
        this.answerCount = answerCount;
        this.answerTotal = answerTotal;

    }

    public Score(Integer id, byte[] imageScoreBytes, int score, String name, Integer contextId, Integer answerCount, Integer answerTotal) {
        this.user = name;
        this.contextId = contextId;
        this.id = id;
        this.score = score;
        this.imageScoreBytes = imageScoreBytes;
        this.image = ConvertImageUtil.getImage(imageScoreBytes);
        this.answerCount = answerCount;
        this.answerTotal = answerTotal;
    }

    public String getUser() {
        return user;
    }


    public long getId() {
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
        setImageScoreBytes(ConvertImageUtil.getBytes(image));
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public byte[] getImageScoreBytes() {
        return imageScoreBytes;
    }

    private void setImageScoreBytes(byte[] imageScoreBytes) {
        this.imageScoreBytes = imageScoreBytes;
    }

    public Integer getContextId() {
        return contextId;
    }

    public Integer getAnswerCount() {
        return answerCount;
    }

    public Integer getAnswerTotal() {
        return answerTotal;
    }
}
