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

    /**
     * This method is used to get the list of books from the database
     * @return the list of books
     */
    public LiveData<List<Book>> getListOfBooks() {
        return listOfBooks;
    }

    /**
     * This method is used to add a book to the database
     * @param book the book to be added
     */
    public void addBookRepository(Book book) {
        // This is a lambda expression that schedules the execution of the code inside the run() method on a background thread
        BookDatabase.databaseWriteExecutor.execute(() -> {
            bookDao.addBook(book);
        });
    }

    /**
     * This method is used to delete all books from the database
     */
    public void deleteAllBooksRepository() {
        BookDatabase.databaseWriteExecutor.execute(() -> {
            bookDao.deleteAllBooks();
        });
    }
}
