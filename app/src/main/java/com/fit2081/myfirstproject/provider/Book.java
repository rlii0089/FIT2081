package com.fit2081.myfirstproject.provider;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * This class is used to define the Book table in the database
 */
@Entity(tableName = "books")
public class Book {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "book_id") // This is the column name in the database
    private int bookId;

    @ColumnInfo(name = "book_title")
    private String bookTitle;

    @ColumnInfo(name = "book_isbn")
    private String bookIsbn;

    @ColumnInfo(name = "book_author")
    private String bookAuthor;

    @ColumnInfo(name = "book_description")
    private String bookDescription;

    @ColumnInfo(name = "book_price")
    private String bookPrice;

    public Book(String bookTitle, String bookIsbn, String bookAuthor, String bookDescription, String bookPrice) {
        this.bookTitle = bookTitle;
        this.bookIsbn = bookIsbn;
        this.bookAuthor = bookAuthor;
        this.bookDescription = bookDescription;
        this.bookPrice = bookPrice;
    }

    public void setBookId(@NonNull int bookId) {
        this.bookId = bookId;
    }

    @NonNull
    public int getBookId() {
        return bookId;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public String getBookIsbn() {
        return bookIsbn;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public String getBookDescription() {
        return bookDescription;
    }

    public String getBookPrice() {
        return bookPrice;
    }
}
