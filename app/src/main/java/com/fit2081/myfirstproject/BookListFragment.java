package com.fit2081.myfirstproject;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class BookListFragment extends Fragment {

    private RecyclerView recyclerView; // RecyclerView to display books

    private RecyclerAdapter recyclerAdapter; // Adapter to display items in RecyclerView


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

        return view;
    }

}