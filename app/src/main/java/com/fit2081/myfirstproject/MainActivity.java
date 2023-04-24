package com.fit2081.myfirstproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.fit2081.myfirstproject.provider.Book;
import com.fit2081.myfirstproject.provider.BookViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {
    ArrayList<Item> database; // ArrayList to store books
    RecyclerView recyclerView; // RecyclerView to display books
    RecyclerView.LayoutManager layoutManager; // Layout manager to manage items in RecyclerView
    Adapter adapter; // Adapter to display items in RecyclerView
    DrawerLayout drawerLayout; // Drawer layout to display navigation drawer
    NavigationView navigationView; // Navigation view to display navigation drawer
    Toolbar toolbar; // Toolbar to display app bar
    private BookViewModel bookViewModel; // View model to access database

    EditText bookIdEt
            , bookTitleEt
            , bookIsbnEt
            , bookAuthorEt
            , bookDescriptionEt
            , bookPriceEt
            , bookCopiesSoldEt; // Extra task in week 3 lab

    // Keys used for key-value pairs in save/restore instance state methods
    public static final String BOOK_ID_KEY = "BOOK_ID"
            , BOOK_TITLE_KEY = "BOOK_TITLE"
            , BOOK_ISBN_KEY = "BOOK_ISBN"
            , BOOK_AUTHOR_KEY = "BOOK_AUTHOR"
            , BOOK_DESCRIPTION_KEY = "BOOK_DESCRIPTION"
            , BOOK_PRICE_KEY = "BOOK_PRICE"
            , BOOK_COPIES_SOLD_KEY = "BOOK_COPIES_SOLD"; // Extra task in week 3 lab

    /**
     * Method to handle create activity
     * @param savedInstanceState The saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_layout); // Set content view to drawer layout as it contains the navigation drawer and list view

        Week2OnCreate();
        Week3OnCreate(savedInstanceState);
        Week4OnCreate();
        Week5OnCreate();
        Week6OnCreate();

        bookViewModel = new ViewModelProvider(this).get(BookViewModel.class); // Initialise ViewModelProvider that will be used to access database across multiple fragments

        // Observe changes in the list of books in the database
        bookViewModel.getListOfBooks().observe(this, (books -> {
            Toast.makeText(getApplicationContext(), books.size() + " books", Toast.LENGTH_SHORT).show();
        }));
    }

    /**
     * Method to handle options menu creation
     * @param menu The menu to inflate
     * @return true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }

    /**
     * Method to handle options menu item selected
     * @param item The menu item selected
     * @return true
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Get the ID of the menu item selected
        int id = item.getItemId();
        if (id == R.id.options_menu_clear_all_items) {

            // Clear all items in the list view
            database.clear();
            adapter.notifyDataSetChanged();
        } else if (id == R.id.options_menu_load_shared_preferences_saved_values) {

            // Call loadSharedPreferences method to load saved EditText values
            loadSharedPreferences();
        }
        return true;
    }

    /**
     * Method to handle state data across activity instances in the same user session
     * @param outState The bundle to save the state to
     */
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save values from EditTexts to key-value pairs
        outState.putString(BOOK_TITLE_KEY, bookTitleEt.getText().toString());
        outState.putInt(BOOK_ISBN_KEY, Integer.parseInt(bookIsbnEt.getText().toString()));

        // Extra task in week 3 lab
        outState.putInt(BOOK_COPIES_SOLD_KEY, Integer.parseInt(bookCopiesSoldEt.getText().toString()));
    }

    /**
     * Method to handle restoring state data across activity instances in the same user session
     * @param savedInstanceState The bundle to restore the state from
     */
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        // Super keyword not called as we only want to restore selected states
        // Retrieve the values from their key-value pairs and set texts to the EditText variables using the values retrieved
        bookTitleEt.setText(savedInstanceState.getString(BOOK_TITLE_KEY));
        bookIsbnEt.setText(String.valueOf(savedInstanceState.getInt(BOOK_ISBN_KEY)));

        // Extra task in week 3 lab
        bookCopiesSoldEt.setText(String.valueOf(savedInstanceState.getInt(BOOK_COPIES_SOLD_KEY)));
    }

    /**
     * Method to handle saving data across user sessions
     */
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

    /**
     * Method to handle loading data across user sessions
     */
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

    /**
     * Method to handle add book button click
     * @param view The view that was clicked
     */
    public void onAddBookButtonClick(View view){ // Button has been replaced by floating action button
        // Save values as variables and create new item
        String theBookId = bookIdEt.getText().toString();
        String theBookTitle = bookTitleEt.getText().toString();
        String theBookIsbn = bookIsbnEt.getText().toString();
        String theBookAuthor = bookAuthorEt.getText().toString();
        String theBookDescription = bookDescriptionEt.getText().toString();
        String theBookPrice = bookPriceEt.getText().toString(); // Treating as String as no arithmetic operations are performed
        Item item = new Item(theBookId, theBookTitle, theBookIsbn, theBookAuthor, theBookDescription, theBookPrice);

        // Add item to database and notify adapter of data change
        database.add(item);
        adapter.notifyDataSetChanged();

        Book book = new Book(theBookTitle, theBookIsbn, theBookAuthor, theBookDescription, theBookPrice);
        bookViewModel.addBookViewModel(book); // Add book to database using ViewModelProvider

        saveSharedPreferences(); // call saveSharedPreferences method to save current EditText values
        String toastMessage = "Added: " + theBookTitle  + " ($" + theBookPrice + ")"; // String variable displayed in the toast using above variables
        Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show(); // Create toast with defined variables from above
    }

    /**
     * Method to handle clear button click
     * @param view The view that was clicked
     */
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

    /**
     * Method to handle load last book button click
     * @param view The view that was clicked
     */
    public void onLoadLastBookButtonClick(View view) {
        // call loadSharedPreferences method to load saved EditText values
        loadSharedPreferences();
    }

    // Extra task in week 2 lab
    /**
     * Method to handle double price button click
     * @param view The view that was clicked
     */
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
        /**
         * Method to handle broadcast receiver
         * @param context The context in which the receiver is running
         * @param intent The intent being received
         */
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

    class NavigationHandler implements NavigationView.OnNavigationItemSelectedListener {
        /**
         * Method to handle navigation item selection
         * @param item The selected item
         * @return True if the item was selected
         */
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            int id = item.getItemId();
            if (id == R.id.navigation_menu_add_book) { // If the item selected is the add book item, call onAddBookButtonClick method and notify adapter of data change
                onAddBookButtonClick(null);
                adapter.notifyDataSetChanged();
            } else if (id == R.id.navigation_menu_remove_last_book) { // If the item selected is the remove last book item, remove the last item in the database and notify adapter of data change
                database.remove(database.size() - 1);
                adapter.notifyDataSetChanged();
            } else if (id == R.id.navigation_menu_remove_all_books) { // If the item selected is the remove all books item, clear the database and notify adapter of data change
                database.clear();
                adapter.notifyDataSetChanged();
            }
            drawerLayout.closeDrawers(); // Close the drawer
            return true;
        }
    }

    public void Week2OnCreate() {
        // Initialise EditText variables with corresponding element ID
        bookIdEt = findViewById(R.id.bookID_id);
        bookTitleEt = findViewById(R.id.bookTitle_id);
        bookIsbnEt = findViewById(R.id.bookIsbn_id);
        bookAuthorEt = findViewById(R.id.bookAuthor_id);
        bookDescriptionEt = findViewById(R.id.bookDescription_id);
        bookPriceEt = findViewById(R.id.bookPrice_id);
        bookCopiesSoldEt = findViewById(R.id.bookCopiesSold_id); // Extra task in week 3 lab
    }

    public void Week3OnCreate(Bundle savedInstanceState) {
        // Check if savedInstanceState is null
        if(savedInstanceState == null || savedInstanceState.isEmpty()) {
            loadSharedPreferences(); // Call loadSharedPreferences method to load saved EditText values
        }
    }

    public void Week4OnCreate() {
        // Request permissions to access SMS
        ActivityCompat.requestPermissions(this
                , new String[] {android.Manifest.permission.SEND_SMS
                        , android.Manifest.permission.RECEIVE_SMS
                        , android.Manifest.permission.READ_SMS}
                , 0);

        MyBroadcastReceiver myBroadCastReceiver = new MyBroadcastReceiver(); // Create and instantiate the local broadcast receiver
        registerReceiver(myBroadCastReceiver, new IntentFilter(SMSReceiver.SMS_FILTER)); // Register the broadcast handler with the intent filter that is declared in class SMSReceiver
    }

    public void Week5OnCreate() {
        // Initialise DrawerLayout, NavigationView and Toolbar variables with corresponding element ID
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar); // Set toolbar as action bar (Using setSupportActionbar() method to support older versions of Android)

        // Initialise ActionBarDrawerToggle with drawer layout, toolbar, open and close navigation drawer strings
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this
                , drawerLayout
                , toolbar
                , R.string.open_navigation_drawer
                , R.string.close_navigation_drawer);

        // Add drawer listener to drawer layout and sync state
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        navigationView.setNavigationItemSelectedListener(new NavigationHandler());// Set navigation view item selected listener to the custom class NavigationHandler

        // Initialise FloatingActionButton variable with corresponding element ID
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            // Set on click listener to add book button
            @Override
            public void onClick(View view) {
                onAddBookButtonClick(view); // Call onAddBookButtonClick method
            }
        });
    }

    public void Week6OnCreate() {
        // Initialise RecyclerView variable with corresponding element ID and set layout manager
        recyclerView = findViewById(R.id.listOfBooksRecyclerView);
        layoutManager = new LinearLayoutManager(this); // Created to provide similar functionality to ListView
        recyclerView.setLayoutManager(layoutManager);

        // Initialise ArrayList and Adapter variables and set data to ArrayList and adapter
        database = new ArrayList<>();
        adapter = new Adapter();
        adapter.setData(database);
        recyclerView.setAdapter(adapter);
    }
}