package msa.looped;

import com.google.gson.annotations.SerializedName;

public class FirstPhoto {
    @SerializedName("thumbnail_url")
    public String thumbnailUrl;

    public String getThumbnailUrl()
    {
        return thumbnailUrl;
    }
}
