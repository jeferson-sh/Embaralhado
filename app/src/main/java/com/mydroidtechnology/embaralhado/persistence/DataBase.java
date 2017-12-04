package com.mydroidtechnology.embaralhado.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import com.mydroidtechnology.embaralhado.model.Categorie;
import com.mydroidtechnology.embaralhado.model.Score;
import com.mydroidtechnology.embaralhado.model.Word;

/*
 * Created by Jeferson on 20/11/2016.
 */

public class DataBase {
    private SQLiteDatabase bd;

    public DataBase(Context context) {
        DataBaseOpenHelper dataBaseOpenHelper = DataBaseOpenHelper.getInstanceDataBaseOpenHelper(context);
        bd = dataBaseOpenHelper.getWritableDatabase();
    }


    public void insertWord(Word word) {
        ContentValues valores = new ContentValues();
        valores.put("name", word.getName());
        valores.put("image", word.getImageBytes());
        valores.put("context_id", word.getContext());

        bd.insert("words", null, valores);
    }

    public void insertCategorie(Categorie categorie) {
        ContentValues valores = new ContentValues();
        valores.put("name", categorie.getName());
        valores.put("image", categorie.getImageBytes());
        valores.put("elements", categorie.getElements());
        valores.put("scores", categorie.getScores());
        bd.insert("contexts", null, valores);
    }

    public void insertScore(Score score) {
        ContentValues valores = new ContentValues();
        valores.put("score", score.getScore());
        valores.put("image", score.getImageScoreBytes());
        valores.put("user", score.getUser());
        valores.put("context_id", score.getContextId());
        valores.put("answer_count", score.getAnswerCount());
        valores.put("answer_total",score.getAnswerTotal());
        bd.insert("scores", null, valores);
    }


    public void updateWord(Word word) {
        ContentValues valores = new ContentValues();
        valores.put("name", word.getName());
        valores.put("image", word.getImageBytes());
        bd.update("words", valores, "_id = ?", new String[]{"" + word.getId()});
    }


    public void deleteWord(Word word) {
        bd.delete("words", "_id = " + word.getId(), null);
    }

    public void deleteCategorie(Categorie categorie) {
        bd.delete("scores", "context_id = ?", new String[]{categorie.getId().toString()});
        bd.delete("words", "context_id = ?", new String[]{categorie.getId().toString()});
        bd.delete("contexts", "_id = " + categorie.getId(), null);
    }

    public void deleteScore(Score score) {
        bd.delete("scores", "_id = " + score.getId(), null);
    }


    public List<Word> searchWordsDatabase(Integer contextID) {
        final List<Word> list = new ArrayList<>();
        String[] columns = new String[]{"_id", "name", "image", "context_id"};

        final Cursor cursor = bd.query("words", columns, "context_id = ?", new String[]{"" + contextID}, null, null, "name ASC", null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {

                Integer id = cursor.getInt(0);
                String name = cursor.getString(1);
                byte[] image = cursor.getBlob(2);
                Integer context = cursor.getInt(3);
                Word w = new Word(id, image, name, context);
                list.add(w);

            } while (cursor.moveToNext());
        }

        cursor.close();

        return (list);
    }

    public List<Categorie> searchMyCategoriesDatabase() {
        List<Categorie> list = new ArrayList<>();
        String[] columns = new String[]{"_id", "name", "image", "elements", "scores"};

        Cursor cursor = bd.query("contexts", columns, null, null, null, null, "name ASC");

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {

                Integer id = cursor.getInt(0);
                String name = cursor.getString(1);
                byte[] image = cursor.getBlob(2);
                String elements = cursor.getString(3);
                String scores = cursor.getString(4);
                Categorie w = new Categorie(id, image, name, elements, scores);
                list.add(w);

            } while (cursor.moveToNext());
        }

        cursor.close();

