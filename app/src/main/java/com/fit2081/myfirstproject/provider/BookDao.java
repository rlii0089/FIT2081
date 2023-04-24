package com.fit2081.myfirstproject.provider;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface BookDao {

    @Query("SELECT * FROM books")
    public LiveData<List<Book>> getAllBooks();

    @Insert
    public void addBook(Book book);
}
