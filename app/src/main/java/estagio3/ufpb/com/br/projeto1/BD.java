package estagio3.ufpb.com.br.projeto1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jeferson on 20/11/2016.
 */

public class BD {
    private SQLiteDatabase bd;

    public BD(Context context){
        BDCore auxBd = new BDCore(context);
        bd = auxBd.getWritableDatabase();
    }


    public void inserirPalavra(Palavra palavra){
        ContentValues valores = new ContentValues();
        valores.put("nome", palavra.getPalavra());
        valores.put("image", palavra.getImageBytes());

        bd.insert("palavra", null, valores);
    }
    public void inserirPontos(Pontuação pontos){
        ContentValues valores = new ContentValues();
        valores.put("pontos", pontos.getPontos());
        valores.put("image", pontos.getImageScoreBytes());

        bd.insert("pontos", null, valores);
    }


    public void atualizarPalavra(Palavra palavra){
        ContentValues valores = new ContentValues();
        valores.put("nome", palavra.getPalavra());
        valores.put("image", palavra.getImageBytes());

        bd.update("palavra", valores, "_id = ?", new String[]{""+palavra.getId()});
    }
    public void atualizarPontos(Pontuação pontuação){
        ContentValues valores = new ContentValues();
        valores.put("pontos", pontuação.getPontos());
        valores.put("image", pontuação.getImageScoreBytes());

        bd.update("pontos", valores, "_id = ?", new String[]{""+pontuação.getId()});
    }


    public void deletarPalavra(Palavra palavra){
        bd.delete("palavra", "_id = "+palavra.getId(), null);
    }

    public void deletarPontos(Pontuação pontuação){
        bd.delete("pontos", "_id = "+pontuação.getId(), null);
    }


    public List<Palavra> buscarPalavras(){
        List<Palavra> list = new ArrayList<Palavra>();
        String[] colunas = new String[]{"_id", "nome", "image"};

        Cursor cursor = bd.query("palavra", colunas, null, null, null, null, null);

        if(cursor.getCount() > 0){
            cursor.moveToFirst();

            do{

                long id = cursor.getLong(0);
                String palavra = cursor.getString(1);
                byte [] image = cursor.getBlob(2);
                Palavra p = new Palavra(id,image,palavra);
                list.add(p);

            }while(cursor.moveToNext());
        }

        return(list);
    }

    public List<Pontuação> buscarPontos(){
        List<Pontuação> list = new ArrayList<Pontuação>();
        String[] colunas = new String[]{"_id", "pontos", "image"};

        Cursor cursor = bd.query("pontos", colunas, null, null, null, null, null);

        if(cursor.getCount() > 0){
            cursor.moveToFirst();

            do{

                long id = cursor.getLong(0);
                int pontos = cursor.getInt(1);
                byte [] image = cursor.getBlob(2);
                Pontuação p = new Pontuação(id,image,pontos);
                list.add(p);

            }while(cursor.moveToNext());
        }

        return(list);
    }
}
