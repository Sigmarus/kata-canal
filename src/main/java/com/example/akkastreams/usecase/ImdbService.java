package com.example.akkastreams.usecase;

import com.example.akkastreams.domain.entities.Person;
import com.example.akkastreams.domain.entities.Title;
import com.example.akkastreams.domain.repositories.TitleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ImdbService {

    @Autowired
    private TitleRepository titleRepository;

    public List<Person> findAllTitleCrewForTitleName(String titleName) {
        Optional<Title> title = titleRepository.findByOriginalTitle(titleName);
        return title.map(Title::getCrew).orElse(Collections.EMPTY_LIST);
    }

    public List<Title> find10FirstSeriesOrderedByNumberOfEpisodes() {
        return titleRepository.findAllByMaxEpisodeNumberLimit10();
    }
}
