package com.fit2081.myfirstproject;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fit2081.myfirstproject.provider.Book;
import com.fit2081.myfirstproject.provider.BookViewModel;

import java.util.List;

public class BookListFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerAdapter recyclerAdapter;
    private BookViewModel bookViewModel;

    public BookListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_book_list, container, false);

        recyclerView = view.findViewById(R.id.listOfBooksRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        recyclerAdapter = new RecyclerAdapter();
        recyclerView.setAdapter(recyclerAdapter);

        bookViewModel = new ViewModelProvider(this).get(BookViewModel.class);
        bookViewModel.getListOfBooks().observe(getViewLifecycleOwner(), new Observer<List<Book>>() {
            @Override
            public void onChanged(List<Book> books) {
                recyclerAdapter.setBooks(books);
            }
        });

        return view;
    }

    // Public method to update the RecyclerView's adapter with the new list of books
    public void updateBookList(List<Book> books) {
        recyclerAdapter.setBooks(books);
    }
}