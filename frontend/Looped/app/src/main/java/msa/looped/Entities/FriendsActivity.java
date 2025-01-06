package msa.looped.Entities;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FriendsActivity {
    @SerializedName("activities")
    private List<Activity> activities;

    public List<Activity> getActivities() {
        return activities;
    }

    public void setActivities(List<Activity> activities) {
        this.activities = activities;
    }

    @Override
    public String toString() {
        return "FriendsActivity{" +
                "activities=" + activities +
                '}';
    }
}
