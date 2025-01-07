package msa.looped.Entities;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ProjectBigResult implements Serializable {
    @SerializedName("project")
    private ProjectBig project;

    public ProjectBig getProject() {
        return project;
    }

    @Override
    public String toString() {
        return "ProjectBigResult{" +
                "project=" + project +
                '}';
    }
}
