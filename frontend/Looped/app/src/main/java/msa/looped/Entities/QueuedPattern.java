package msa.looped.Entities;

import com.google.gson.annotations.SerializedName;

import java.sql.Date;

public class QueuedPattern {

    @SerializedName("created_at")
    private String created_at;
    @SerializedName("short_pattern_name")
    private String short_pattern_name;
    @SerializedName("pattern_author_name")
    private String pattern_author_name;
    @SerializedName("make_for")
    private String make_for;
    @SerializedName("notes")
    private String notes;
    @SerializedName("id")
    private int id;
    @SerializedName("position_in_queue")
    private int position_in_queue;

    @SerializedName("best_photo")
    private FirstPhoto bestPhoto;

    public int getId() {
        return id;
    }

    public FirstPhoto getBestPhoto() {
        return bestPhoto;
    }

    public void setBestPhoto(FirstPhoto bestPhoto) {
        this.bestPhoto = bestPhoto;
    }

    public String getCreated_at_date(){
        return created_at;
    }
    public String getShort_pattern_name() {
        return short_pattern_name;
    }

    public void setShort_pattern_name(String short_pattern_name) {
        this.short_pattern_name = short_pattern_name;
    }

    public String getPattern_author_name() {
        return pattern_author_name;
    }

    public void setPattern_author_name(String pattern_author_name) {
        this.pattern_author_name = pattern_author_name;
    }

    public String getMake_for() {
        return make_for;
    }

    public void setMake_for(String make_for) {
        this.make_for = make_for;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getPosition_in_queue() {
        return position_in_queue;
    }

    public void setPosition_in_queue(int position_in_queue) {
        this.position_in_queue = position_in_queue;
    }

    @Override
    public String toString() {
        return "QueuedPattern{" +
                "short_pattern_name='" + short_pattern_name + '\'' +
                ", pattern_author_name='" + pattern_author_name + '\'' +
                ", created_at='" + created_at + '\'' +
                ", make_for='" + make_for + '\'' +
                ", notes='" + notes + '\'' +
                ", position_in_queue=" + position_in_queue +
                '}';
    }
}
