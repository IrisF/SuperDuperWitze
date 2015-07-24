package com.example.witz;

import android.app.Activity;
import android.content.ContentValues;
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

	/*private final Button nextJokeButton = (Button) findViewById(R.id.nextJoke);
	private final Button addJokeButton = (Button) findViewById(R.id.add);
	private final TextView jokeView = (TextView) findViewById(R.id.jokeView);*/
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
		fillDatabase();
		//retrieveJokesFromDatabase();
		//showNewJoke();
		//nextJokeButton.setOnClickListener(new View.OnClickListener() {
		/*	public void onClick(View v) {
				showNewJoke();
			}
		});*/

	}

	private void fillDatabase(){
		try {
			dbFiller.fillDatabase(getAssets());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void retrieveJokesFromDatabase(){
		//listJokes = db.getAllJokes();
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

/*	private void showNewJoke() {
		String oldJoke = jokeView.getText().toString();
		maxForRandom = listJokes.size();
		int position = random.nextInt((maxForRandom - minForRandom) + 1) + minForRandom;
		String newJoke = listJokes.get(position).toString();
		if(!oldJoke.equals(newJoke)){
			jokeView.setText(newJoke);
		}else{
			showNewJoke();
		}
	}*/

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
