package estagio3.ufpb.com.br.embaralhando;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

/**
 * Created by Jeferson on 10/11/2016.
 */
public class Score {

    private byte[] imageScoreBytes;
    private int score;
    private Bitmap image;
    private long id;
    private String name;

    public Score(byte[] imageScoreBytes, int score) {
        this.score = score;
        this.imageScoreBytes = imageScoreBytes;
        this.image = DatabaseBitmapUtility.getImage(imageScoreBytes);
    }

    public Score(Drawable drawable, int score) {
        this.image = ((BitmapDrawable) drawable).getBitmap();
        this.score = score;
        this.imageScoreBytes = DatabaseBitmapUtility.getBytes(this.image);
    }

    public Score(Drawable drawable, int score, String name) {
        this.name = name;
        this.image = ((BitmapDrawable) drawable).getBitmap();
        this.score = score;
        this.imageScoreBytes = DatabaseBitmapUtility.getBytes(this.image);
    }

    public Score(long id, byte[] imageScoreBytes, int score) {
        this.id = id;
        this.score = score;
        this.imageScoreBytes = imageScoreBytes;
        this.image = DatabaseBitmapUtility.getImage(imageScoreBytes);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
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
}
