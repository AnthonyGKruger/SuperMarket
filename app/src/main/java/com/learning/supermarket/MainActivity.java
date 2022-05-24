package com.learning.supermarket;


import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    //declaring variables to be used throughout the program
    DBHelper myDBHelper;
    String storeName, storeAddress;
    double produceRating, cheeseRating, LiquorRating, easeOfCheckoutRating, average;
    EditText editTextStoreName, editTextStoreAddress;
    Button buttonSave, buttonRate, buttonDoRating, buttonReturn;
    RatingBar produceRatingBar, cheeseRatingBar, LiquorRatingBar, easeOfCheckoutRatingBar;
    TextView averageRatingOutput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mapMainActivityWidgets();

    }

    //helper function that makes sure store information was entered correctly and that it is in
    // lowercase so that all text in the db is uniform. returns true if details were satisfactory
    public boolean getStoreDetails(EditText editTextStoreName, EditText editTextStoreAddress){

        if (!editTextStoreName.getText().toString().equals("") &&
                !editTextStoreAddress.getText().toString().equals("") ){

            storeName = editTextStoreName.getText().toString().toLowerCase();
            storeAddress = editTextStoreAddress.getText().toString().toLowerCase();
            return true;
        }
        return false;
    }

    public void mapMainActivityWidgets(){

        //mapping variables to widgets in main activity
        editTextStoreName = findViewById(R.id.editTextStoreName);
        editTextStoreAddress = findViewById(R.id.editTextStoreAddress);
        buttonRate = findViewById(R.id.buttonRate);
        buttonSave = findViewById(R.id.buttonSaveStoreDetails);

        //initializing dbhelper variable
        myDBHelper = new DBHelper(this, null,null, 1);

    }

    //setting an onclick method for the do rating button. this will save the ratings
    //to the db
    public void doRating(View view){

            //storing the ratings in variables
            produceRating = produceRatingBar.getRating();
            cheeseRating = cheeseRatingBar.getRating();
            LiquorRating = LiquorRatingBar.getRating();
            easeOfCheckoutRating = easeOfCheckoutRatingBar.getRating();

            //using the variables to calulate the average of the ratings
            average = (produceRating + cheeseRating + LiquorRating
                    + easeOfCheckoutRating) / 4;

            //outputting the average onto the relevant textview.
            averageRatingOutput.setText(String.format("%s", average));

            //using our db handler to update the database.
            myDBHelper.updateHandler(storeName, storeAddress, LiquorRating,
                    produceRating, cheeseRating, easeOfCheckoutRating, average);

            //outputting a message to let the user know it was a success
            Toast.makeText(getApplicationContext(),
                    "Your ratings have been saved to the database",
                    Toast.LENGTH_LONG).show();
        }

    //setting onclick listener for the return button which will take the user back to
    //the main activity layout.
    public void changeLayoutToRatingLayout(View view){

        //checking if store details were entered correctly.
        if (getStoreDetails(editTextStoreName, editTextStoreAddress)){

            //changing the content view to the rating layout.
            setContentView(R.layout.rating_layout);

            mapRatingWidgets();



            //store details werent set so the user needs to let us know which store to rate.
        } else {
            Toast.makeText(getApplicationContext(),
                    "Please enter valid store information",
                    Toast.LENGTH_LONG).show();
        }
    }


    public void returnToMainActivity(View view){

        setContentView(R.layout.activity_main);

    }

    public void mapRatingWidgets(){

        //mapping variables to the widgets in the rating layout.
        averageRatingOutput = findViewById(R.id.TextViewTheAverage);
        produceRatingBar = findViewById(R.id.ratingBarProduceDep);
        cheeseRatingBar = findViewById(R.id.ratingBarCheeseDep);
        LiquorRatingBar = findViewById(R.id.ratingBarLiquorDep);
        easeOfCheckoutRatingBar = findViewById(R.id.ratingBarEaseOfCheckout);
        buttonDoRating = findViewById(R.id.buttonDoRating);
        buttonReturn = findViewById(R.id.buttonReturn);


    }
}
