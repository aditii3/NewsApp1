package com.example.android.newsapp.ui;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.example.android.newsapp.R;
import com.example.android.newsapp.utils.NewsAdapter;
import com.example.android.newsapp.utils.NewsDetail;
import com.example.android.newsapp.utils.NewsLoader;

import java.net.URL;
import java.util.List;

import static android.support.design.widget.Snackbar.LENGTH_SHORT;

public class NewsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<NewsDetail>>, SwipeRefreshLayout.OnRefreshListener, NewsAdapter.NewsItemListener {
    private RecyclerView recyclerView;
    private CoordinatorLayout coordinatorLayout;
    private final int LOADER_ID = 11;
    private NewsAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ConnectivityManager connectivityManager;
    private NetworkInfo activeNetworkInfo;
    private URL url;
    private TextView noText;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        setContentView(R.layout.activity_news);
        recyclerView = findViewById(R.id.recycler_view);
        coordinatorLayout = findViewById(R.id.coordinator_layout);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        noText = findViewById(R.id.tv_no_text);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new NewsAdapter(this, this);
        recyclerView.setAdapter(adapter);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent));
        connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        getSupportLoaderManager().initLoader(LOADER_ID, null, NewsActivity.this);
    }


    @NonNull
    @Override
    public Loader<List<NewsDetail>> onCreateLoader(int id, @Nullable Bundle args) {
        swipeRefreshLayout.setRefreshing(true);
        return new NewsLoader(this);

    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<NewsDetail>> loader, List<NewsDetail> data) {
        swipeRefreshLayout.setRefreshing(false);
        if (data != null && data.size() >= 1) {
            noText.setVisibility(View.GONE);
            adapter.clearData();
            adapter.setList(data);
            adapter.notifyDataSetChanged();

        } else if (NewsLoader.getResponse()) {
            noText.setVisibility(View.VISIBLE);
            noText.setText(getString(R.string.no_data));
        } else if (NewsLoader.checkDataNotAvailable()) {
            noText.setVisibility(View.VISIBLE);
            noText.setText(R.string.no_news_available);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<NewsDetail>> loader) {
        adapter.clearData();
    }

    @Override
    public void onRefresh() {

        if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
            getSupportLoaderManager().restartLoader(LOADER_ID, null, this);
            Snackbar.make(coordinatorLayout, getString(R.string.refresh), LENGTH_SHORT).show();
        } else {
            Snackbar.make(coordinatorLayout, getString(R.string.check_internet), LENGTH_SHORT).show();
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onNewsItemClick(int clickedIndex) {
        Intent viewNews = new Intent("android.intent.action.VIEW", Uri.parse(adapter.getNewsItem(clickedIndex).getWebUrl()));
        startActivity(viewNews);
    }


}
