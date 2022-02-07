package com.example.akkastreams.api;

import com.example.akkastreams.domain.entities.Person;
import com.example.akkastreams.domain.entities.Title;
import com.example.akkastreams.usecase.ImdbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ImdbController {

    @Autowired
    private ImdbService imdbService;

    @GetMapping("/titles/{titleName}/people")
    public List<Person> getTitleRelatedPersons(@PathVariable String titleName) {
        return imdbService.findAllCrewForTitleName(titleName);
    }

    @GetMapping("/top10-longest-tvseries")
    public List<Title> getTop10TvSeriesWithGreatestNumberOfEpisodes() {
        return imdbService.find10FirstSeriesOrderedByNumberOfEpisodes();
    }
}
