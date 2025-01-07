package msa.looped.Entities;

import com.google.gson.annotations.SerializedName;

public class FirstPhoto {
    @SerializedName("thumbnail_url")
    public String thumbnailUrl;

    public String getThumbnailUrl()
    {
        return thumbnailUrl;
    }

    @Override
    public String toString() {
        return "FirstPhoto{" +
                "thumbnailUrl='" + thumbnailUrl + '\'' +
                '}';
    }
}
