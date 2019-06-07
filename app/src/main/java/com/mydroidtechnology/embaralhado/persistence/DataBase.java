package com.mydroidtechnology.embaralhado.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.mydroidtechnology.embaralhado.model.Category;
import com.mydroidtechnology.embaralhado.model.Score;
import com.mydroidtechnology.embaralhado.model.Word;
import java.util.ArrayList;
import java.util.List;


public class DataBase {
    private SQLiteDatabase bd;
    private String[] wordsColumns;
    private String[] categoryColumns;
    private String[] scoreColumns;

    public DataBase(Context context) {
        DataBaseOpenHelper dataBaseOpenHelper = DataBaseOpenHelper.getInstanceDataBaseOpenHelper(context);
        bd = dataBaseOpenHelper.getWritableDatabase();
        this.categoryColumns = new String[]{"_id", "name", "image", "elements", "scores"};
        this.wordsColumns = new String[]{"_id", "name", "image", "context_id"};
        this.scoreColumns = new String[]{"_id", "score", "image", "user", "context_id", "answer_count","answer_total"};
    }

    public void insertWord(Word word) {
        ContentValues values = new ContentValues();
        values.put("name", word.getName());
        values.put("image", word.getImageBytes());
        values.put("context_id", word.getContextID());
        bd.insert("words", null, values);
    }

    public void insertCategory(Category category) {
        ContentValues values = new ContentValues();
        values.put("name", category.getName());
        values.put("image", category.getImageBytes());
        values.put("elements", category.getHaveElements());
        values.put("scores", category.getScores());
        bd.insert("contexts", null, values);
    }

    public void insertScore(Score score) {
        ContentValues values = new ContentValues();
        values.put("score", score.getScore());
        values.put("image", score.getImageBytes());
        values.put("user", score.getName());
        values.put("context_id", score.getContextID());
        values.put("answer_count", score.getAnswerCount());
        values.put("answer_total",score.getAnswerTotal());
        bd.insert("scores", null, values);
    }


    public void deleteWord(Word word) {
        bd.delete("words", "_id = " + word.getId(), null);
    }

    public void deleteCategory(Category category) {
        bd.delete("scores", "context_id = ?", new String[]{category.getId().toString()});
        bd.delete("words", "context_id = ?", new String[]{category.getId().toString()});
        bd.delete("contexts", "_id = " + category.getId(), null);
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

    private List<Category> searchCategories(Cursor cursorQuery){
        List<Category> categories = new ArrayList<>();
        if (cursorQuery.getCount() > 0) {
            cursorQuery.moveToFirst();
            do {
                Integer id = cursorQuery.getInt(0);
                String name = cursorQuery.getString(1);
                byte[] image = cursorQuery.getBlob(2);
                String elements = cursorQuery.getString(3);
                String scores = cursorQuery.getString(4);
                Category w = new Category(id, image, name, elements, scores);
                categories.add(w);
            } while (cursorQuery.moveToNext());
        }
        cursorQuery.close();
        return (categories);
    }

    public List<Category> searchMyCategoriesDatabase() {
        final Cursor cursor = bd.query("contexts", this.categoryColumns, null, null, null, null, "name ASC");
        return searchCategories(cursor);
    }

    public List<Category> searchMyCategoriesWithWordsDatabase(String elements) {
        final Cursor cursor = bd.query("contexts", this.categoryColumns, "elements = ?", new String[]{"" + elements}, null, null, "name ASC", null);
        return searchCategories(cursor);
    }

    public List<Category> searchMyCategoriesWithScoresDatabase(String scores) {
        final Cursor cursor = bd.query("contexts", this.categoryColumns, "scores = ?", new String[]{"" + scores}, null, null, "name ASC", null);
        return searchCategories(cursor);
    }

    public List<Score> searchScoresDatabase(Integer contextID) {
        List<Score> list = new ArrayList<>();
        final Cursor cursor = bd.query("scores", this.scoreColumns, "context_id = ?", new String[]{"" + contextID}, null, null, "score DESC, answer_count DESC, answer_total DESC", null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                Integer id = cursor.getInt(0);
                Double score = cursor.getDouble(1);
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

    private Category searchCategory(Cursor cursorQuery){
        cursorQuery.moveToFirst();
        int id = cursorQuery.getInt(0);
        String name = cursorQuery.getString(1);
        byte[] image = cursorQuery.getBlob(2);
        String elements = cursorQuery.getString(3);
        String scores = cursorQuery.getString(4);
        Category category = new Category(id, image, name, elements, scores);
        cursorQuery.close();
        return (category);
    }

    public Category searchCategoryDatabase(Integer categoryID) {
        final Cursor cursor = bd.query("contexts", this.categoryColumns, "_id = ?", new String[]{"" + categoryID}, null, null, null, null);
        return searchCategory(cursor);
    }

    public Category searchCategoryDatabase(String nameCategory) {
        final Cursor cursor = bd.query("contexts", this.categoryColumns, "name = ?", new String[]{"" + nameCategory}, null, null, null, null);
        return searchCategory(cursor);
    }

    public Word searchWordDatabase(Integer wordID) {
        final Cursor cursor = bd.query("words", this.wordsColumns, "_id = ?", new String[]{"" + wordID}, null, null, null, null);
        cursor.moveToFirst();
        int id = cursor.getInt(0);
        String name = cursor.getString(1);
        byte[] image = cursor.getBlob(2);
        Integer contextID = cursor.getInt(3);
        Word word = new Word(id, image, name, contextID);
        cursor.close();
        return (word);
    }

    public void updateCategory(Category category) {
        ContentValues values = new ContentValues();
        values.put("name", category.getName());
        values.put("image", category.getImageBytes());
        values.put("elements", category.getHaveElements());
        values.put("scores", category.getScores());
        bd.update("contexts", values, "_id = ?", new String[]{"" + category.getId()});
    }

    public void updateWord(Word word) {
        ContentValues values = new ContentValues();
        values.put("name", word.getName());
        values.put("image", word.getImageBytes());
        bd.update("words", values, "_id = ?", new String[]{"" + word.getId()});
    }
}
