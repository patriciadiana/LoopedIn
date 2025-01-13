package msa.looped.Entities;

import android.os.Build;

import com.google.gson.annotations.SerializedName;

import java.util.Base64;

public class Document {
    @SerializedName("title")
    private String Title;

    @SerializedName("data")
    private String Data;

    @SerializedName("authorName")
    private String AuthorName;

    @SerializedName("craft")
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

    public void setTitle(String title) {
        Title = title;
    }

    public String getData() {
        return Data;
    }
    public byte[] getDataBytes() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return Base64.getDecoder().decode(Data);
        }
        return null;
    }

    public void setData(String data) {
        Data = data;
    }

    public void setAuthorName(String authorName) {
        AuthorName = authorName;
    }

    public void setCraft(String craft) {
        Craft = craft;
    }
}
