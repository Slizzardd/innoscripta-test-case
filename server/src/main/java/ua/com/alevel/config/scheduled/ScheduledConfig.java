package ua.com.alevel.config.scheduled;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import ua.com.alevel.persistence.entity.News;
import ua.com.alevel.service.NewsService;
import ua.com.alevel.utils.NewsUtil;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableScheduling
public class ScheduledConfig {

    private final NewsService newsService;

    public ScheduledConfig(NewsService newsService) {
        this.newsService = newsService;
    }

//    @Scheduled(cron = "0 0/30 * * * *")
    @Scheduled(fixedDelay = 150000)
    public void updateNews() {
        List<News> newsList = new ArrayList<>();
        newsList.addAll(NewsUtil.getLatestNewsFromNewsApi());
        newsList.addAll(NewsUtil.getLatestNewsFromNYT());
        newsList.addAll(NewsUtil.getLatestNewsFromTheGuardian());
        newsService.createNewsFromAPI(newsList);
    }
}
