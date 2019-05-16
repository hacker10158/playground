package com.troy.playground.server.response;

import com.troy.playground.base.model.ImageData;

import java.util.List;

public class SearchPictureResponse {
    int total;
    int totalHits;
    List<ImageData> hits;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotalHits() {
        return totalHits;
    }

    public void setTotalHits(int totalHits) {
        this.totalHits = totalHits;
    }

    public List<ImageData> getHits() {
        return hits;
    }

    public void setHits(List<ImageData> hits) {
        this.hits = hits;
    }
}
