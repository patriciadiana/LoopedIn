package msa.looped;

import com.google.gson.annotations.SerializedName;

public class Project {
    @SerializedName("name")
    private String name;

    @SerializedName("first_photo")
    private FirstPhoto firstPhoto;

    public String getName()
    {
        return name;
    }

    public FirstPhoto getFirstPhoto()
    {
        return firstPhoto;
    }
}

