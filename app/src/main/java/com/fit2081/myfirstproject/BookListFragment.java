package com.fit2081.myfirstproject;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.ArrayList;

public class BookListFragment extends Fragment {
    private ArrayList<Item> database = new ArrayList<>();
    private RecyclerView recyclerView;
    private Adapter adapter = new Adapter();
    private LinearLayoutManager layoutManager;

    public BookListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_list, container, false);

        // Initialise RecyclerView variable with corresponding element ID and set layout manager

        recyclerView = view.findViewById(R.id.listOfBooksRecyclerView);
        layoutManager = new LinearLayoutManager(getContext()); // Created to provide similar functionality to ListView
        recyclerView.setLayoutManager(layoutManager);

        // Initialise ArrayList and Adapter variables and set data to ArrayList and adapter
        adapter.setData(database);
        recyclerView.setAdapter(adapter);

        return view;
    }

    public void addItem(Item item) {
        // Add item to ArrayList and notify adapter of change
        database.add(item);
        adapter.notifyDataSetChanged();
    }
}