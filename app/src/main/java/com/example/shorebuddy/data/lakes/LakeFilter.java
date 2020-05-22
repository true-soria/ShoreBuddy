package com.example.shorebuddy.data.lakes;

import com.example.shorebuddy.utilities.SearchQuery;

import java.util.List;

public class LakeFilter {
    public SearchQuery name;
    public List<String> county;
    public List<String> fish;

    public LakeFilter() {
        reset();
    }

    public void reset() {
        this.name = new SearchQuery("");
        county = null;
        fish = null;
    }

    public List<String> getFishSpecies() {
        return this.fish;
    }
}
