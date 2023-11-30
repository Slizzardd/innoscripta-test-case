package ua.com.alevel.facade.impl;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ua.com.alevel.facade.NewsFacade;
import ua.com.alevel.persistence.entity.News;
import ua.com.alevel.service.NewsService;
import ua.com.alevel.service.UserService;
import ua.com.alevel.utils.WebRequestUtil;
import ua.com.alevel.web.dto.requests.DataTableRequest;
import ua.com.alevel.web.dto.responses.NewsResponseDto;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NewsFacadeImpl implements NewsFacade {

    private final NewsService newsService;
    private final UserService userService;


    public NewsFacadeImpl(NewsService newsService, UserService userService) {
        this.newsService = newsService;
        this.userService = userService;
    }

    @Override
    public List<NewsResponseDto> findAllNews(DataTableRequest request, String authToken) {
        List<String> specifications = request.getSpecifications();

        List<News> resultList;

        if (authToken != null) {
            if (request.getMyFavorite()) {
                resultList = findAllWithFavorite(request, authToken);
            } else {
                resultList = !specifications.isEmpty()
                        ? newsService.findAllNews(WebRequestUtil.generatePageRequest(request, News.class), WebRequestUtil.generateSpecification(specifications))
                        : newsService.findAllNews(WebRequestUtil.generatePageRequest(request, News.class));
            }
        } else {
            resultList = !specifications.isEmpty()
                    ? newsService.findAllNews(WebRequestUtil.generatePageRequest(request, News.class), WebRequestUtil.generateSpecification(specifications))
                    : newsService.findAllNews(WebRequestUtil.generatePageRequest(request, News.class));
        }

        return resultList.stream().map(NewsResponseDto::new).collect(Collectors.toList());
    }

    @Override
    public List<String> findAllUniqPublisher() {
        return newsService.findUniqPublisher();
    }

    private List<News> findAllWithFavorite(DataTableRequest request, String authToken) {
        List<String> specifications = request.getSpecifications();
        try {
            String pref = userService.findUserByJwtToken(authToken).getPreferredPublisher();
            List<String> preferredPublisher = Arrays.asList(pref.split(","));
            Specification<News> favoritesSpecification = WebRequestUtil.generateFavorites(preferredPublisher);

            if (!specifications.isEmpty()) {
                Specification<News> combinedSpecification = favoritesSpecification.and(
                        WebRequestUtil.generateSpecificationWithFavorites(specifications, preferredPublisher)
                );
                return newsService.findAllNews(WebRequestUtil.generatePageRequest(request, News.class), combinedSpecification);
            } else {
                return newsService.findAllNews(WebRequestUtil.generatePageRequest(request, News.class), favoritesSpecification);
            }
        } catch (NullPointerException e) {
            return !specifications.isEmpty()
                    ? newsService.findAllNews(WebRequestUtil.generatePageRequest(request, News.class), WebRequestUtil.generateSpecification(specifications))
                    : newsService.findAllNews(WebRequestUtil.generatePageRequest(request, News.class));
        }
    }
}
