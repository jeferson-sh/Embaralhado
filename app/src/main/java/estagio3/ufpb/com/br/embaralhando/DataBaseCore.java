package estagio3.ufpb.com.br.embaralhando;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Jeferson on 20/11/2016.
 */
public class DataBaseCore extends SQLiteOpenHelper {
    private static final String NAME_DATABASE = "DATABASE";
    private static final int VERSION_DATABASE = 1;


    public DataBaseCore(Context ctx){
        super(ctx, NAME_DATABASE, null, VERSION_DATABASE);
    }


    @Override
    public void onCreate(SQLiteDatabase bd) {
        bd.execSQL("create table words(_id integer primary key autoincrement, name text not null, image BLOB not null);");
        bd.execSQL("create table scores(_id integer primary key autoincrement, score int not null, image BLOB not null);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase bd, int arg1, int arg2) {
        bd.execSQL("drop table words;");
        bd.execSQL("drop table scores;");
        onCreate(bd);
    }

}
