package msa.looped;

public class Project {
    private String name;
    private String thumbnail_url;

    public Project(String name, String thumbnailUrl)
    {
        this.name = name;
        this.thumbnail_url = thumbnailUrl;
    }

    public String getName()
    {
        return name;
    }
    public String getThumbnailUrl()
    {
        return thumbnail_url;
    }

}
