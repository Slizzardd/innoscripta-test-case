package ua.com.alevel.persistence.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ua.com.alevel.persistence.entity.News;

import java.util.List;

@Repository
public interface NewsRepository extends BaseRepository<News> {

    Boolean existsByUniqId(String uniqId);

    Boolean existsBySourcePublisher(String sourcePublisher);

    @Query("SELECT DISTINCT e.publisher FROM News e")
    List<String> findDistinctByPublisher();
}
