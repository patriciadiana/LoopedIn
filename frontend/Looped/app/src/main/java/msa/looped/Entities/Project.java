package msa.looped.Entities;

import com.google.gson.annotations.SerializedName;

public class Project {
    @SerializedName("name")
    private String name;

    @SerializedName("progress")
    private int progress;

    @SerializedName("favorites_count")
    private String likes;

    @SerializedName("status_name")
    private String status;

    @SerializedName("first_photo")
    private FirstPhoto firstPhoto;

    public String getName()
    {
        return name;
    }

    public int getProgress() { return progress; }

    public String getStatus() { return status; }

    public String getLikes() { return likes; }

    public FirstPhoto getFirstPhoto()
    {
        return firstPhoto;
    }
}

