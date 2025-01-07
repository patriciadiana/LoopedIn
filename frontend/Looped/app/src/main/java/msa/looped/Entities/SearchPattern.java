package msa.looped.Entities;

import com.google.gson.annotations.SerializedName;

public class SearchPattern {
    @SerializedName("name")
    private String patternName;

    @SerializedName("designer")
    private PatternAuthor patternAuthor;
    @SerializedName("first_photo")
    private FirstPhoto patterPhoto;

    public String getPatternName() {
        return patternName;
    }

    public PatternAuthor getPatternAuthor() {
        return patternAuthor;
    }

    public FirstPhoto getPatterPhoto() {
        return patterPhoto;
    }

    @Override
    public String toString() {
        return "SearchPattern{" +
                "patternName='" + patternName + '\'' +
                ", patternAuthor=" + patternAuthor +
                ", patterPhoto=" + patterPhoto +
                '}';
    }
}
