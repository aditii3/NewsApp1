package com.example.android.newsapp.utils;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.newsapp.R;

import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsItemHolder> {
    private List<NewsDetail> list = null;
    private final NewsItemListener callback;

    public NewsAdapter(Context c, NewsItemListener listener) {
        this.list = new ArrayList<NewsDetail>();
        callback = listener;
    }

    public void setList(List l) {
        list = l;
    }

    public void clearData() {
        list.clear();
    }

    public NewsDetail getNewsItem(int position) {
        return list.get(position);
    }

    @NonNull
    @Override
    public NewsItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item, parent, false);
        NewsItemHolder holder = new NewsItemHolder(root);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull NewsItemHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public interface NewsItemListener {
        public void onNewsItemClick(int clickedIndex);
    }


    public class NewsItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView newsTitle;
        TextView newsHeading;
        TextView newsDate;
        ImageView newsImage;
        TextView newsAuthor;

        public NewsItemHolder(View itemView) {
            super(itemView);
            newsTitle = itemView.findViewById(R.id.news_title);
            newsHeading = itemView.findViewById(R.id.news_heading);
            newsDate = itemView.findViewById(R.id.news_date);
            newsImage = itemView.findViewById(R.id.news_image);
            newsAuthor = itemView.findViewById(R.id.news_author);
            itemView.setOnClickListener(this);
        }

        private void bind(int index) {
            newsTitle.setText(list.get(index).getTitle());
            newsHeading.setText(list.get(index).getSection());
            newsAuthor.setText(list.get(index).getAuthor());
            switch (list.get(index).getSection()) {
                case "Technology":
                    newsImage.setImageResource(R.drawable.tech);
                    break;
                case "Sport":
                    newsImage.setImageResource(R.drawable.sports);
                    break;
                case "Politics":
                    newsImage.setImageResource(R.drawable.politivcs);
                    break;
                default:
                    newsImage.setImageResource(R.drawable.ams);
            }
            newsDate.setText(list.get(index).getPublishedDate().substring(0, 10));

        }

        @Override
        public void onClick(View v) {
            callback.onNewsItemClick(getAdapterPosition());
        }
    }
}
