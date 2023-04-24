package com.fit2081.myfirstproject;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.fit2081.myfirstproject.provider.Book;
import com.fit2081.myfirstproject.provider.BookDatabase;
import com.fit2081.myfirstproject.provider.BookRepository;

import java.util.ArrayList;

public class BookListFragment extends Fragment {
    private ArrayList<Item> database;

    private RecyclerView recyclerView;
    private Adapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_list, container, false);

        database = new ArrayList<>();

        recyclerView = view.findViewById(R.id.listOfBooksRecyclerView);
        adapter = new Adapter();
        recyclerView.setAdapter(adapter);

        adapter.setData(database);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        return view;
    }

    public void addBook(Item item) {
        if (database != null) {
            database.add(item);
            adapter.notifyItemInserted(database.size() - 1);
        } else {
            Log.e("BookListFragment", "bookList is null");
        }
    }

    public void clear() {
        database.clear();
        adapter.notifyDataSetChanged();
    }

    public void removeBook(Item item) {
        database.remove(item);
        adapter.notifyDataSetChanged();
    }

    public Item getLastBook() {
        return database.get(database.size() - 1);
    }
}