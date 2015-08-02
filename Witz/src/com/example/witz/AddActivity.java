package com.example.witz;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;

/**
 * Created by Iris on 02.08.2015.
 */
public class AddActivity extends Activity{

    private MultiSelectionSpinner multiSelectionSpinner;
    private EditText userInput;
    private Button add;
    private SQLiteHelper sqLiteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        Intent i = getIntent();
        //sqLiteHelper =(SQLiteHelper)getIntent().getSerializableExtra("db");
        sqLiteHelper = new SQLiteHelper(this);
        addCategoriesToDatabase();
        userInput = (EditText) findViewById(R.id.input_label);
        add = (Button) findViewById(R.id.btn_add);
        add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                saveNewJoke();
            }
        });
    }

    private void saveNewJoke(){
        String joke = userInput.getText().toString();
        String category = multiSelectionSpinner.getSelectedItemsAsString();
        Joke j = new Joke();
        j.setCategories(category);
        j.setJoke(joke);
        if(j.getCategories()!=""){
            if(j.getJoke() != ""){
            System.out.println("categories: " + category);
            sqLiteHelper.createJoke(j);
                System.out.println("Joke: " + joke);
            finish();
            }else{
                System.out.println("show Dialog");
                showErrorDialog("Du musst erst einen Witz eingeben, bevor du speichern kannst!");
            }
        }else{
            showErrorDialog("Es muss mindestens eine Kategorie ausgewaehlt werden!");
        }
    }

    private void showErrorDialog(String message){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set title
        alertDialogBuilder.setTitle("Obacht!");

        // set dialog message
        alertDialogBuilder
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, close
                        // current activity
                        dialog.cancel();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    private void addCategoriesToDatabase() {
        DatabaseCategoriesHelper dbHelper = new DatabaseCategoriesHelper(getApplicationContext());
        dbHelper.deleteAllCategories();
        String categories= getApplicationContext().getString(R.string.categories);
        String[] categoriesAsArray = categories.split(":");
        for(String c: categoriesAsArray){
            dbHelper.insertLabel(c);
            System.out.println("Category: " + c);
        }
        String[] array = new String[dbHelper.getAllLabels().size()];
        for(int i=0;i<dbHelper.getAllLabels().size();i++){
            array[i] = dbHelper.getAllLabels().get(i);
        }
        multiSelectionSpinner = (MultiSelectionSpinner) findViewById(R.id.categories_spinner);
        multiSelectionSpinner.setItems(array);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
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
