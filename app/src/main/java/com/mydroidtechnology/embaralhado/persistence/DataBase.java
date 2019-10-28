package com.mydroidtechnology.embaralhado.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mydroidtechnology.embaralhado.exceptions.DatabaseException;
import com.mydroidtechnology.embaralhado.models.ContextModel;
import com.mydroidtechnology.embaralhado.models.ScoreModel;
import com.mydroidtechnology.embaralhado.models.WordModel;

import java.util.ArrayList;
import java.util.List;


//This class is the instance of Database in app.
public class DataBase {
    private SQLiteDatabase bd; //instance of SQLiteDatabase.
    private final String wordTableName;
    private final String[] wordsColumns; //columns of the table words.
    private final String contextTableName;
    private final String[] contextsColumns; //columns of the table contexts.
    private final String scoreTableName;
    private final String[] scoresColumns; //columns of the table scores.

    public DataBase(Context context) {
        DataBaseOpenHelper dataBaseOpenHelper = DataBaseOpenHelper.getInstanceDataBaseOpenHelper(context);
        this.bd = dataBaseOpenHelper.getWritableDatabase();
        this.wordTableName = "words";
        this.contextsColumns = new String[]{"_id", "name", "image", "has_elements", "scores"};
        this.contextTableName = "contexts";
        this.wordsColumns = new String[]{"_id", "name", "image", "context_id"};
        this.scoreTableName = "scores";
        this.scoresColumns = new String[]{"_id", "score", "image", "user_name", "context_id", "correct_answer_count", "answer_total"};
    }

    //CRUD WORD
    //Inserts new WordModel in database.
    public void insertWord(WordModel wordModel) throws DatabaseException {
        ContentValues values = new ContentValues();
        values.put(wordsColumns[1], wordModel.getName());
        values.put(wordsColumns[2], wordModel.getImageBytes());
        values.put(wordsColumns[3], wordModel.getContextID());
        try {
            this.bd.insert(wordTableName, null, values);

        } catch (RuntimeException e) {
            throw new DatabaseException("Error inserting word in database.");
        }
    }

    //Delete wordModel in database by id.
    public void deleteWord(WordModel wordModel) throws DatabaseException {
        try {
            bd.delete(wordTableName, wordsColumns[0].concat(" = ?"), new String[]{wordModel.getId().toString()});

        } catch (RuntimeException e) {
            throw new DatabaseException("Error deleting word in database.");
        }
    }


