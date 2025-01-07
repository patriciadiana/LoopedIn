package msa.looped.Entities;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchResults {
    @SerializedName("patterns")
    private List<SearchPattern> patterns;

    @SerializedName("paginator")
    private Paginator paginator;

    public List<SearchPattern> getPatterns() {
        return patterns;
    }

    @Override
    public String toString() {
        return "SearchResults{" +
                "patterns=" + patterns +
                '}';
    }
}
