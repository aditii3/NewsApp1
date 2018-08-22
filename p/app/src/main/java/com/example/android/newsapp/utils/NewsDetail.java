package com.example.android.newsapp.utils;


public class NewsDetail {
    private String section;
    private String title;
    private String webUrl;
    private String publishedDate;
    private String author;

    NewsDetail(String section, String title, String webUrl, String publishedDate, String author) {
        this.section = section;
        this.publishedDate = publishedDate;
        this.title = title;
        this.webUrl = webUrl;
        this.author = author;
    }

    String getAuthor() {
        return author;
    }


    String getSection() {
        return section;
    }

    String getTitle() {
        return title;
    }

    public String getWebUrl() {
        return webUrl;
    }

    String getPublishedDate() {
        return publishedDate;
    }

}
