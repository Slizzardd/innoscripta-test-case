package ua.com.alevel.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ua.com.alevel.persistence.entity.News;
import ua.com.alevel.web.dto.requests.apiRequests.NewYorkRequestDto;
import ua.com.alevel.web.dto.requests.apiRequests.NewsApiRequestDto;
import ua.com.alevel.web.dto.requests.apiRequests.TheGuardianRequestDto;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public final class NewsUtil {

    private static final String NEWS_API_SECRET_KEY = "2115db90a13c40b28bc53398ec733d51";
    private static final String NEW_YORK_TIME_SECRET_KEY = "gQbAPta9ew5jnZWV1dO0GdZhJZHFHBA1";
    private static final String THE_GUARDIAN_SECRET_KEY = "0fd83cb3-dcff-4756-814f-b598dd1b29c5";
    private static final Logger logger = LoggerFactory.getLogger(NewsUtil.class);
    private static final RestTemplate restTemplate = new RestTemplateBuilder().build();
    private static final String NEWS_API_DOMAIN = "https://newsapi.org/v2/everything";
    private static final String NEW_YORK_TIME_API_DOMAIN = "https://api.nytimes.com/svc/mostpopular/v2/viewed/1.json";
    private static final String THE_GUARDIAN_API_DOMAIN = "https://content.guardianapis.com/search";

    private static final UriComponentsBuilder NEWS_API_BUILDER = UriComponentsBuilder.fromUriString(NEWS_API_DOMAIN)
            .queryParam("domains", "buzzfeednews.com,bloomberg.com,bbc.co.uk,techcrunch.com,thenextweb.com")
            .queryParam("sortBy", "publishedAt")
            .queryParam("pageSize", "100")
            .queryParam("apiKey", NEWS_API_SECRET_KEY);

    private static final UriComponentsBuilder THE_GUARDIAN_BUILDER = UriComponentsBuilder.fromUriString(THE_GUARDIAN_API_DOMAIN)
            .queryParam("order-by", "newest")
            .queryParam("api-key", THE_GUARDIAN_SECRET_KEY);

    private static final UriComponentsBuilder NEW_YORK_TIME_BUILDER = UriComponentsBuilder.fromUriString(NEW_YORK_TIME_API_DOMAIN)
            .queryParam("api-key", NEW_YORK_TIME_SECRET_KEY);

    private NewsUtil() {
        throw new IllegalStateException("Util class.");
    }

    public static List<News> getLatestNewsFromNewsApi() {
        try {
            NewsApiRequestDto response = restTemplate.getForObject(NEWS_API_BUILDER.toUriString(), NewsApiRequestDto.class);
            return Optional.ofNullable(response)
                    .map(NewsUtil::newsApiToNews)
                    .orElse(Collections.emptyList());
        } catch (Exception e) {
            logError("Error fetching news from News API", e);
            return Collections.emptyList();
        }
    }

    public static List<News> getLatestNewsFromNYT() {
        try {
            NewYorkRequestDto response = restTemplate.getForObject(NEW_YORK_TIME_BUILDER.toUriString(), NewYorkRequestDto.class);
            return Optional.ofNullable(response)
                    .map(NewsUtil::newYorkReqToNews)
                    .orElse(Collections.emptyList());
        } catch (Exception e) {
            logError("Error fetching news from New York Times API", e);
            return Collections.emptyList();
        }
    }

    public static List<News> getLatestNewsFromTheGuardian() {
        try {
            TheGuardianRequestDto response = restTemplate.getForObject(THE_GUARDIAN_BUILDER.toUriString(), TheGuardianRequestDto.class);
            return Optional.ofNullable(response)
                    .map(NewsUtil::theGuardianToNews)
                    .orElse(Collections.emptyList());
        } catch (Exception e) {
            logError("Error fetching news from The Guardian API", e);
            return Collections.emptyList();
        }
    }

    private static List<News> newYorkReqToNews(NewYorkRequestDto requestDto) {
        return requestDto.getResults().stream()
                .map(item -> {
                    News news = new News();
                    news.setUniqId(generateUniqueId(formatDate(item.getPublishedAt()), item.getSource()));
                    news.setTitle(item.getTitle());
                    news.setDescription(item.getDescription());
                    news.setPublisher(item.getSource());
                    String sourceImg = item.getMedia().stream()
                            .findFirst().flatMap(media -> media.getMediaMetadata().stream()
                                    .skip(2)
                                    .findFirst()
                                    .map(NewYorkRequestDto.Article.Media.MediaMetadata::getSourceImageUrl))
                            .orElse(null);
                    news.setSourceImg(sourceImg);
                    news.setSourcePublisher(item.getSourceUrl());
                    news.setPublishedAt(formatDate(item.getPublishedAt()));
                    return news;
                })
                .collect(Collectors.toList());
    }

    private static List<News> theGuardianToNews(TheGuardianRequestDto requestDto) {
        return requestDto.getResponse().getResults().stream()
                .map(item -> {
                    News news = new News();
                    news.setUniqId(generateUniqueId(formatDate(item.getWebPublicationDate()), "The Guardian"));
                    news.setPublisher("The Guardian");
                    news.setTitle(item.getWebTitle());
                    news.setSourcePublisher(item.getWebUrl());
                    news.setPublishedAt(formatDate(item.getWebPublicationDate()));
                    return news;
                })
                .collect(Collectors.toList());
    }

    private static List<News> newsApiToNews(NewsApiRequestDto requestDto) {
        return requestDto.getResult().stream()
                .map(item -> {
                    News news = new News();
                    news.setUniqId(generateUniqueId(formatDate(item.getPublishedAt()), item.getSource().getName()));
                    news.setTitle(item.getTitle());
                    news.setPublishedAt(formatDate(item.getPublishedAt()));
                    news.setDescription(item.getDescription());
                    news.setSourceImg(item.getSourceImageUrl());
                    news.setSourcePublisher(item.getSourceURL());
                    news.setContent(removeBracketContent(item.getContent()));
                    news.setPublisher(item.getSource().getName());
                    return news;
                })
                .collect(Collectors.toList());
    }

    private static String generateUniqueId(String publishedAt, String publisher) {
        return String.format("%s_%s", publishedAt, publisher);
    }

    private static String formatDate(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd/HH:mm:ss").format(date);
    }

    private static String removeBracketContent(String input) {
        String regex = "\\[.*?\\]";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher.replaceAll("");
    }

    private static void logError(String message, Exception e) {
        logger.error(message, e);
    }
}
