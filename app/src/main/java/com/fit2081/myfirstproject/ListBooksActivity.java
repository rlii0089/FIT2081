package com.fit2081.myfirstproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.fit2081.myfirstproject.provider.Book;
import com.fit2081.myfirstproject.provider.BookViewModel;

import java.util.List;

public class ListBooksActivity extends AppCompatActivity {

    private BookViewModel bookViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_books);

        bookViewModel = new ViewModelProvider(this).get(BookViewModel.class);
        bookViewModel.getListOfBooks().observe(this, new Observer<List<Book>>() {
            @Override
            public void onChanged(List<Book> books) {
                // update the RecyclerView's adapter with the new list of books
                RecyclerView recyclerView = findViewById(R.id.listOfBooksRecyclerView);
                RecyclerAdapter recyclerAdapter = (RecyclerAdapter) recyclerView.getAdapter();
                recyclerAdapter.setBooks(books);
            }
        });
    }
}