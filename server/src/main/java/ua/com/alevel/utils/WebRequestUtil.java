package ua.com.alevel.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import ua.com.alevel.web.dto.requests.DataTableRequest;

import javax.persistence.criteria.Predicate;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public final class WebRequestUtil {

    private WebRequestUtil() {
        throw new IllegalStateException("Util class.");
    }

    public static PageRequest generatePageRequest(DataTableRequest findAllRequest, Class<?> clazz) {
        Sort sort = Sort.by(checkOrderBy(findAllRequest.getOrderBy()), checkSortBy(findAllRequest.getSortBy(), clazz));
        return PageRequest.of(checkPageParam(findAllRequest.getPage()), checkPageParam(findAllRequest.getSize()), sort);
    }

    public static <T> Specification<T> generateSpecification(List<String> keywords) {
        return (root, query, criteriaBuilder) -> {
            Predicate[] predicates = keywords.stream()
                    .map(keyword -> criteriaBuilder.or(
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%" + keyword.toLowerCase() + "%"),
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), "%" + keyword.toLowerCase() + "%")
                    ))
                    .toArray(Predicate[]::new);

            return criteriaBuilder.or(predicates);
        };
    }

    public static <T> Specification<T> generateSpecificationWithFavorites(List<String> keywords, List<String> listFavorites) {
        return (root, query, criteriaBuilder) -> {
            Predicate[] keywordPredicates = keywords.stream()
                    .map(keyword -> criteriaBuilder.or(
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%" + keyword.toLowerCase() + "%"),
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), "%" + keyword.toLowerCase() + "%")
                    ))
                    .toArray(Predicate[]::new);

            Predicate[] favoritesPredicates = listFavorites.stream()
                    .map(favorite -> criteriaBuilder.like(criteriaBuilder.lower(root.get("publisher")), "%" + favorite.toLowerCase() + "%"))
                    .toArray(Predicate[]::new);

            Predicate keywordPredicate = criteriaBuilder.and(keywordPredicates);
            Predicate favoritesPredicate = criteriaBuilder.or(favoritesPredicates);

            return criteriaBuilder.and(keywordPredicate, favoritesPredicate);
        };
    }


    public static <T> Specification<T> generateFavorites(List<String> listFavorites) {
        return (root, query, criteriaBuilder) -> {
            Predicate[] predicates = listFavorites.stream()
                    .map(keyword -> criteriaBuilder.like(
                            criteriaBuilder.lower(root.get("publisher")), "%" + keyword.toLowerCase() + "%"
                    ))
                    .toArray(Predicate[]::new);

            return criteriaBuilder.or(predicates);
        };
    }

    private static String checkSortBy(String sortBy, Class<?> clazz) {
        List<String> variableNames = Arrays.stream(clazz.getDeclaredFields())
                .map(Field::getName)
                .toList();

        return variableNames.contains(sortBy) ? sortBy : "publishedAt";
    }

    private static Sort.Direction checkOrderBy(String orderBy) {
        return orderBy.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
    }

    private static int checkPageParam(int pageParam) {
        return Math.max(pageParam, 0);
    }
}