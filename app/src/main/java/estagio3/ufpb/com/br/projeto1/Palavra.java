package estagio3.ufpb.com.br.projeto1;

/**
 * Created by Jeferson on 10/11/2016.
 */
public class Palavra {

    private String palavra;
    private int idImage;

    public Palavra (String palavra, int idImage){
        this.palavra = palavra;
        this.idImage = idImage;
    }

    public String getPalavra() {
        return palavra;
    }

    public void setPalavra(String palavra) {
        this.palavra = palavra;
    }

    public int getIdImage() {
        return idImage;
    }

    public void setIdImage(int idImage) {
        this.idImage = idImage;
    }
}
