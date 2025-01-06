package msa.looped.Entities;

import com.google.gson.annotations.SerializedName;

public class Friend {
    @SerializedName("created_at")
    private String created_at;
    @SerializedName("friend_username")
    private String friend_username;
    @SerializedName("friend_avatar")
    private FriendAvatar friend_avatar;

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getFriend_username() {
        return friend_username;
    }

    public void setFriend_username(String friend_username) {
        this.friend_username = friend_username;
    }

    public FriendAvatar getFriend_avatar() {
        return friend_avatar;
    }

    public void setFriend_avatar(FriendAvatar friend_avatar) {
        this.friend_avatar = friend_avatar;
    }

    @Override
    public String toString() {
        return "Friend{" +
                "created_at='" + created_at + '\'' +
                ", friend_username='" + friend_username + '\'' +
                ", friend_avatar=" + friend_avatar +
                '}';
    }
}
