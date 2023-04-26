package com.fit2081.myfirstproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    ArrayList<Item> database; // The ArrayList of Items that will be displayed in the RecyclerView

    /**
     * This method creates a new ViewHolder object whenever the RecyclerView needs a new one.
     * @param parent The ViewGroup into which the new View will be added after it is bound to
     *               an adapter position.
     * @param viewType The view type of the new View.
     *
     * @return A new ViewHolder that holds a View of the given view type.
     */
    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder((ViewGroup) view);
        return viewHolder;
    }

    /**
     * This method binds the data to the view holder
     * @param holder The ViewHolder which should be updated to represent the contents of the
     *        item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bookIdTV.setText(database.get(position).getBookId());
        holder.bookTitleTV.setText(database.get(position).getBookTitle());
        holder.bookIsbnTV.setText(database.get(position).getBookIsbn());
        holder.bookAuthorTV.setText(database.get(position).getBookAuthor());
        holder.bookDescriptionTV.setText(database.get(position).getBookDescription());
        holder.bookPriceTV.setText(database.get(position).getBookPrice());
    }

    /**
     * This method returns the number of items in the database
     * @return the number of items in the database
     */
    @Override
    public int getItemCount() {
        if (database == null) {
            return 0;
        } else {
            return database.size();
        }
    }

    /**
     * This method sets the data for the adapter
     * @param database The ArrayList of Items that will be displayed in the RecyclerView
     */
    public void setData(ArrayList<Item> database) {
        this.database = database;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView bookIdTV
                , bookTitleTV
                , bookIsbnTV
                , bookAuthorTV
                , bookDescriptionTV
                , bookPriceTV;

        /**
         * This method creates a ViewHolder object
         * @param parent The ViewGroup into which the new View will be added after it is bound to
         */
        public ViewHolder(@NonNull ViewGroup parent) {
            super(parent);
            bookIdTV = parent.findViewById(R.id.bookId_card_id);
            bookTitleTV = parent.findViewById(R.id.bookTitle_card_id);
            bookIsbnTV = parent.findViewById(R.id.bookIsbn_card_id);
            bookAuthorTV = parent.findViewById(R.id.bookAuthor_card_id);
            bookDescriptionTV = parent.findViewById(R.id.bookDescription_card_id);
            bookPriceTV = parent.findViewById(R.id.bookPrice_card_id);
        }
    }
}
