package com.fit2081.myfirstproject;

public class Item {
    String bookId;
    String bookTitle;
    String bookIsbn;
    String bookAuthor;
    String bookDescription;
    String bookPrice;

    public Item(String bookId, String bookTitle, String bookIsbn, String bookAuthor, String bookDescription, String bookPrice) {
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.bookIsbn = bookIsbn;
        this.bookAuthor = bookAuthor;
        this.bookDescription = bookDescription;
        this.bookPrice = bookPrice;
    }

    /**
     * This method returns the book id
     * @return the book id
     */
    public String getBookId() {
        return bookId;
    }

    /**
     * This method returns the book title
     * @return the book title
     */
    public String getBookTitle() {
        return bookTitle;
    }

    /**
     * This method returns the book isbn
     * @return the book isbn
     */
    public String getBookIsbn() {
        return bookIsbn;
    }

    /**
     * This method returns the book author
     * @return the book author
     */
    public String getBookAuthor() {
        return bookAuthor;
    }

    /**
     * This method returns the book description
     * @return the book description
     */
    public String getBookDescription() {
        return bookDescription;
    }

    /**
     * This method returns the book price
     * @return the book price
     */
    public String getBookPrice() {
        return bookPrice;
    }
}