    //Return a list of Words from the Database by query.
    private List<WordModel> searchWordsDatabase(final Cursor cursor) throws DatabaseException {
        final List<WordModel> list = new ArrayList<>();
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                Integer id = cursor.getInt(0);
                String name = cursor.getString(1);
                byte[] image = cursor.getBlob(2);
                Integer context = cursor.getInt(3);
                WordModel w = new WordModel(id, image, name, context);
                list.add(w);
            } while (cursor.moveToNext());
        } else {
            throw new DatabaseException("Object not found");
        }
        cursor.close();
        return (list);
    }

    //Return a list of Words from the Database by id contexts.
    public List<WordModel> searchWordsDatabase(Integer contextID) throws DatabaseException {
        final Cursor cursor = bd.query(wordTableName, wordsColumns, wordsColumns[3].concat(" = ?"), new String[]{contextID.toString()}, null, null, "name ASC", null);
        return searchWordsDatabase(cursor);
    }

    //Return a WordModel from the Database by id word.
    public WordModel searchWordDatabase(Integer wordID) throws DatabaseException {
        final Cursor cursor = bd.query(wordTableName, this.wordsColumns, wordsColumns[0].concat(" = ?"), new String[]{wordID.toString()}, null, null, null, null);
        return this.searchWordsDatabase(cursor).get(0);
    }

    //Update wordModel in database by id.
    public void updateWord(WordModel wordModel) throws DatabaseException {
        ContentValues values = new ContentValues();
        values.put(wordsColumns[1], wordModel.getName());
        values.put(wordsColumns[2], wordModel.getImageBytes());
        try {
            bd.update(wordTableName, values, wordsColumns[0].concat(" = ?"), new String[]{wordModel.getId().toString()});

        } catch (RuntimeException e) {
            throw new DatabaseException("Error updating word in database.");
        }
    }


    //CRUD CONTEXTS
    //Inserts new ContextModel in database.
    public void insertContext(ContextModel contextModel) throws DatabaseException {
        ContentValues values = new ContentValues();
        values.put(contextsColumns[1], contextModel.getName());
        values.put(contextsColumns[2], contextModel.getImageBytes());
        values.put(contextsColumns[3], contextModel.getHasElements());
        values.put(contextsColumns[4], contextModel.getScores());
        try {
            bd.insert(contextTableName, null, values);
        } catch (RuntimeException e) {
            throw new DatabaseException("Error inserting context in database");
        }
    }

    //Delete contextModel in database by id.
    public void deleteContext(ContextModel contextModel) throws DatabaseException {
        try {
            bd.delete(scoreTableName, scoresColumns[4].concat(" = ?"), new String[]{contextModel.getId().toString()});
            bd.delete(wordTableName, wordsColumns[3].concat(" = ?"), new String[]{contextModel.getId().toString()});
            bd.delete(contextTableName, contextsColumns[0].concat(" = ?"), new String[]{contextModel.getId().toString()});

        } catch (RuntimeException e) {
            throw new DatabaseException("Error delete context in database");
        }
    }

    //Return a list of Contexts from the database by query.
    private List<ContextModel> searchContexts(Cursor cursorQuery) throws DatabaseException {
        List<ContextModel> contextModels = new ArrayList<>();
        if (cursorQuery.getCount() > 0) {
            cursorQuery.moveToFirst();
            do {
                Integer id = cursorQuery.getInt(0);
                String name = cursorQuery.getString(1);
                byte[] image = cursorQuery.getBlob(2);
                String hasElements = cursorQuery.getString(3);
                String scores = cursorQuery.getString(4);
                ContextModel w = new ContextModel(id, image, name, hasElements, scores);
                contextModels.add(w);
            } while (cursorQuery.moveToNext());
        } else {
            throw new DatabaseException("Object not found");
        }
        cursorQuery.close();
        return contextModels;
    }

    //Return a list of Contexts from the database.
    public List<ContextModel> searchMyContextsDatabase() throws DatabaseException {
        final Cursor cursor = bd.query(contextTableName, this.contextsColumns, null, null, null, null, "name ASC");
        return this.searchContexts(cursor);
    }

    //Return a list of Contexts from the database if has elements(words).
    public List<ContextModel> searchMyContextsWithWordsDatabase(String has_elements) throws
            DatabaseException {
        final Cursor cursor = bd.query(contextTableName, this.contextsColumns, "has_elements = ?", new String[]{has_elements}, null, null, "name ASC", null);
        return this.searchContexts(cursor);
    }

    //Return a list of Contexts from the database if has scores.
    public List<ContextModel> searchMyContextsWithScoresDatabase(String scores) throws
            DatabaseException {
        final Cursor cursor = bd.query(contextTableName, this.contextsColumns, "scores = ?", new String[]{scores}, null, null, "name ASC", null);
        return this.searchContexts(cursor);
    }

    //Return an object of ContextModel from the database by id context.
    public ContextModel searchContextDatabase(Integer contextID) throws DatabaseException {
        final Cursor cursor = bd.query(contextTableName, this.contextsColumns, "_id = ?", new String[]{contextID.toString()}, null, null, null, null);
        return this.searchContexts(cursor).get(0);
    }

    //Return an object of Contexts from the database by name context.
    public ContextModel searchContextDatabase(String nameContext) throws DatabaseException {
        final Cursor cursor = bd.query(contextTableName, this.contextsColumns, "name = ?", new String[]{nameContext}, null, null, null, null);
        return this.searchContexts(cursor).get(0);
    }

    //Update contextModel in database by id.
    public void updateContext(ContextModel contextModel) throws DatabaseException {
        ContentValues values = new ContentValues();
        values.put(contextsColumns[1], contextModel.getName());
        values.put(contextsColumns[2], contextModel.getImageBytes());
        values.put(contextsColumns[3], contextModel.getHasElements());
        values.put(contextsColumns[4], contextModel.getScores());
        try {
            bd.update(contextTableName, values, "_id = ?", new String[]{contextModel.getId().toString()});
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            throw new DatabaseException("Object not found.");
        }
    }


    //CRD SCORES
    //Insert new scores in database.
    public void insertScore(ScoreModel scoreModel) throws DatabaseException {
        ContentValues values = new ContentValues();
        values.put(scoresColumns[1], scoreModel.getScore());
        values.put(scoresColumns[2], scoreModel.getImageBytes());
        values.put(scoresColumns[3], scoreModel.getName());
        values.put(scoresColumns[4], scoreModel.getContextID());
        values.put(scoresColumns[5], scoreModel.getCorrectAnswerCount());
        values.put(scoresColumns[6], scoreModel.getAnswerTotal());
        try {
            bd.insert(scoreTableName, null, values);
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            throw new DatabaseException("Insert error in database.");
        }
    }

    //Delete scores in database by id.
    public void deleteScore(ScoreModel scoreModel) throws DatabaseException {
        try {
            bd.delete(scoreTableName, "_id = " + scoreModel.getId(), null);
        } catch (RuntimeException ex) {
            throw new DatabaseException("The object can't be deleted.");
        }
    }

    //Return a list of scores from the database by id context.
    public List<ScoreModel> searchScoresDatabase(Integer contextID) throws DatabaseException {
        List<ScoreModel> list = new ArrayList<>();
        final Cursor cursor = bd.query(scoreTableName, this.scoresColumns, "context_id = ?", new String[]{contextID.toString()}, null, null, "score DESC, correct_answer_count DESC, answer_total DESC", null);
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
                ScoreModel s = new ScoreModel(id, image, score, user, contextId, correctAnswerCount, answerTotal);
                list.add(s);
            } while (cursor.moveToNext());
        }else{
            throw new DatabaseException("objects not found");
        }
        cursor.close();
        return (list);
    }
}
