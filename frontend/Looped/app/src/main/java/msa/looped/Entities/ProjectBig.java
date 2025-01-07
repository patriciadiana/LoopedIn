package msa.looped.Entities;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ProjectBig implements Serializable {
    @SerializedName("completed")
    private String completed;

    @SerializedName("completed_day_set")
    private String completed_day_set;

    @SerializedName("craft_name")
    private String craft_name;

    @SerializedName("size")
    private String size;

    @SerializedName("created_at")
    private String created_at;

    @SerializedName("favorites_count")
    private String favorites_count;

    @SerializedName("made_for")
    private String made_for;
    @SerializedName("name")
    private String name;

    @SerializedName("status_name")
    private String status_name;

    @SerializedName("notes_html")
    private String notes_html;

    @SerializedName("pattern_name")
    private String pattern_name;

    @SerializedName("photos")
    private List<FirstPhoto> photos;

    @SerializedName("progress")
    private int progress;
    @SerializedName("started")
    private String started;

    @SerializedName("started_day_set")
    private String started_day_set;

    @Override
    public String toString() {
        return "ProjectBig{" +
                "completed='" + completed + '\'' +
                ", completed_day_set=" + completed_day_set +
                ", craft_name='" + craft_name + '\'' +
                ", created_at='" + created_at + '\'' +
                ", favorites_count='" + favorites_count + '\'' +
                ", made_for='" + made_for + '\'' +
                ", name='" + name + '\'' +
                ", notes_html=" + notes_html +
                ", pattern_name=" + pattern_name +
                ", photos='" + photos + '\'' +
                ", progress='" + progress + '\'' +
                ", started='" + started + '\'' +
                ", started_day_set=" + started_day_set +
                '}';
    }

    public String getCompleted() {
        return completed;
    }

    public String getCompleted_day_set() {
        return completed_day_set;
    }

    public String getCraft_name() {
        return craft_name;
    }

    public String getSize() {
        return size;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getFavorites_count() {
        return favorites_count;
    }

    public String getStatus_name() {
        return status_name;
    }

    public String getMade_for() {
        return made_for;
    }

    public String getName() {
        return name;
    }

    public String getNotes_html() {
        return notes_html;
    }

    public String getPattern_name() {
        return pattern_name;
    }

    public List<FirstPhoto> getPhotos() {
        return photos;
    }

    public int getProgress() {
        return progress;
    }

    public String getStarted() {
        return started;
    }

    public String getStarted_day_set() {
        return started_day_set;
    }
}
