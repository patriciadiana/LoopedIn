package msa.looped.Entities;

import com.google.gson.annotations.SerializedName;

public class Document {
    @SerializedName("Title")
    private String Title;

    @SerializedName("AuthorName")
    private String AuthorName;

    @SerializedName("Craft")
    private String Craft;

    @Override
    public String toString() {
        return "Document{" +
                "Title='" + Title + '\'' +
                ", AuthorName='" + AuthorName + '\'' +
                ", Craft='" + Craft + '\'' +
                '}';
    }

    public String getTitle() {
        return Title;
    }

    public String getAuthorName() {
        return AuthorName;
    }

    public String getCraft() {
        return Craft;
    }
}
