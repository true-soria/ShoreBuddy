package com.example.shorebuddy.utilities;

public class SearchQuery {
    private String mString;

    public SearchQuery(String s) {
        mString = s;
    }

    String getQuery() {
        return "%" + mString + "%";
    }

    String getRawString() {
        return mString;
    }
}
