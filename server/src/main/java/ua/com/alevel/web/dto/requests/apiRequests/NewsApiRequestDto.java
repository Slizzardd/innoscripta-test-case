package ua.com.alevel.web.dto.requests.apiRequests;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.List;

public class NewsApiRequestDto {
    private String status;

    @JsonProperty("articles")
    private List<Article> result;

    public static class Article {
        private Source source;
        private String title;
        private String description;
        @JsonProperty("url")
        private String sourceURL;
        @JsonProperty("urlToImage")
        private String sourceImageUrl;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
        private Date publishedAt;
        private String content;

        public static class Source {
            private String name;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }

        public Source getSource() {
            return source;
        }

        public void setSource(Source source) {
            this.source = source;
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

        public String getSourceURL() {
            return sourceURL;
        }

        public void setSourceURL(String sourceURL) {
            this.sourceURL = sourceURL;
        }

        public String getSourceImageUrl() {
            return sourceImageUrl;
        }

        public void setSourceImageUrl(String sourceImageUrl) {
            this.sourceImageUrl = sourceImageUrl;
        }

        public Date getPublishedAt() {
            return publishedAt;
        }

        public void setPublishedAt(Date publishedAt) {
            this.publishedAt = publishedAt;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Article> getResult() {
        return result;
    }

    public void setResult(List<Article> result) {
        this.result = result;
    }
}
