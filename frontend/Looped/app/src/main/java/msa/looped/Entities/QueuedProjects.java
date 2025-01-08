package msa.looped.Entities;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class QueuedProjects {
    @SerializedName("queued_projects")
    private List<QueuedPattern> queued_projects;

    public List<QueuedPattern> getQueuedPatterns() {
        return queued_projects;
    }

    public void setQueuedPatterns(List<QueuedPattern> queuedPatterns) {
        this.queued_projects = queuedPatterns;
    }

    public int getQueuedPatternCount()
    {
        return queued_projects.size();
    }

    public void deletePatternAtPosition(int position)
    {
        position = position-1;
        queued_projects.remove(position);
    }
    @Override
    public String toString() {
        return "QueueResponse{" +
                "queuedPatterns=" + queued_projects +
                '}';
    }
}
