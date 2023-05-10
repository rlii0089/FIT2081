package com.fit2081.myfirstproject;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.ArrayList;

public class BookListFragment extends Fragment {
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private Adapter adapter;
    private ArrayList<Item> database;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_list, container, false);

        recyclerView = view.findViewById(R.id.listOfBooksRecyclerView);
        layoutManager = new LinearLayoutManager(getContext()); // Created to provide similar functionality to ListView
        recyclerView.setLayoutManager(layoutManager);

        database = new ArrayList<>();
        adapter = new Adapter();
        recyclerView.setAdapter(adapter);

        return view;
    }
}