package msa.looped.Entities;

import com.google.gson.annotations.SerializedName;

public class PatternAuthor {
    @SerializedName("name")
    private String name;

    @Override
    public String toString() {
        return "PatternAuthor{" +
                "name='" + name + '\'' +
                '}';
    }
}
