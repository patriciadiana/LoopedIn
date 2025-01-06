package msa.looped.Entities;

import com.google.gson.annotations.SerializedName;

public class Activity {
    @SerializedName("title")
    private String title;

    @SerializedName("created_at")
    private String created_at;

    @SerializedName("user")
    private SmallUser user;

    @SerializedName("photo")
    private FirstPhoto activityPhoto;

    @Override
    public String toString() {
        return "Activity{" +
                "title='" + title + '\'' +
                ", created_at='" + created_at + '\'' +
                ", user=" + user +
                ", activityPhoto=" + activityPhoto +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public SmallUser getUser() {
        return user;
    }

    public void setUser(SmallUser user) {
        this.user = user;
    }

    public FirstPhoto getActivityPhoto() {
        return activityPhoto;
    }

    public void setActivityPhoto(FirstPhoto activityPhoto) {
        this.activityPhoto = activityPhoto;
    }
}
