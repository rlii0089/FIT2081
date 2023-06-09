package com.fit2081.myfirstproject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fit2081.myfirstproject.provider.Book;
import com.fit2081.myfirstproject.provider.BookViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {
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
    ArrayList<Item> database; // ArrayList to store books
    RecyclerView recyclerView; // RecyclerView to display books
    RecyclerView.LayoutManager layoutManager; // Layout manager to manage items in RecyclerView
    Adapter adapter; // Adapter to display items in RecyclerView
    DrawerLayout drawerLayout; // Drawer layout to display navigation drawer
    NavigationView navigationView; // Navigation view to display navigation drawer
    Toolbar toolbar; // Toolbar to display app bar
    private BookViewModel bookViewModel; // View model to access database
//    DatabaseReference cloudDatabase; // Firebase database reference
//    DatabaseReference bookBranch; // Firebase database reference
    View gestureView; // View to detect gestures
    int startingX, startingY, movedX, movedY, endingX, endingY; // Variables to store coordinates of starting, moved and endpoints of x and y coordinates
    GestureDetector gestureDetector; // Gesture detector to detect gestures

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
        Week7OnCreate();
        Week8OnCreate();
//        Week9OnCreate();
        Week10OnCreate();
    }

    /**
     * Method to handle add book button click
     * @param view The view that was clicked
     */
    public void onAddBookButtonClick(View view) { // Button has been replaced by floating action button
        // Save values as variables and create new item
        String theBookId = bookIdEt.getText().toString();
        String theBookTitle = bookTitleEt.getText().toString();
        String theBookIsbn = bookIsbnEt.getText().toString();
        String theBookAuthor = bookAuthorEt.getText().toString();
        String theBookDescription = bookDescriptionEt.getText().toString();
        String theBookPrice = bookPriceEt.getText().toString(); // Treating as String as no arithmetic operations are performed
        Item item = new Item(theBookId, theBookTitle, theBookIsbn, theBookAuthor, theBookDescription, theBookPrice);

        // Add item to database and notify adapter of change to display new item
        database.add(item);
        adapter.notifyDataSetChanged();

        // Add book to database using ViewModelProvider
        Book book = new Book(theBookTitle, theBookIsbn, theBookAuthor, theBookDescription, theBookPrice);
        bookViewModel.addBookViewModel(book);

//        bookBranch.push().setValue(book); // Add book to Firebase database

        saveSharedPreferences(); // call saveSharedPreferences method to save current EditText values

        String toastMessage = "Added: " + theBookTitle + " ($" + theBookPrice + ")"; // String variable displayed in the toast using above variables
        Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show(); // Create toast with defined variables from above
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

                bookViewModel.deleteAllBooksViewModel(); // Extra task in week 6 lab
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

    public void Week7OnCreate() {
        // Initialise ViewModelProvider that will be used to access database across multiple fragments
        bookViewModel = new ViewModelProvider(this).get(BookViewModel.class);

        // Observe changes in the list of books in the database
        bookViewModel.getListOfBooks().observe(this, (books -> {
            Toast.makeText(getApplicationContext(), books.size() + " books", Toast.LENGTH_SHORT).show();
        }));
    }

    public void Week8OnCreate() {
//        FirebaseApp.initializeApp(this); // Initialise Firebase app
//        cloudDatabase = FirebaseDatabase.getInstance().getReference(); // Initialise cloud database reference
//        bookBranch = cloudDatabase.child("books"); // Initialise book branch reference

        // Content Resolver file: BookLib2 Content Resolver
        // Firebase made using personal email
    }

    public void Week9OnCreate() {
        gestureView = findViewById(R.id.gestureView); // Initialise GestureOverlayView variable with corresponding element ID
        gestureView.setOnTouchListener(new View.OnTouchListener() {
            // Set on touch listener to gesture view
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getActionMasked(); // Get action from MotionEvent
                int moveDistance = 75;

                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        // Initialise starting coordinates
                        startingX = (int) event.getX();
                        startingY = (int) event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        movedX = (int) event.getX();
                        movedY = (int) event.getY();

                        if (movedX > startingX && movedX - startingX > moveDistance && startingY == movedY) { // If gesture is right, increment book price by 1
                            int currentInputtedPrice = Integer.parseInt(bookPriceEt.getText().toString());
                            bookPriceEt.setText(String.valueOf(currentInputtedPrice + 1));
                        } else if (
                                ((movedX > startingX && movedX - startingX > moveDistance) && (movedY > startingY + moveDistance && movedY - startingY > moveDistance))
                        ) {
                            database.clear();
                            adapter.notifyDataSetChanged();
                            bookViewModel.deleteAllBooksViewModel();
                        }

                    case MotionEvent.ACTION_UP: // If action is up, get ending coordinates and check if gesture is left, right or up
                        // Initialise ending coordinates
                        endingX = (int) event.getX();
                        endingY = (int) event.getY();

                        if (startingX > endingX && startingY == endingY) { // If gesture is left, call onClearButtonClick method
                            onAddBookButtonClick(null);
                        } else if (startingY > endingY && startingX == endingX) { // If gesture is up, call onClearButtonClick method
                            onClearButtonClick(null);
                        }
                        break;
                }
                return true; // Return true to indicate that the listener has consumed the event
            }
        });

    }

    public void Week10OnCreate() {
        gestureView = findViewById(R.id.gestureView); // Initialise GestureOverlayView variable with corresponding element ID
        gestureDetector = new GestureDetector(this, new GestureHandler()); // Initialise GestureDetector variable with custom class GestureHandler
        gestureView.setOnTouchListener(new View.OnTouchListener() {
            // Set on touch listener to gesture view
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetector.onTouchEvent(event); // Pass event to gesture detector
                return true; // Return true to indicate that the listener has consumed the event
            }
        });
    }

    private class GestureHandler extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDown(@NonNull MotionEvent motionEvent) {
            return true;
        }

        @Override
        public boolean onSingleTapConfirmed(@NonNull MotionEvent e) {
            bookIsbnEt.setText(RandomString.generateNewRandomString(6)); // Set book ISBN to random string
            return super.onSingleTapConfirmed(e);
        }

        @Override
        public boolean onDoubleTap(@NonNull MotionEvent e) {
            onClearButtonClick(null);
            return super.onDoubleTap(e);
        }

        @Override
        public boolean onScroll(@NonNull MotionEvent e1, @NonNull MotionEvent e2, float distanceX, float distanceY) { // If scrolling from right to left, increment the price by the amount of distance scrolled, else if the scrolling is left to right, decrement the price by the amount of distance scrolled
            if (e1.getX() > e2.getX()) { // If scrolling from right to left, increment the price by the amount of distance scrolled
                int currentInputtedPrice = Integer.parseInt(bookPriceEt.getText().toString());
                bookPriceEt.setText(String.valueOf(currentInputtedPrice + (int) distanceX));
            } else if (e1.getX() < e2.getX()) { // If scrolling from left to right, decrement the price by the amount of distance scrolled
                int currentInputtedPrice = Integer.parseInt(bookPriceEt.getText().toString());
                bookPriceEt.setText(String.valueOf(currentInputtedPrice + (int) distanceX)); // Adding the distance because X is negative
            }
            return super.onScroll(e1, e2, distanceX, distanceY);
        }

        @Override
        public boolean onFling(@NonNull MotionEvent e1, @NonNull MotionEvent e2, float velocityX, float velocityY) {
            if (velocityX > 1000) {
                moveTaskToBack(true);
            }
            return super.onFling(e1, e2, velocityX, velocityY);
        }

        @Override
        public void onLongPress(@NonNull MotionEvent e) {
            onLoadLastBookButtonClick(null);
            super.onLongPress(e);
        }
    }
}