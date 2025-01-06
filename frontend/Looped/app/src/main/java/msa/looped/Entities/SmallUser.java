package msa.looped.Entities;

import com.google.gson.annotations.SerializedName;

public class SmallUser {
    @SerializedName("username")
    private String username;

    @SerializedName("photo_url")
    private String photo_url;

    @Override
    public String toString() {
        return "SmallUser{" +
                "username='" + username + '\'' +
                ", photo_url='" + photo_url + '\'' +
                '}';
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhoto_url() {
        return photo_url;
    }

    public void setPhoto_url(String photo_url) {
        this.photo_url = photo_url;
    }
}
