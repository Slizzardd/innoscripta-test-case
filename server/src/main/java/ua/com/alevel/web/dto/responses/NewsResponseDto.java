package ua.com.alevel.web.dto.responses;

import ua.com.alevel.persistence.entity.News;

public class NewsResponseDto extends ResponseDto {

    private String title;

    private String description;

    private String content;

    private String publishedAt;

    private String sourceImage;

    private String publisher;

    private String sourcePublisher;

    public NewsResponseDto(News news) {
        setId(news.getId());
        setUpdated(news.getUpdated());
        setCreated(news.getCreated());
        this.title = news.getTitle();
        this.description = news.getDescription();
        this.content = news.getContent();
        this.publishedAt = news.getPublishedAt();
        this.sourceImage = news.getSourceImg();
        this.sourcePublisher = news.getSourcePublisher();
        this.publisher = news.getPublisher();
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
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

    public String getSourceImage() {
        return sourceImage;
    }

    public void setSourceImage(String sourceImage) {
        this.sourceImage = sourceImage;
    }

    public String getSourcePublisher() {
        return sourcePublisher;
    }

    public void setSourcePublisher(String sourcePublisher) {
        this.sourcePublisher = sourcePublisher;
    }
}
