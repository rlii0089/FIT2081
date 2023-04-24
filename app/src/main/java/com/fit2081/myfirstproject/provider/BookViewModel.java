package com.fit2081.myfirstproject.provider;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class BookViewModel extends AndroidViewModel {

    private BookRepository bookRepository;
    LiveData<List<Book>> listOfBooks;

    public BookViewModel(@NonNull Application application) {
        super(application);

        bookRepository = new BookRepository(application);
        listOfBooks = bookRepository.getListOfBooks();
    }

    public LiveData<List<Book>> getListOfBooks() {
        return listOfBooks;
    }

    public void addBookViewModel(Book book) {
        bookRepository.addBookRepository(book);
    }
}
