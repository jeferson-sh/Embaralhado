package estagio3.ufpb.com.br.projeto1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Jeferson on 20/11/2016.
 */
public class BDCore extends SQLiteOpenHelper {
    private static final String NOME_BD = "BD";
    private static final int VERSAO_BD = 1;


    public BDCore(Context ctx){
        super(ctx, NOME_BD, null, VERSAO_BD);
    }


    @Override
    public void onCreate(SQLiteDatabase bd) {
        bd.execSQL("create table palavra(_id integer primary key autoincrement, nome text not null, image BLOB not null);");
        bd.execSQL("create table pontos(_id integer primary key autoincrement, pontos int not null, image BLOB not null);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase bd, int arg1, int arg2) {
        bd.execSQL("drop table palavra;");
        bd.execSQL("drop table pontos;");
        onCreate(bd);
    }

}
