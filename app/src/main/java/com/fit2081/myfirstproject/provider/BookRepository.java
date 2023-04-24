package com.fit2081.myfirstproject.provider;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class BookRepository {

    private BookDao bookDao;
    private LiveData<List<Book>> listOfBooks;

    public BookRepository(Application application) {
        BookDatabase bookDatabase = BookDatabase.getDatabase(application);

        bookDao = bookDatabase.bookDao();
        listOfBooks = bookDao.getAllBooks();
    }

    public LiveData<List<Book>> getListOfBooks() {
        return listOfBooks;
    }

    public void addBookRepository(Book book) {
        // This is a lambda expression that schedules the execution of the code inside the run() method on a background thread
        BookDatabase.databaseWriteExecutor.execute(() -> {
            bookDao.addBook(book);
        });
    }
}
