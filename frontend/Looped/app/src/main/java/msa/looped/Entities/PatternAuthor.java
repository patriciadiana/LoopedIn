package msa.looped.Entities;

import com.google.gson.annotations.SerializedName;

public class PatternAuthor {
    @SerializedName("name")
    private String name;

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "PatternAuthor{" +
                "name='" + name + '\'' +
                '}';
    }
}
