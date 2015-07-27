package com.example.witz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Iris on 24.07.2015.
 */
public class SQLiteHelper extends SQLiteOpenHelper {

    // database version
    private static final int database_VERSION = 1;
            // database name

    private static final String database_NAME = "JokeDB";

    private static final String table_JOKE = "jokes";

    private static final String id = "id";

    private static final String JOKE = "joke";

    private static final String CATEGORIES = "categories";



    private static final String[] COLUMNS = {id, JOKE, CATEGORIES};


    public SQLiteHelper(Context context) {

        super(context, database_NAME, null, database_VERSION);

    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL statement to create book table

        String CREATE_JOKE_TABLE = "CREATE TABLE jokes ( " + "id INTEGER PRIMARY KEY AUTOINCREMENT, " + "joke TEXT, " + "categories TEXT )";

        db.execSQL(CREATE_JOKE_TABLE);

    }



    @Override

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // drop books table if already exists
        db.execSQL("DROP TABLE IF EXISTS jokes");

        this.onCreate(db);
    }


    public Joke readJoke(int id) {
        // get reference of the BookDB database
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(table_JOKE, // a. table
                COLUMNS, " id = ?", new String[] { String.valueOf(id) }, null, null, null, null);

        // if results !=null, parse the first one

        if (cursor != null)
        cursor.moveToFirst();
        Joke joke = new Joke();
        joke.setId(Integer.parseInt(cursor.getString(0)));

        joke.setJoke(cursor.getString(1));
        joke.setCategories(cursor.getString(2));
        return joke;
    }

    public void deleteAllJokes(){
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(table_JOKE, id + " > ?", new String[] { String.valueOf(0) });

        db.close();
    }

    public List getAllJokes() {
        List jokes = new LinkedList();
        String query = "SELECT  * FROM " + table_JOKE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        // parse all results
        Joke joke = null;

        if (cursor.moveToFirst()) {
            do {
                joke = new Joke();
                joke.setId(Integer.parseInt(cursor.getString(0)));
                joke.setJoke(cursor.getString(1));
                joke.setCategories(cursor.getString(2));
                jokes.add(joke);
            } while (cursor.moveToNext());
        }
        return jokes;
    }

    public int updateJoke(Joke joke) {
        SQLiteDatabase db = this.getWritableDatabase();
        // make values to be inserted
        ContentValues values = new ContentValues();
        values.put("joke", joke.getJoke());
        values.put("categories", joke.getCategories());
        // update
        int i = db.update(table_JOKE, values, id + " = ?", new String[] { String.valueOf(joke.getId()) });
        db.close();
        return i;
    }

    public void deleteJoke(Joke joke) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(table_JOKE, id + " = ?", new String[] { String.valueOf(joke.getId()) });

        db.close();

    }

    public void createJoke(Joke joke) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(JOKE, joke.getJoke());
        values.put(CATEGORIES, joke.getCategories());
        db.insert(table_JOKE, null, values);
        db.close();
    }
}
