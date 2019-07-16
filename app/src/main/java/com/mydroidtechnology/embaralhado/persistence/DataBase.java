package com.mydroidtechnology.embaralhado.persistence;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.mydroidtechnology.embaralhado.model.Context;
import com.mydroidtechnology.embaralhado.model.Score;
import com.mydroidtechnology.embaralhado.model.Word;
import java.util.ArrayList;
import java.util.List;


//This class is the instance of Database in app.
public class DataBase {
    private SQLiteDatabase bd; //instance of SQLiteDatabase.
    private final String[] wordsColumns; //collumns of the table words.
    private final String[] contextsColumns; //collumns of the table contexts.
    private final String[] scoresColumns; //collumns of the table scores.

    public DataBase(android.content.Context context) {
        DataBaseOpenHelper dataBaseOpenHelper = DataBaseOpenHelper.getInstanceDataBaseOpenHelper(context);
        this.bd = dataBaseOpenHelper.getWritableDatabase();
        this.contextsColumns = new String[]{"_id", "name", "image", "has_elements", "scores"};
        this.wordsColumns = new String[]{"_id", "name", "image", "context_id"};
        this.scoresColumns = new String[]{"_id", "score", "image", "user_name", "context_id", "correct_answer_count","answer_total"};
    }

    //CRUD WORD
    //Inserts new Word in database.
    public void insertWord(Word word) {
        ContentValues values = new ContentValues();
        values.put("name", word.getName());
        values.put("image", word.getImageBytes());
        values.put("context_id", word.getContextID());
        this.bd.insert("words", null, values);
    }
    //Delete word in database by id.
    public void deleteWord(Word word) {
        bd.delete("words", "_id = ?", new String[]{word.getId().toString()});
    }
    //Return a list of Words from the Database by query.
    private List<Word> searchWordsDatabase(final Cursor cursor) {
        final List<Word> list = new ArrayList<>();
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
    //Return a list of Words from the Database by id contexts.    
    public List<Word> searchWordsDatabase(Integer contextID) {
        final Cursor cursor = bd.query("words", wordsColumns, "context_id = ?", new String[]{contextID.toString()}, null, null, "name ASC", null);
        return searchWordsDatabase(cursor);
    }
    //Return a Word from the Database by id word.
    public Word searchWordDatabase(Integer wordID) {
        final Cursor cursor = bd.query("words", this.wordsColumns, "_id = ?", new String[]{wordID.toString()}, null, null, null, null);
        return this.searchWordsDatabase(cursor).get(0);
    }
    //Update word in database by id.
    public void updateWord(Word word) {
        ContentValues values = new ContentValues();
        values.put("name", word.getName());
        values.put("image", word.getImageBytes());
        bd.update("words", values, "_id = ?", new String[]{word.getId().toString()});
    }


    //CRUD CONTEXTS
    //Inserts new Context in database.
    public void insertContext(Context context) {
        ContentValues values = new ContentValues();
        values.put("name", context.getName());
        values.put("image", context.getImageBytes());
        values.put("has_elements", context.getHasElements());
        values.put("scores", context.getScores());
        bd.insert("contexts", null, values);
    }
    //Delete context in database by id.
    public void deleteContext(Context context) {
        bd.delete("scores", "context_id = ?", new String[]{context.getId().toString()});
        bd.delete("words", "context_id = ?", new String[]{context.getId().toString()});
        bd.delete("contexts", "_id = ?", new String[]{context.getId().toString()});
    }
    //Return a list of Contexts from the database by query.
    private List<Context> searchContexts(Cursor cursorQuery){
        List<Context> contexts = new ArrayList<>();
        if (cursorQuery.getCount() > 0) {
            cursorQuery.moveToFirst();
            do {
                Integer id = cursorQuery.getInt(0);
                String name = cursorQuery.getString(1);
                byte[] image = cursorQuery.getBlob(2);
                String hasElements = cursorQuery.getString(3);
                String scores = cursorQuery.getString(4);
                Context w = new Context(id, image, name, hasElements, scores);
                contexts.add(w);
            } while (cursorQuery.moveToNext());
        }
        cursorQuery.close();
        return (contexts);
    }
    //Return a list of Contexts from the database.
    public List<Context> searchMyContextsDatabase() {
        final Cursor cursor = bd.query("contexts", this.contextsColumns, null, null, null, null, "name ASC");
        return this.searchContexts(cursor);
    }
    //Return a list of Contexts from the database if has elements(words).
    public List<Context> searchMyContextsWithWordsDatabase(String has_elements) {
        final Cursor cursor = bd.query("contexts", this.contextsColumns, "has_elements = ?", new String[]{has_elements}, null, null, "name ASC", null);
        return this.searchContexts(cursor);
    }
    //Return a list of Contexts from the database if has scores.
    public List<Context> searchMyContextsWithScoresDatabase(String scores) {
        final Cursor cursor = bd.query("contexts", this.contextsColumns, "scores = ?", new String[]{scores}, null, null, "name ASC", null);
        return this.searchContexts(cursor);
    }
    //Return a list of Context from the database by id context.
    public Context searchContextDatabase(Integer contextID) {
        final Cursor cursor = bd.query("contexts", this.contextsColumns, "_id = ?", new String[]{contextID.toString()}, null, null, null, null);
        return this.searchContexts(cursor).get(0);
    }
    //Return a list of Contexts from the database by name context.
    public Context searchContextDatabase(String nameContext) {
        final Cursor cursor = bd.query("contexts", this.contextsColumns, "name = ?", new String[]{nameContext}, null, null, null, null);
        return this.searchContexts(cursor).get(0);
    }
    //Update context in database by id.
    public void updateContext(Context context) {
        ContentValues values = new ContentValues();
        values.put("name", context.getName());
        values.put("image", context.getImageBytes());
        values.put("has_elements", context.getHasElements());
        values.put("scores", context.getScores());
        bd.update("contexts", values, "_id = ?", new String[]{context.getId().toString()});
    }

    //CRD SCORES
    //Insert new scores in database.
    public void insertScore(Score score) {
        ContentValues values = new ContentValues();
        values.put("score", score.getScore());
        values.put("image", score.getImageBytes());
        values.put("user_name", score.getName());
        values.put("context_id", score.getContextID());
        values.put("correct_answer_count", score.getCorrectAnswerCount());
        values.put("answer_total",score.getAnswerTotal());
        bd.insert("scores", null, values);
    }
    //Delete scores in database by id.
    public void deleteScore(Score score) {
        bd.delete("scores", "_id = " + score.getId(), null);
    }
    //Return a list of scores from the database by id context.
    public List<Score> searchScoresDatabase(Integer contextID) {
        List<Score> list = new ArrayList<>();
        final Cursor cursor = bd.query("scores", this.scoresColumns, "context_id = ?", new String[]{contextID.toString()}, null, null, "score DESC, correct_answer_count DESC, answer_total DESC", null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                Integer id = cursor.getInt(0);
                Double score = cursor.getDouble(1);
                byte[] image = cursor.getBlob(2);
                String user = cursor.getString(3);
                Integer contextId = cursor.getInt(4);
                Integer correctAnswerCount = cursor.getInt(5);
                Integer answerTotal = cursor.getInt(6);
                Score s = new Score(id, image, score, user, contextId,correctAnswerCount,answerTotal);
                list.add(s);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return (list);
    }

}
