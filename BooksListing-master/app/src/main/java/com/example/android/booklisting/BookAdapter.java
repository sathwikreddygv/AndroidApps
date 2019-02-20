package com.example.android.booklisting;

import android.content.Context;
import android.graphics.Bitmap;
import android.opengl.Visibility;
import android.util.Log;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


/**
 * Created by alicantipi on 09.12.17.
 */

public class BookAdapter extends ArrayAdapter<Book> {

    private ProgressBar progressBar;
    public BookAdapter(Context context, ArrayList<Book> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;


        // inflate a view if its empty
        if (convertView == null) {
            holder = new ViewHolder();

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
            holder.progressBar =convertView.findViewById(R.id.loading_indicator_list);
            holder.bookName = convertView.findViewById(R.id.book_name_textView);
            holder.publishedDate = convertView.findViewById(R.id.dateTextView);
            holder.bookAuthor = convertView.findViewById(R.id.author_textView);
            holder.bookImage = convertView.findViewById(R.id.book_image);


            convertView.setTag(holder);

        } else{
            holder = (ViewHolder) convertView.getTag();

        }
                //changed this from else

        Book currentBook = getItem(position);
        holder.position = position;


        holder.bookName.setText(currentBook.getBookName());
        holder.bookAuthor.setText(currentBook.getAuthor());
        holder.publishedDate.setText(currentBook.getDate());
        holder.Url = currentBook.getBookImage();


        holder.progressBar.setVisibility(View.VISIBLE);
        Log.i("LOGOOO", currentBook.getBookName()+ "WITH "+currentBook.getBookImage().toString());

        if (currentBook.getBookImage() != null && !currentBook.getBookImage().isEmpty()) {

            final ViewHolder finalHolder = holder;
            Picasso.with(convertView.getContext()).load(currentBook.getBookImage()).into(holder.bookImage, new Callback() {
                @Override
                public void onSuccess() {

                    finalHolder.progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onError() {
                    finalHolder.progressBar.setVisibility(View.GONE);
                    finalHolder.bookImage.setImageResource(R.mipmap.ic_launcher);
                }

            });

        } else if (currentBook.getBookImage() == null || currentBook.getBookImage().isEmpty()){
            final ViewHolder finalHolder = holder;
            finalHolder.progressBar.setVisibility(View.GONE);
            finalHolder.bookImage.setImageResource(R.mipmap.ic_launcher);


        }


        return convertView;

    }

    static class ViewHolder {
        ImageView bookImage;
        TextView bookName;
        TextView bookAuthor;
        TextView publishedDate;
        String Url;
        int position;
        ProgressBar progressBar;
    }

}
