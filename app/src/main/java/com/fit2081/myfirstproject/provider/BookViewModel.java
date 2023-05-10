package com.fit2081.myfirstproject.provider;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

/**
 * This class is used to store and manage UI-related data in a lifecycle conscious way.
 */
public class BookViewModel extends AndroidViewModel {

    private BookRepository bookRepository; // This is the repository that will be used to access the database
    LiveData<List<Book>> listOfBooks;

    public BookViewModel(@NonNull Application application) {
        super(application);

        bookRepository = new BookRepository(application);
        listOfBooks = bookRepository.getListOfBooks();
    }

    /**
     * This method is used to get the list of books from the repository
     * @return the list of books
     */
    public LiveData<List<Book>> getListOfBooks() {
        return listOfBooks;
    }

    /**
     * This method is used to add a book to the database
     * @param book the book to be added
     */
    public void addBookViewModel(Book book) {
        bookRepository.addBookRepository(book);
    }

    /**
     * This method is used to delete all books from the database
     */
    public void deleteAllBooksViewModel() {
        bookRepository.deleteAllBooksRepository();
    }
}
