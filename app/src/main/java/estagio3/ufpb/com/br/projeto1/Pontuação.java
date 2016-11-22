package estagio3.ufpb.com.br.projeto1;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

/**
 * Created by Jeferson on 10/11/2016.
 */
public class Pontuação {

    private byte[] imageScoreBytes;
    private int pontos;
    private Bitmap image;
    private long id;

    public Pontuação(byte[] imageScoreBytes, int pontos){
        this.pontos = pontos;
        this.imageScoreBytes = imageScoreBytes;
        this.image = DbBitmapUtility.getImage(imageScoreBytes);
    }

    public Pontuação(Drawable drawable, int pontos) {
        this.image = ((BitmapDrawable) drawable).getBitmap();
        this.pontos = pontos;
        this.imageScoreBytes = DbBitmapUtility.getBytes(this.image);
    }

    public Pontuação(long id, byte[] imageScoreBytes, int pontos) {
        this.id = id;
        this.pontos = pontos;
        this.imageScoreBytes = imageScoreBytes;
        this.image = DbBitmapUtility.getImage(imageScoreBytes);
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

    public int getPontos() {
        return pontos;
    }

    public void setPontos(int pontos) {
        this.pontos = pontos;
    }

    public byte[] getImageScoreBytes() {
        return imageScoreBytes;
    }

    public void setImageScoreBytes(byte[] imageScoreBytes) {
        this.imageScoreBytes = imageScoreBytes;
    }
}
