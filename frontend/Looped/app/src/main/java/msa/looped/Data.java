package msa.looped;

import msa.looped.Entities.User;

public class Data {
    private static Data INSTANCE;
    private static User currentUser;
    private String profilePicUrl = "";
//    private String apiUrl = "http://192.168.1.248:5298"; // Barsi
    private String apiUrl = "http://192.168.8.104:5298";  // Cala
//    private String apiUrl = "http://10.0.2.2:5298";       // emulator
    public String getApiUrl()
    {
        return apiUrl;
    }
    private Data() {
    }
    public static Data getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new Data();
            currentUser = new User();
        }
        return INSTANCE;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        Data.currentUser = currentUser;
    }

    public String getProfilePicUrl() {
        return profilePicUrl;
    }

    public void setProfilePicUrl(String profilePicUrl) {
        this.profilePicUrl = profilePicUrl;
    }
}
