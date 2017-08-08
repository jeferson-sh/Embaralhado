package estagio3.ufpb.com.br.embaralhando.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Jeferson on 20/11/2016.
 */
public class DataBaseOpenHelper extends SQLiteOpenHelper {
    private static final String NAME_DATABASE = "DATABASE";
    private static final int VERSION_DATABASE = 1;
    private static DataBaseOpenHelper MYDATABASECORE = null;


    private DataBaseOpenHelper(Context ctx) {
        super(ctx, NAME_DATABASE, null, VERSION_DATABASE);
    }


    @Override
    public void onCreate(SQLiteDatabase bd) {
        bd.execSQL("create table words(_id integer primary key autoincrement, name text not null, image BLOB not null, context text not null );");
        bd.execSQL("create table scores(_id integer primary key autoincrement, score int not null, image BLOB not null);");
        bd.execSQL("create table contexts(_id integer primary key autoincrement, name text not null, image BLOB not null);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase bd, int arg1, int arg2) {
        bd.execSQL("drop table words;");
        bd.execSQL("drop table scores;");
        bd.execSQL("drop table contexts;");
        onCreate(bd);
    }

    public static DataBaseOpenHelper getInstanceDataBaseOpenHelper(Context ctx) {
        if (MYDATABASECORE == null) {
            MYDATABASECORE = new DataBaseOpenHelper(ctx.getApplicationContext());
        }
        return MYDATABASECORE;
    }

}
