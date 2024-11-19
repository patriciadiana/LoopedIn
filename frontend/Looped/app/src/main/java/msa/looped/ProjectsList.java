package msa.looped;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProjectsList {
    @SerializedName("projects")
    private List<Project> projects;
    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }
}
