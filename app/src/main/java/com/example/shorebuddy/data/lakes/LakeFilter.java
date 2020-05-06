package com.example.shorebuddy.data.lakes;

import com.example.shorebuddy.utilities.SearchQuery;

public class LakeFilter {
    public SearchQuery name;
    public String county;

    public LakeFilter() {
        this.name = new SearchQuery("");
        county = null;
    }
}
