package com.example.android.newsapp.query;

import java.net.MalformedURLException;
import java.net.URL;


public class GenerateUri {
    private static final String GENERATED_API_KEY = "9f77a22f-b501-4dff-b9ba-f449a53a8731";
    private static final String PROTOCOL = "https";
    private static final String AUTHORITY = "content.guardianapis.com";
    private static final String ORDER_BY = "order-by";
    private static final String API = "api-key";
    private static final String ORDER_NEWEST = "newest";
    private static final String FORMAT = "format";
    private static final String JSON = "json";
    private static final String ITEMS = "page-size";
    private static final String TYPE = "search";
    private static final String NUM_ITEMS = "30";
    private static final String QUERY = "q";
    private static final String QUERY_PARAM = "recent";
    private static final String SHOW_TAGS = "show-tags";
    private static final String CONTRIBUTOR = "contributor";


    public static URL getUri() {
        try {
            return new URL(baseUri());
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String baseUri() {
        return new android.net.Uri.Builder()
                .scheme(PROTOCOL)
                .encodedAuthority(AUTHORITY)
                .appendPath(TYPE)
                .appendQueryParameter(QUERY, QUERY_PARAM)
                .appendQueryParameter(SHOW_TAGS, CONTRIBUTOR)
                .appendQueryParameter(ORDER_BY, ORDER_NEWEST)
                .appendQueryParameter(API, GENERATED_API_KEY)
                .appendQueryParameter(FORMAT, JSON)
                .appendQueryParameter(ITEMS, NUM_ITEMS)
                .build()
                .toString();
    }
}
