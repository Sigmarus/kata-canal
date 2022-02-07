package com.example.akkastreams.domain.repositories;

import com.example.akkastreams.domain.entities.Title;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TitleRepository extends JpaRepository<Title, String> {

    Optional<Title> findByOriginalTitle(String titleName);

    @Query(value = "select * from title" +
            " where tconst in (" +
            "   select title.tconst, count(episode.parent_tconst)" +
            "   from title right join episodes on (title.tconst = episodes.parent_tconst)" +
            "   where title.title_type like 'tvSeries'" +
            "   group by title.tconst" +
            "   order by 2 desc" +
            "   limit 10);",
            nativeQuery = true)
    List<Title> findAllByMaxEpisodeNumberLimit10();
}
