package com.example.witz;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends Activity {

	private Button nextJokeButton = null;
	private Button addJokeButton = null;
	private TextView jokeView = null;
	SQLiteHelper db = new SQLiteHelper(this);
	private final DatabaseFiller dbFiller = new DatabaseFiller(db);
	private List listJokes = new ArrayList();
	private Random random = new Random();
	private int minForRandom = 0;
	private int maxForRandom = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		nextJokeButton = (Button) findViewById(R.id.nextJoke);
		addJokeButton = (Button) findViewById(R.id.add);
		jokeView = (TextView) findViewById(R.id.jokeView);
		refillDatabase();
		showNewJoke();
		nextJokeButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				showNewJoke();
			}
		});
		addJokeButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				addNewJoke();
			}
		});

	}

	private void refillDatabase(){
		db.deleteAllJokes();
		fillDatabase();
		retrieveJokesFromDatabase();
	}

	private void addNewJoke() {
		Intent i = new Intent(getApplicationContext(), AddActivity.class);
		i.putExtra("db", db);
		startActivity(i);
		refillDatabase();
	}

	private void fillDatabase(){
		try {
			String s= getApplicationContext().getString(R.string.witze);
			System.out.println(s);
			dbFiller.fillDatabase(s, getAssets());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void retrieveJokesFromDatabase(){
		listJokes = db.getAllJokes();
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private void showNewJoke() {
		checkForNewJokes();
		String oldJoke = jokeView.getText().toString();
		maxForRandom = listJokes.size()-1;
		int position = random.nextInt((maxForRandom - minForRandom) + 1) + minForRandom;
		Joke newJoke = (Joke) listJokes.get(position);
		if(!oldJoke.equals(newJoke.getJoke())){
			jokeView.setText(newJoke.getJoke());
		}else{
			showNewJoke();
		}
	}

	private void checkForNewJokes(){
		if(listJokes.size()!= db.getAllJokes().size()){
			retrieveJokesFromDatabase();
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
