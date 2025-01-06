package msa.looped.Entities;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Friendslist {
    @SerializedName("friendships")
    private List<Friend> friendships;
    public List<Friend> getFriends() {
        return friendships;
    }
    public int getProjectsListSize()
    {
        return friendships.size();
    }

    public void setProjects(List<Friend> friendships) {
        this.friendships = friendships;
    }

    @Override
    public String toString() {
        return "Friendslist{" +
                "friendships=" + friendships +
                '}';
    }
}
