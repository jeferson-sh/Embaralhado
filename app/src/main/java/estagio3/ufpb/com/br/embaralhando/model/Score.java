package estagio3.ufpb.com.br.embaralhando.model;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import estagio3.ufpb.com.br.embaralhando.util.DatabaseBitmapUtil;

/**
 * Created by Jeferson on 10/11/2016.
 */
public class Score {

    private byte[] imageScoreBytes;
    private int score;
    private Bitmap image;
    private Integer id;
    private String user;
    private Integer contextId;

    public Score(Drawable drawable, int score, String name, Integer contextId) {
        this.contextId = contextId;
        this.user = name;
        this.image = ((BitmapDrawable) drawable).getBitmap();
        this.score = score;
        this.imageScoreBytes = DatabaseBitmapUtil.getBytes(this.image);
    }

    public Score(Integer id, byte[] imageScoreBytes, int score, String name, Integer contextId) {
        this.user = name;
        this.contextId = contextId;
        this.id = id;
        this.score = score;
        this.imageScoreBytes = imageScoreBytes;
        this.image = DatabaseBitmapUtil.getImage(imageScoreBytes);
    }

    public String getUser() {
        return user;
    }

    public void setUser(String name) {
        this.user = name;
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
        setImageScoreBytes(DatabaseBitmapUtil.getBytes(image));
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

    public void setImageScoreBytes(byte[] imageScoreBytes) {
        this.imageScoreBytes = imageScoreBytes;
    }

    public Integer getContextId() {
        return contextId;
    }

    public void setContextId(Integer contextId) {
        this.contextId = contextId;
    }
}