        return (list);
    }

    public List<Categorie> searchMyCategoriesWordsDatabase(String elements) {
        List<Categorie> list = new ArrayList<>();
        String[] columns = new String[]{"_id", "name", "image", "elements", "scores"};

        final Cursor cursor = bd.query("contexts", columns, "elements = ?", new String[]{"" + elements}, null, null, "name ASC", null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {

                Integer id = cursor.getInt(0);
                String name = cursor.getString(1);
                byte[] image = cursor.getBlob(2);
                String ele = cursor.getString(3);
                String scores = cursor.getString(4);
                Categorie w = new Categorie(id, image, name, ele, scores);
                list.add(w);

            } while (cursor.moveToNext());
        }

        cursor.close();

        return (list);
    }

    public List<Categorie> searchMyCategoriesScoresDatabase(String scores) {
        List<Categorie> list = new ArrayList<>();
        String[] columns = new String[]{"_id", "name", "image", "elements", "scores"};

        final Cursor cursor = bd.query("contexts", columns, "scores = ?", new String[]{"" + scores}, null, null, "name ASC", null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {

                Integer id = cursor.getInt(0);
                String name = cursor.getString(1);
                byte[] image = cursor.getBlob(2);
                String elements = cursor.getString(3);
                String score = cursor.getString(4);
                Categorie w = new Categorie(id, image, name, elements, score);
                list.add(w);

            } while (cursor.moveToNext());
        }

        cursor.close();

        return (list);
    }


    public List<Score> searchScoresDatabase(Integer contextID) {
        List<Score> list = new ArrayList<>();
        String[] columns = new String[]{"_id", "score", "image", "user", "context_id", "answer_count","answer_total"};

        final Cursor cursor = bd.query("scores", columns, "context_id = ?", new String[]{"" + contextID}, null, null, "score DESC, answer_count DESC, answer_total DESC", null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {

                Integer id = cursor.getInt(0);
                int score = cursor.getInt(1);
                byte[] image = cursor.getBlob(2);
                String user = cursor.getString(3);
                Integer contextId = cursor.getInt(4);
                Integer answerCount = cursor.getInt(5);
                Integer answerTotal = cursor.getInt(6);
                Score s = new Score(id, image, score, user, contextId,answerCount,answerTotal);
                list.add(s);

            } while (cursor.moveToNext());
        }

        cursor.close();

        return (list);
    }

    public Categorie searchCategorieDatabase(Integer categorieID) {
        String[] columns = new String[]{"_id", "name", "image", "elements", "scores"};
        Cursor cursor = bd.query("contexts", columns, "_id = ?", new String[]{"" + categorieID}, null, null, null, null);
        cursor.moveToFirst();
        int id = cursor.getInt(0);
        String name = cursor.getString(1);
        byte[] image = cursor.getBlob(2);
        String elements = cursor.getString(3);
        String scores = cursor.getString(4);
        Categorie categorie = new Categorie(id, image, name, elements, scores);
        cursor.close();
        return (categorie);
    }

    public Word searchWordDatabase(Integer wordID) {
        String[] columns = new String[]{"_id", "name", "image", "context_id"};
        Cursor cursor = bd.query("words", columns, "_id = ?", new String[]{"" + wordID}, null, null, null, null);
        cursor.moveToFirst();
        int id = cursor.getInt(0);
        String name = cursor.getString(1);
        byte[] image = cursor.getBlob(2);
        Integer contextID = cursor.getInt(3);
        Word word = new Word(id, image, name, contextID);
        cursor.close();
        return (word);
    }


    public Categorie searchCategorieDatabase(String nameContext) {
        String[] columns = new String[]{"_id", "name", "image", "elements", "scores"};
        Cursor cursor = bd.query("contexts", columns, "name = ?", new String[]{"" + nameContext}, null, null, null, null);
        cursor.moveToFirst();
        int id = cursor.getInt(0);
        String name = cursor.getString(1);
        byte[] image = cursor.getBlob(2);
        String elements = cursor.getString(3);
        String scores = cursor.getString(4);
        Categorie categorie = new Categorie(id, image, name, elements, scores);
        cursor.close();
        return (categorie);
    }


    public void updateCategorie(Categorie categorie) {
        ContentValues valores = new ContentValues();
        valores.put("name", categorie.getName());
        valores.put("image", categorie.getImageBytes());
        valores.put("elements", categorie.getElements());
        valores.put("scores", categorie.getScores());
        bd.update("contexts", valores, "_id = ?", new String[]{"" + categorie.getId()});
    }
}
