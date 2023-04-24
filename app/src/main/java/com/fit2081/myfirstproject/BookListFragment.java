package com.fit2081.myfirstproject;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class BookListFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter recyclerAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Item> bookList;

    public BookListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            bookList = new ArrayList<Item>();
            recyclerAdapter = new RecyclerAdapter();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_list, container, false);
        recyclerView = view.findViewById(R.id.listOfBooksRecyclerView);
        layoutManager = new LinearLayoutManager(getActivity()); // Created to provide similar functionality to ListView
        recyclerView.setLayoutManager(layoutManager);

        // Initialise ArrayList and RecyclerAdapter variables and set data to ArrayList and adapter
        recyclerView.setAdapter(recyclerAdapter);
        return view;
    }

    public void addBook(Item item) {
        bookList.add(item);
        recyclerAdapter.notifyDataSetChanged();
    }
}