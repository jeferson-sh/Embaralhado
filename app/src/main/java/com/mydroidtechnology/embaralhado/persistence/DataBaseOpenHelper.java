package com.mydroidtechnology.embaralhado.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


class DataBaseOpenHelper extends SQLiteOpenHelper {
    private static final String NAME_DATABASE = "DATABASE";
    private static final int VERSION_DATABASE = 2;
    private static DataBaseOpenHelper MY_DATABASE_CORE = null;


    private DataBaseOpenHelper(Context ctx) {
        super(ctx, NAME_DATABASE, null, VERSION_DATABASE);
    }

    //Create tables of the database.
    @Override
    public void onCreate(SQLiteDatabase bd) {
        bd.execSQL("create table contexts(_id integer primary key autoincrement, name text not null, image BLOB not null, has_elements text, scores text);");
        bd.execSQL("create table words(_id integer primary key autoincrement, name text not null, image BLOB not null, context_id int not null, FOREIGN KEY(context_id) REFERENCES contexts(_id));");
        bd.execSQL("create table scores(_id integer primary key autoincrement, score double not null, image BLOB not null, user_name text not null, context_id int not null, correct_answer_count double not null, answer_total double not null, FOREIGN KEY(context_id) REFERENCES contexts(_id));");
    }

    //This method is called when the version database change.
    @Override
    public void onUpgrade(SQLiteDatabase bd, int arg1, int arg2) {
        //UPDATE TABLE CONTEXTS
        bd.execSQL("ALTER TABLE contexts RENAME TO contexts_old");
        bd.execSQL("create table contexts(_id integer primary key autoincrement, name text not null, image BLOB not null, has_elements text, scores text);");
        bd.execSQL("INSERT INTO contexts (_id,name,image,has_elements,scores) SELECT _id,name,image,elements,scores FROM contexts_old;");
        bd.execSQL("DROP TABLE contexts_old");
        //UPDATE TABLE SCORES
        bd.execSQL("ALTER TABLE scores RENAME TO scores_old");
        bd.execSQL("create table scores(_id integer primary key autoincrement, score double not null, image BLOB not null, user_name text not null, context_id int not null, correct_answer_count double not null, answer_total double not null, FOREIGN KEY(context_id) REFERENCES contexts(_id));");
        bd.execSQL("INSERT INTO scores(_id, score, image, user_name, context_id, correct_answer_count,answer_total) SELECT _id, score, image, user, context_id, answer_count,answer_total FROM scores_old;");
        bd.execSQL("DROP TABLE scores_old");
    }
    //Return a singleton instance of database.
    static DataBaseOpenHelper getInstanceDataBaseOpenHelper(Context ctx) {
        if (MY_DATABASE_CORE == null) {
            MY_DATABASE_CORE = new DataBaseOpenHelper(ctx.getApplicationContext());
        }
        return MY_DATABASE_CORE;
    }

}
