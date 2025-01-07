package msa.looped.Entities;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Project implements Serializable {
    @SerializedName("name")
    private String name;

    @SerializedName("progress")
    private int progress;

    @SerializedName("favorites_count")
    private String likes;

    @SerializedName("made_for")
    private String made_for;

    @SerializedName("status_name")
    private String status;

    @SerializedName("permalink")
    private String permalink;
    @SerializedName("pattern_name")
    private String pattern_name;

    @SerializedName("craft_id")
    private int craft_id;

    @SerializedName("first_photo")
    private FirstPhoto firstPhoto;

    public String getPermalink() {
        return permalink;
    }

    public String getPattern_name() {
        return pattern_name;
    }

    public int getCraft_id() {
        return craft_id;
    }

    public String getName()
    {
        return name;
    }

    public int getProgress() { return progress; }

    public String getMade_for() {
        return made_for;
    }

    public String getStatus() { return status; }

    public String getLikes() { return likes; }

    public FirstPhoto getFirstPhoto()
    {
        return firstPhoto;
    }

    public Project(String name, int progress, String likes, String made_for, String status, String pattern_name, int craft_id, FirstPhoto firstPhoto) {
        this.name = name;
        this.progress = progress;
        this.likes = likes;
        this.made_for = made_for;
        this.status = status;
        this.pattern_name = pattern_name;
        this.craft_id = craft_id;
        this.firstPhoto = firstPhoto;
    }
}

