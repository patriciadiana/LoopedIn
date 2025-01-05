package msa.looped;

import msa.looped.Entities.ProjectsList;
import msa.looped.Entities.QueuedProjects;
import msa.looped.Entities.User;

public class Data {
    public static boolean authorizationComplete = false;
    private static Data INSTANCE;
    private static User currentUser;
    private static String userCode = "";
    private static String profilePicUrl = "";
//    private String apiUrl = "http://192.168.1.160:5298"; // Barsi
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

    public static String getUserCode() {
        return userCode;
    }

    public static void setUserCode(String userCode) {
        Data.userCode = userCode;
    }

    private static ProjectsList projectsList;
    private static QueuedProjects queuedProjects;

    public static QueuedProjects getQueuedProjects() {
        return queuedProjects;
    }

    public static void setQueuedProjects(QueuedProjects queuedProjects) {
        Data.queuedProjects = queuedProjects;
    }

    public static ProjectsList getProjectsList() {
        return projectsList;
    }

    public static void setProjectsList(ProjectsList projectsList) {
        Data.projectsList = projectsList;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        profilePicUrl = currentUser.getLarge_photo_url();
        Data.currentUser = currentUser;
    }

    public String getProfilePicUrl() {
        return profilePicUrl;
    }

}
