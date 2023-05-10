package com.fit2081.myfirstproject.provider;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

/**
 * This interface is used to define the database operations that can be performed on the Book table
 */
@Dao
public interface BookDao {

    @Query("SELECT * FROM books")
    public LiveData<List<Book>> getAllBooks(); // LiveData is a wrapper class that can be used to observe changes in the data

    @Insert
    public void addBook(Book book);

    @Query("DELETE FROM books")
    public void deleteAllBooks();
}
