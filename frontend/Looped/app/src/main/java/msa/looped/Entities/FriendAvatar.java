package msa.looped.Entities;

import com.google.gson.annotations.SerializedName;

public class FriendAvatar {
    @SerializedName("large_photo_url")
    public String large_photo_url;

    public String getLarge_photo_url() {
        return large_photo_url;
    }
}
