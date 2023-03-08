package edu.northeastern.numad23team8.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.northeastern.numad23team8.R;
import edu.northeastern.numad23team8.models.Books;

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.ViewHolder> {
    private ArrayList<Books> booksList;

    public BooksAdapter(ArrayList<Books> booksList) {
        this.booksList = booksList;
    }

    @NonNull
    @Override
    public BooksAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BooksAdapter.ViewHolder holder, int position) {
        Books currItem = booksList.get(position);
        holder.title.setText(currItem.getTitle());
        holder.author.setText(currItem.getAuthor());
        holder.authorYear.setText(currItem.getAuthorYear());
        holder.language.setText(currItem.getLanguage());
    }

    @Override
    public int getItemCount() {
        return booksList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView title, author, authorYear, language;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.bookName);
            author = itemView.findViewById(R.id.bookAuthor);
            authorYear = itemView.findViewById(R.id.bookAuthorYear);
            language = itemView.findViewById(R.id.bookLang);
        }
    }


}
