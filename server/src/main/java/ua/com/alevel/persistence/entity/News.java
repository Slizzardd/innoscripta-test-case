package ua.com.alevel.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name= "news")
public class News extends BaseEntity{

    @Column(name = "uniq_id", unique = true)
    private String uniqId;

    @Column(name = "publisher_name")
    private String publisher;

    @Column(name = "published_at")
    private String publishedAt;

    @Column(name = "source_img")
    private String sourceImg;

    @Column(name = "source_publisher")
    private String sourcePublisher;

    @Column(name = "title")
    private String title;

    @Column(name = "description", length = 500)
    private String description;

    @Column(name = "content", length = 3000)
    private String content;

    public News() {
        super();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getUniqId() {
        return uniqId;
    }

    public void setUniqId(String uniqId) {
        this.uniqId = uniqId;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getSourceImg() {
        return sourceImg;
    }

    public void setSourceImg(String sourceImg) {
        this.sourceImg = sourceImg;
    }

    public String getSourcePublisher() {
        return sourcePublisher;
    }

    public void setSourcePublisher(String sourcePublisher) {
        this.sourcePublisher = sourcePublisher;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
