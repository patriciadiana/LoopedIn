package msa.looped.Entities;

import com.google.gson.annotations.SerializedName;

public class Paginator {
    @SerializedName("page_count")
    private int page_count;

    public int getPage_count() {
        return page_count;
    }

    public void setPage_count(int page_count) {
        this.page_count = page_count;
    }

    @Override
    public String toString() {
        return "Paginator{" +
                "page_count=" + page_count +
                '}';
    }
}
