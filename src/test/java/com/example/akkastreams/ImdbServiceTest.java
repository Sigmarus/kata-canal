package com.example.akkastreams;

import com.example.akkastreams.domain.entities.*;
import com.example.akkastreams.domain.repositories.TitleRepository;
import com.example.akkastreams.usecase.ImdbService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

@SpringBootTest
public class ImdbServiceTest {

    private final TitleRepository titleRepositoryMock = mock(TitleRepository.class);

    @InjectMocks
    private ImdbService imdbService;

    @Test
    void givenANonExistingTitle_whenFindingCrewForTheTitle_thenShouldReturnAnEmptyList() {
        List<Person> expected = Collections.EMPTY_LIST;

        Mockito.when(titleRepositoryMock.findByOriginalTitle("toto"))
                .thenReturn(Optional.empty());

        List<Person> result = imdbService.findAllTitleCrewForTitleName("toto");

        assertEquals(result, expected);
    }

    @Test
    void givenATitleWithEmptyCrew_whenFindingCrewForTheTitle_thenShouldReturnAnEmptyList() {
        Title title = getTMovies().get(0);

        List<Person> expected = Collections.EMPTY_LIST;

        Mockito.when(titleRepositoryMock.findByOriginalTitle("toto"))
                .thenReturn(Optional.of(title));

        List<Person> result = imdbService.findAllTitleCrewForTitleName("toto");

        assertEquals(result, expected);
    }

    @Test
    void givenATitleWithACrew_whenFindingCrewForTheTitle_thenShouldReturnTheListPersonsInTheCrew() {
        Person p1 = new Person("nm0000001", "Toto", 1999, null, "barber,haircutter", List.of(), List.of(), List.of());
        Title title = getTMovies().get(0);
        Crew crew = new Crew(new CrewKey(p1.getId(), title.getId()), title, p1, CrewType.DIRECTOR);
        title.setCrew(List.of(crew));

        List<Person> expected = title.getCrew().stream().map(Crew::getPerson).collect(Collectors.toList());

        Mockito.when(titleRepositoryMock.findByOriginalTitle("toto"))
                .thenReturn(Optional.of(title));

        List<Person> result = imdbService.findAllTitleCrewForTitleName("toto");

        assertEquals(result, expected);
    }

    @Test
    void givenNoTitlesOfTypeTvSerie_whenLookingForThe10TvSeriesWithMaxNumberOfEpisodes_thenShouldReturnAnEmptyList() {
        List<Title> expected = Collections.EMPTY_LIST;

        Mockito.when(titleRepositoryMock.findAllByMaxEpisodeNumberLimit10())
                .thenReturn(Collections.EMPTY_LIST);

        List<Title> result = imdbService.find10FirstSeriesOrderedByNumberOfEpisodes();

        assertEquals(result, expected);
    }

    @Test
    void given10TitlesOrLessOfTypeTvSerie_whenLookingForThe10TvSeriesWithMaxNumberOfEpisodes_thenShouldReturnTheOrderedTitlesList() {
        //TODO Not yet implemented
    }

    @Test
    void givenMoreThan10TitlesOfTypeTvSerie_whenLookingForThe10TvSeriesWithMaxNumberOfEpisodes_thenShouldReturnTheProperTitlesList() {
        //TODO Not yet implemented
    }

    @Test
    void givenTitlesOfTypeTvSerieWithSameEpisodeNumber_whenLookingForThe10TvSeriesWithMaxNumberOfEpisodes_thenShouldOrderTheTitlesByAverageRatings() {
        //TODO Not yet implemented
    }

    private List<Title> getTitles() {
        return List.of(
                new Title("tt0000001", "movie", "toto", "toto", false, 2010, 2012, 1, 4.5, 34, List.of(), List.of(), List.of(), List.of()),
                new Title("tt0000002", "tvSeries", "Voice of Firestone Televues", "Voice of Firestone Televues", false, 1943, 1947, 1, 4.8, 34, List.of(), List.of(), List.of(), List.of()),
                new Title("tt0000003", "tvSeries", "Les Misérables", "Les misérables", false, 1934, 1934, 1, 4.5, 34, List.of(), List.of(), List.of(), List.of()),
                new Title("tt0000004", "tvSeries", "Les Misérables", "Les misérables", false, 2010, 2012, 1, 4.5, 34, List.of(), List.of(), List.of(), List.of()),
                new Title("tt0000005", "tvSeries", "Les Misérables", "Les misérables", false, 2010, 2012, 1, 4.5, 34, List.of(), List.of(), List.of(), List.of()),
                new Title("tt0000006", "tvSeries", "Les Misérables", "Les misérables", false, 2010, 2012, 1, 4.5, 34, List.of(), List.of(), List.of(), List.of()),
                new Title("tt0000007", "tvSeries", "Les Misérables", "Les misérables", false, 2010, 2012, 1, 4.5, 34, List.of(), List.of(), List.of(), List.of()),
                new Title("tt0000008", "tvSeries", "Les Misérables", "Les misérables", false, 2010, 2012, 1, 4.5, 34, List.of(), List.of(), List.of(), List.of()),
                new Title("tt0000009", "tvSeries", "Les Misérables", "Les misérables", false, 2010, 2012, 1, 4.5, 34, List.of(), List.of(), List.of(), List.of()),
                new Title("tt0000010", "tvSeries", "Les Misérables", "Les misérables", false, 2010, 2012, 1, 4.5, 34, List.of(), List.of(), List.of(), List.of()),
                new Title("tt0000011", "tvSeries", "Les Misérables", "Les misérables", false, 2010, 2012, 1, 4.5, 34, List.of(), List.of(), List.of(), List.of()),
                new Title("tt0000012", "tvSeries", "Les Misérables", "Les misérables", false, 2010, 2012, 1, 4.5, 34, List.of(), List.of(), List.of(), List.of())
        );
    }

    private List<Title> getTMovies() {
        return getTitles().stream()
                .filter(t -> t.getType().equalsIgnoreCase("movie"))
                .collect(Collectors.toList());
    }

    private List<Title> getTvSeries() {
        return getTitles().stream()
                .filter(t -> t.getType().equalsIgnoreCase("tvseries"))
                .collect(Collectors.toList());
    }
}
