package com.fit2081.myfirstproject.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

/**
 * This class is used to provide access to the Book database
 */
public class BookContentProvider extends ContentProvider {
    BookDatabase bookDatabase; // This is the database that will be used to access the data

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public boolean onCreate() {
        bookDatabase = BookDatabase.getDatabase(getContext()); // Get the database instance
        return true;
    }

    /**
     * This method is used to query the database
     * @param uri The URI to query
     * @param projection The columns to return
     * @param selection The columns for the WHERE clause
     * @param selectionArgs The values for the WHERE clause
     * @param sortOrder How the rows in the cursor should be sorted
     * @return A cursor containing the results of the query
     */
    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        // Use the SQLiteQueryBuilder to build the query
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables("Books");

        // Get the query string
        String query = builder.buildQuery(projection, selection, null, null, sortOrder, null);

        // Use the query() method from the BookDatabase to access the database
        Cursor cursor = bookDatabase.getOpenHelper()
                .getReadableDatabase()
                .query(query);

        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}