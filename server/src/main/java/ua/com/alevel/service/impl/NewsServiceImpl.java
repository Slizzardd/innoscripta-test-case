package ua.com.alevel.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ua.com.alevel.exceptions.EntityExistException;
import ua.com.alevel.exceptions.EntityNotFoundException;
import ua.com.alevel.persistence.entity.News;
import ua.com.alevel.persistence.repository.NewsRepository;
import ua.com.alevel.service.NewsService;

import java.util.List;
import java.util.Objects;

@Service
public class NewsServiceImpl implements NewsService {

    private final NewsRepository newsRepository;

    private final Logger logger = LoggerFactory.getLogger(NewsServiceImpl.class);


    public NewsServiceImpl(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    @Override
    public News createNews(News news) {
        if (checkUniqueNews(news)) {
            logger.info("Creating news with uniq id: {}", news.getUniqId());
            News savedNews = newsRepository.save(news);
            logger.info("News created successfully with id: {}", savedNews.getId());
            return savedNews;
        } else {
            throw new EntityExistException("Such news already exists");
        }
    }

    @Override
    public List<News> findAllNews(PageRequest pageRequest) {
        return newsRepository.findAll(pageRequest).getContent();
    }

    @Override
    public List<News> findAllNews(PageRequest pageRequest, Specification<News> specification) {
        return newsRepository.findAll(specification, pageRequest).getContent();
    }

    @Override
    public void createNewsFromAPI(List<News> newsList) {
        for (News news : newsList) {
            try {
                createNews(news);
            } catch (EntityExistException ignored) { }
        }
    }

    @Override
    public List<String> findUniqPublisher() {
        return newsRepository.findDistinctByPublisher();
    }

    private Boolean checkUniqueNews(News news){
        return !newsRepository.existsByUniqId(news.getUniqId()) &&
                !newsRepository.existsBySourcePublisher(news.getSourcePublisher());
    }
}
