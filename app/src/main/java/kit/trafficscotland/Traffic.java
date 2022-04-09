package kit.trafficscotland;

import java.util.Date;

public class Traffic {
    private String title;
    private String description;
    private String link;
    private String point;
    private String pubDate;
    private Date startDate;
    private Date endDate;


    //Getter
    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getLink() {
        return link;
    }

    public String getPoint() {
        return point;
    }

    public String getPubDate() {
        return pubDate;
    }

    public Date getStartDate() { return startDate; }

    public Date getEndDate() { return endDate; }

    //Setter
    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public void setPubDate(String pubDate) { this.pubDate = pubDate; }

    public void setStartDate(Date startDate) { this.startDate = startDate; }

    public void setEndDate(Date endDate) { this.endDate = endDate; }

    //Empty Constructor
    public Traffic() {

    }

    //Constructor
    public Traffic(String title, String description, String link, String point, String pubDate, Date startDate, Date endDate) {
        this.title = title;
        this.description = description;
        this.link = link;
        this.point = point;
        this.pubDate = pubDate;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}