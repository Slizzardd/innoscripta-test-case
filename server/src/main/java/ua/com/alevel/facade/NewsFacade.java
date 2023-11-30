package ua.com.alevel.facade;

import ua.com.alevel.web.dto.requests.DataTableRequest;
import ua.com.alevel.web.dto.requests.NewsRequestDto;
import ua.com.alevel.web.dto.responses.NewsResponseDto;

import java.util.List;

public interface NewsFacade extends BaseFacade<NewsRequestDto, NewsResponseDto> {

    List<NewsResponseDto> findAllNews(DataTableRequest request, String authToken);

    List<String> findAllUniqPublisher();

}
