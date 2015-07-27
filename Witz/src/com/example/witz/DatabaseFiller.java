package com.example.witz;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by Iris on 24.07.2015.
 */
public class DatabaseFiller {

    private SQLiteHelper db;

    public DatabaseFiller(SQLiteHelper db){
        this.db  = db;
    }

    public void fillDatabase(String jokeString,AssetManager assets) throws IOException {
        String jokes = jokeString;//readFile("witze.txt", assets);
        System.out.println("fillDatabase, jokes: " + jokes);
        String [] jokesAsList = jokes.split(";");
        insertJokesIntoDatabase(jokesAsList);
    }

    private void insertJokesIntoDatabase(String[] jokesAsList) {
        for(String joke:jokesAsList){
            System.out.println("insertJokesIntoDatabase, joke: " + joke);
            String[] separatedJokeAndCategories = joke.split(":");
            String actualJoke = separatedJokeAndCategories[0];
            String categories = "";
            if(separatedJokeAndCategories.length>1){
                categories = separatedJokeAndCategories[1];
            }
            System.out.println("insertJokesIntoDatabase, actualJoke: " + actualJoke);
            System.out.println("insertJokesIntoDatabase, categories: " + categories);
            saveJoke(actualJoke, categories);
        }
    }

    private void saveJoke(String actualJoke, String categories){
        Joke funnyJoke = new Joke();
        funnyJoke.setJoke(actualJoke);
        funnyJoke.setCategories(categories);
        db.createJoke(funnyJoke);
        //nein
    }


    private String readFile(String fileName, AssetManager assets) throws IOException {
        /*BufferedReader br = new BufferedReader(new FileReader(fileName));
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append("\n");
                line = br.readLine();
            }
            return sb.toString();
        } finally {
            br.close();
        }*/
        BufferedReader reader;
        String jokes = "";
        try{
            final InputStream file = assets.open(fileName);
            reader = new BufferedReader(new InputStreamReader(file));
            String line = reader.readLine();
            jokes = reader.readLine();
            while(line != null){
                Log.d("StackOverflow", line);
                line = reader.readLine();
            }
        } catch(IOException ioe){
            ioe.printStackTrace();
        }
        return jokes;
    }
}
