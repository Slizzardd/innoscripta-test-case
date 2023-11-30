package ua.com.alevel.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import ua.com.alevel.persistence.entity.News;

import java.util.List;

public interface NewsService extends BaseService<News> {

    News createNews(News news);


    List<News> findAllNews(PageRequest pageRequest);
    List<News> findAllNews(PageRequest pageRequest, Specification<News> specification);

    void createNewsFromAPI(List<News> newsList);

    List<String> findUniqPublisher();
}
