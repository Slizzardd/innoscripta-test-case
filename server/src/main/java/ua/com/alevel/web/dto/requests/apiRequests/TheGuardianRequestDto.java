package ua.com.alevel.web.dto.requests.apiRequests;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.List;

public class TheGuardianRequestDto {
    private Response response;

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public static class Response {
        private String status;
        private String userTier;
        private int total;
        private int startIndex;
        private int pageSize;
        private int currentPage;
        private int pages;
        private String orderBy;
        private List<Result> results;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getUserTier() {
            return userTier;
        }

        public void setUserTier(String userTier) {
            this.userTier = userTier;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getStartIndex() {
            return startIndex;
        }

        public void setStartIndex(int startIndex) {
            this.startIndex = startIndex;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getCurrentPage() {
            return currentPage;
        }

        public void setCurrentPage(int currentPage) {
            this.currentPage = currentPage;
        }

        public int getPages() {
            return pages;
        }

        public void setPages(int pages) {
            this.pages = pages;
        }

        public String getOrderBy() {
            return orderBy;
        }

        public void setOrderBy(String orderBy) {
            this.orderBy = orderBy;
        }

        public List<Result> getResults() {
            return results;
        }

        public void setResults(List<Result> results) {
            this.results = results;
        }
    }

    public static class Result {
        private String id;
        private String type;
        private String sectionId;
        private String sectionName;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
        private Date webPublicationDate;
        private String webTitle;
        private String webUrl;
        private String apiUrl;
        private boolean isHosted;
        private String pillarId;
        private String pillarName;

        public Date getWebPublicationDate() {
            return webPublicationDate;
        }

        public void setWebPublicationDate(Date webPublicationDate) {
            this.webPublicationDate = webPublicationDate;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getSectionId() {
            return sectionId;
        }

        public void setSectionId(String sectionId) {
            this.sectionId = sectionId;
        }

        public String getSectionName() {
            return sectionName;
        }

        public void setSectionName(String sectionName) {
            this.sectionName = sectionName;
        }

        public String getWebTitle() {
            return webTitle;
        }

        public void setWebTitle(String webTitle) {
            this.webTitle = webTitle;
        }

        public String getWebUrl() {
            return webUrl;
        }

        public void setWebUrl(String webUrl) {
            this.webUrl = webUrl;
        }

        public String getApiUrl() {
            return apiUrl;
        }

        public void setApiUrl(String apiUrl) {
            this.apiUrl = apiUrl;
        }

        public boolean isHosted() {
            return isHosted;
        }

        public void setHosted(boolean hosted) {
            isHosted = hosted;
        }

        public String getPillarId() {
            return pillarId;
        }

        public void setPillarId(String pillarId) {
            this.pillarId = pillarId;
        }

        public String getPillarName() {
            return pillarName;
        }

        public void setPillarName(String pillarName) {
            this.pillarName = pillarName;
        }
    }
}
