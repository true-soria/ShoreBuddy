package com.example.shorebuddy.utilities;

public class SearchQuery {
    private final String string;

    public SearchQuery(String s) {
        string = s;
    }

    public String getQuery() {
        return "%" + string + "%";
    }

    public String getRawString() {
        return string;
    }
}
