package com.example.shorebuddy.utilities;

public class SearchQuery {
    private final String mString;

    public SearchQuery(String s) {
        mString = s;
    }

    public String getQuery() {
        return "%" + mString + "%";
    }

    public String getRawString() {
        return mString;
    }
}
