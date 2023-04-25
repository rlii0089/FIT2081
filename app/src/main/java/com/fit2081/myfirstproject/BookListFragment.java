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
    private ArrayList<Item> database;
    private RecyclerView recyclerView;
    private Adapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_list, container, false);

        recyclerView = view.findViewById(R.id.listOfBooksRecyclerView);
        adapter = new Adapter();
        recyclerView.setAdapter(adapter);

        database = new ArrayList<>();
        adapter.setData(database);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        return view;
    }
}