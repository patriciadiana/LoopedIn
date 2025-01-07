package msa.looped.Entities;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchResults {
    @SerializedName("patterns")
    private List<SearchPattern> patterns;

    @Override
    public String toString() {
        return "SearchResults{" +
                "patterns=" + patterns +
                '}';
    }
}
