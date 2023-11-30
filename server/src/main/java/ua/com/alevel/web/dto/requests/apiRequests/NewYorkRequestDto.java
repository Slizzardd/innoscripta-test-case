package ua.com.alevel.web.dto.requests.apiRequests;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.List;

public class NewYorkRequestDto {
    private String status;
    private List<Article> results;

    public static class Article {

        @JsonProperty("url")
        private String sourceUrl;
        private String source;

        @JsonProperty("updated")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        private Date publishedAt;
        private String title;

        @JsonProperty("abstract")
        private String description;
        private List<Media> media;

        public static class Media {
            @JsonProperty("media-metadata")
            private List<MediaMetadata> mediaMetadata;

            public static class MediaMetadata {

                @JsonProperty("url")
                private String sourceImageUrl;

                public String getSourceImageUrl() {
                    return sourceImageUrl;
                }

                public void setSourceImageUrl(String sourceImageUrl) {
                    this.sourceImageUrl = sourceImageUrl;
                }
            }

            public List<MediaMetadata> getMediaMetadata() {
                return mediaMetadata;
            }

            public void setMediaMetadata(List<MediaMetadata> mediaMetadata) {
                this.mediaMetadata = mediaMetadata;
            }
        }

        public String getSourceUrl() {
            return sourceUrl;
        }

        public void setSourceUrl(String sourceUrl) {
            this.sourceUrl = sourceUrl;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public Date getPublishedAt() {
            return publishedAt;
        }

        public void setPublishedAt(Date publishedAt) {
            this.publishedAt = publishedAt;
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

        public List<Media> getMedia() {
            return media;
        }

        public void setMedia(List<Media> media) {
            this.media = media;
        }
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Article> getResults() {
        return results;
    }

    public void setResults(List<Article> results) {
        this.results = results;
    }
}
