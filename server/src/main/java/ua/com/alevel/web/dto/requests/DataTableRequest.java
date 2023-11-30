package ua.com.alevel.web.dto.requests;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DataTableRequest extends RequestDto {

    private int page;
    private int size = 20;
    private String sortBy="publishedAt";
    private String orderBy = "desc";
    private String specifications = "";
    private Boolean myFavorite = false;

    public DataTableRequest() {
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public List<String> getSpecifications() {
        return convert(specifications);
    }

    public void setSpecifications(String specifications) {
        this.specifications = specifications;
    }

    public Boolean getMyFavorite() {
        return myFavorite;
    }

    public void setMyFavorite(Boolean myFavorite) {
        this.myFavorite = myFavorite;
    }

    private static List<String> convert(String input) {
        if (input == null || input.isEmpty()) {
            return List.of();
        }

        return Arrays.stream(input.split(","))
                .map(String::trim)
                .filter(str -> !str.isEmpty())
                .collect(Collectors.toList());
    }
}
