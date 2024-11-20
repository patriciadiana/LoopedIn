package msa.looped.Entities;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import msa.looped.Entities.Project;

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
