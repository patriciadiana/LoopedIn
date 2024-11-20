package msa.looped;

public class Data {
    private static Data INSTANCE;
    private String profilePicUrl = "";
    private String apiUrl = "http://192.168.8.104:5298";
//    private String apiUrl = "http://10.0.2.2:5298";
    public String getApiUrl()
    {
        return apiUrl;
    }
    private Data() {
    }
    public static Data getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new Data();
        }

        return INSTANCE;
    }

    public String getProfilePicUrl() {
        return profilePicUrl;
    }

    public void setProfilePicUrl(String profilePicUrl) {
        this.profilePicUrl = profilePicUrl;
    }
}
