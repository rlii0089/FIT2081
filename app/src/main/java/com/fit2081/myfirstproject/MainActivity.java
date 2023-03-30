package com.fit2081.myfirstproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {

    // Declare EditText variables
    EditText bookIdEt
            , bookTitleEt
            , bookIsbnEt
            , bookAuthorEt
            , bookDescriptionEt
            , bookPriceEt

            // Extra task in week 3 lab
            , bookCopiesSoldEt;

    // Keys used for key-value pairs in save/restore instance state methods
    public static final String BOOK_ID_KEY = "BOOK_ID"
            , BOOK_TITLE_KEY = "BOOK_TITLE"
            , BOOK_ISBN_KEY = "BOOK_ISBN"
            , BOOK_AUTHOR_KEY = "BOOK_AUTHOR"
            , BOOK_DESCRIPTION_KEY = "BOOK_DESCRIPTION"
            , BOOK_PRICE_KEY = "BOOK_PRICE"

            // Extra task in week 3 lab
            , BOOK_COPIES_SOLD_KEY = "BOOK_COPIES_SOLD";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialise EditText variables with corresponding element ID
        bookIdEt = findViewById(R.id.bookID_id);
        bookTitleEt = findViewById(R.id.bookTitle_id);
        bookIsbnEt = findViewById(R.id.bookIsbn_id);
        bookAuthorEt = findViewById(R.id.bookAuthor_id);
        bookDescriptionEt = findViewById(R.id.bookDescription_id);
        bookPriceEt = findViewById(R.id.bookPrice_id);

        // Extra task in week 3 lab
        bookCopiesSoldEt = findViewById(R.id.bookCopiesSold_id);

        // Check if savedInstanceState is null
        if(savedInstanceState == null || savedInstanceState.isEmpty()) {
            // Call loadSharedPreferences method to load saved EditText values
            loadSharedPreferences();
        }

        // Request permissions to access SMS
        ActivityCompat.requestPermissions(this
                , new String[] {android.Manifest.permission.SEND_SMS, android.Manifest.permission.RECEIVE_SMS, android.Manifest.permission.READ_SMS}
                , 0);

        // Create and instantiate the local broadcast receiver
        MyBroadcastReceiver myBroadCastReceiver = new MyBroadcastReceiver();

        // Register the broadcast handler with the intent filter that is declared in class SMSReceiver
        registerReceiver(myBroadCastReceiver, new IntentFilter(SMSReceiver.SMS_FILTER));
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save values from EditTexts to key-value pairs
        outState.putString(BOOK_TITLE_KEY, bookTitleEt.getText().toString());
        outState.putInt(BOOK_ISBN_KEY, Integer.parseInt(bookIsbnEt.getText().toString()));

        // Extra task in week 3 lab
        outState.putInt(BOOK_COPIES_SOLD_KEY, Integer.parseInt(bookCopiesSoldEt.getText().toString()));
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        // Super keyword not called as we only want to restore selected states
        // Retrieve the values from their key-value pairs and set texts to the EditText variables using the values retrieved
        bookTitleEt.setText(savedInstanceState.getString(BOOK_TITLE_KEY));
        bookIsbnEt.setText(String.valueOf(savedInstanceState.getInt(BOOK_ISBN_KEY)));

        // Extra task in week 3 lab
        bookCopiesSoldEt.setText(String.valueOf(savedInstanceState.getInt(BOOK_COPIES_SOLD_KEY)));
    }

    private void saveSharedPreferences() {
        // Create SharedPreferences object to store key-value pairs in a file
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);

        // Retrieve editor instance to modify shared preference data
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Save key with EditText value to form key-value pair
        editor.putString(BOOK_ID_KEY, bookIdEt.getText().toString());
        editor.putString(BOOK_TITLE_KEY, bookTitleEt.getText().toString());
        editor.putString(BOOK_ISBN_KEY, bookIsbnEt.getText().toString());
        editor.putString(BOOK_AUTHOR_KEY, bookAuthorEt.getText().toString());
        editor.putString(BOOK_DESCRIPTION_KEY, bookDescriptionEt.getText().toString());
        editor.putString(BOOK_PRICE_KEY, bookPriceEt.getText().toString());

        // Extra task in week 3 lab
        editor.putString(BOOK_COPIES_SOLD_KEY, bookCopiesSoldEt.getText().toString());

        // Save the changes made to the SharedPreferences object
        editor.apply(); // Is asynchronous so it is preferred over commit(), as commit() can cause Application Not Responding (ANR) error
    }

    private void loadSharedPreferences() {
        // Create SharedPreferences object to store key-value pairs in a file
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);

        // Save values stored in key-value pairs and assign to temporary variables
        String bookId = sharedPreferences.getString(BOOK_ID_KEY,"");
        String bookTitle = sharedPreferences.getString(BOOK_TITLE_KEY,"");
        String bookIsbn = sharedPreferences.getString(BOOK_ISBN_KEY,"");
        String bookAuthor = sharedPreferences.getString(BOOK_AUTHOR_KEY,"");
        String bookDescription = sharedPreferences.getString(BOOK_DESCRIPTION_KEY,"");
        String bookPrice = sharedPreferences.getString(BOOK_PRICE_KEY,"");

        // Extra task in week 3 lab
        String bookCopiesSold = sharedPreferences.getString(BOOK_COPIES_SOLD_KEY,"");

        // Set EditText with value in temporary values
        bookIdEt.setText(bookId);
        bookTitleEt.setText(bookTitle);
        bookIsbnEt.setText(bookIsbn);
        bookAuthorEt.setText(bookAuthor);
        bookDescriptionEt.setText(bookDescription);
        bookPriceEt.setText(bookPrice);

        // Extra task in week 3 lab
        bookCopiesSoldEt.setText(bookCopiesSold);
    }

    public void onAddBookButtonClick(View view){
        // Save values as variables
        String theBookTitle = bookTitleEt.getText().toString();
        String theBookPrice = bookPriceEt.getText().toString(); // Treating as String as no arithmetic operations are performed

        // call saveSharedPreferences method to save current EditText values
        saveSharedPreferences();

        // String variable displayed in the toast using above variables
        String toastMessage = "Added: " + theBookTitle  + " ($" + theBookPrice + ")";

        // Create toast with defined variables from above
        Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show();
    }

    public void onClearButtonClick(View view){
        // Clear fields, alternate way would be to use setText
        // e.g. BookTitleEt.setText("");
        bookIdEt.getText().clear();
        bookTitleEt.getText().clear();
        bookIsbnEt.getText().clear();
        bookAuthorEt.getText().clear();
        bookDescriptionEt.getText().clear();
        bookPriceEt.getText().clear();
        bookCopiesSoldEt.getText().clear();
    }

    public void onLoadLastBookButtonClick(View view) {
        // call loadSharedPreferences method to load saved EditText values
        loadSharedPreferences();
    }

    // Extra task in week 2 lab
    public void onDoublePriceButtonClick(View view){
        // Assign current price in EditText to a variable whilst converting it into an integer
        int currentBookPriceInteger = Integer.parseInt(bookPriceEt.getText().toString());

        // Double the price in the variable and reassign it
        currentBookPriceInteger *= 2;

        // Convert integer to a string and save in a variable
        String currentBookPriceString = Integer.toString(currentBookPriceInteger);

        // Set the text in the EditText as the variable value above, no need to clear as it replaces the text
        bookPriceEt.setText(currentBookPriceString);
    }

    class MyBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive (Context context, Intent intent) {
            // Retrieve the message from the intent
            String msg = intent.getStringExtra(SMSReceiver.SMS_MESSAGE_KEY);

            // Tokenize the message, delimited by "|"
            StringTokenizer stringTokenizer = new StringTokenizer(msg, "|");

            // Retrieve the tokens and assign to variables
            String bookId = stringTokenizer.nextToken();
            String bookTitle = stringTokenizer.nextToken();
            String bookIsbn = stringTokenizer.nextToken();
            String bookAuthor = stringTokenizer.nextToken();
            String bookDescription = stringTokenizer.nextToken();
            String bookPrice = stringTokenizer.nextToken();

            // Extra task in week 4 lab
            boolean bookConfirmed = Boolean.parseBoolean(stringTokenizer.nextToken());

            // Declare and initialize bookPrice as integer
            int bookPriceInt = Integer.parseInt(bookPrice);

            // If book is confirmed, bookPrice add 100, else add 5
            if (bookConfirmed) {
                bookPriceInt += 100;
            } else {
                bookPriceInt += 5;
            }

            // Update book price with new value
            bookPrice = Integer.toString(bookPriceInt);

            // Set EditText with value in temporary values
            bookIdEt.setText(bookId);
            bookTitleEt.setText(bookTitle);
            bookIsbnEt.setText(bookIsbn);
            bookAuthorEt.setText(bookAuthor);
            bookDescriptionEt.setText(bookDescription);
            bookPriceEt.setText(bookPrice);
        }
    }
}