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
import java.util.Map;
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


    private Map<Title, List<Episode>> getEpisodes() {
        List<Title> tvSeries = getTvSeries();
        return Map.of(
                tvSeries.get(0),
                List.of(
                        new Episode("tt4200001", tvSeries.get(0), 1, 1)
                ),
                tvSeries.get(1),
                List.of(
                        new Episode("tt4200011", tvSeries.get(1), 1, 1),
                        new Episode("tt4200012", tvSeries.get(1), 1, 2)
                ),
                tvSeries.get(2),
                List.of(
                        new Episode("tt4200021", tvSeries.get(2), 1, 1),
                        new Episode("tt4200022", tvSeries.get(2), 1, 2),
                        new Episode("tt4200023", tvSeries.get(2), 1, 3)
                ),
                tvSeries.get(3),
                List.of(
                        new Episode("tt4200031", tvSeries.get(3), 1, 1),
                        new Episode("tt4200032", tvSeries.get(3), 1, 2),
                        new Episode("tt4200033", tvSeries.get(3), 1, 3),
                        new Episode("tt4200034", tvSeries.get(3), 2, 1)
                ),
                tvSeries.get(4),
                List.of(
                        new Episode("tt4200041", tvSeries.get(4), 1, 1),
                        new Episode("tt4200042", tvSeries.get(4), 1, 2),
                        new Episode("tt4200043", tvSeries.get(4), 1, 3),
                        new Episode("tt4200044", tvSeries.get(4), 1, 4)
                ),
                tvSeries.get(5),
                List.of(
                        new Episode("tt4200051", tvSeries.get(5), 1, 1),
                        new Episode("tt4200052", tvSeries.get(5), 1, 2),
                        new Episode("tt4200053", tvSeries.get(5), 1, 3),
                        new Episode("tt4200054", tvSeries.get(5), 1, 4),
                        new Episode("tt4200054", tvSeries.get(5), 1, 5)
                ),
                tvSeries.get(6),
                List.of(
                        new Episode("tt4200051", tvSeries.get(6), 1, 1),
                        new Episode("tt4200052", tvSeries.get(6), 1, 2),
                        new Episode("tt4200053", tvSeries.get(6), 1, 3),
                        new Episode("tt4200054", tvSeries.get(6), 1, 4),
                        new Episode("tt4200054", tvSeries.get(6), 1, 5),
                        new Episode("tt4200054", tvSeries.get(6), 1, 6),
                        new Episode("tt4200054", tvSeries.get(6), 1, 7),
                        new Episode("tt4200054", tvSeries.get(6), 1, 8)
                )
                //TODO fill with episodes to be able to get a list of top 10 series
        );
    }

    private List<Title> getTitles() {
        return List.of(
                new Title("tt0000001", "movie", "toto", "toto", false, 2010, 2012, 1, 4.5, 34, List.of(), List.of(), List.of(), List.of()),
                new Title("tt0000002", "tvSeries", "Voice of Firestone Televues", "Voice of Firestone Televues", false, 2010, 2012, 1, 4.8, 34, List.of(), List.of(), List.of(), List.of()),
                new Title("tt0000003", "tvSeries", "Les Misérables", "Les misérables", false, 2010, 2012, 1, 4.5, 34, List.of(), List.of(), List.of(), List.of()),
                new Title("tt0000004", "tvSeries", "The German Weekly Review", "Die Deutsche Wochenschau", false, 2010, 2012, 1, 4.5, 34, List.of(), List.of(), List.of(), List.of()),
                new Title("tt0000005", "tvSeries", "Les Misérables", "Les misérables", false, 2010, 2012, 1, 4.5, 34, List.of(), List.of(), List.of(), List.of()),
                new Title("tt0000006", "tvSeries", "Les Misérables", "Les misérables", false, 2010, 2012, 1, 4.5, 34, List.of(), List.of(), List.of(), List.of()),
                new Title("tt0000007", "tvSeries", "Les Misérables", "Les misérables", false, 2010, 2012, 1, 4.5, 34, List.of(), List.of(), List.of(), List.of()),
                new Title("tt0000008", "tvSeries", "Les Misérables", "Les misérables", false, 2010, 2012, 1, 4.5, 34, List.of(), List.of(), List.of(), List.of()),
                new Title("tt0000009", "tvSeries", "Les Misérables", "Les misérables", false, 2010, 2012, 1, 4.5, 34, List.of(), List.of(), List.of(), List.of()),
                new Title("tt0000010", "tvSeries", "Les Misérables", "Les misérables", false, 2010, 2012, 1, 4.5, 34, List.of(), List.of(), List.of(), List.of()),
                new Title("tt0000011", "tvSeries", "Les Misérables", "Les misérables", false, 2010, 2012, 1, 4.5, 34, List.of(), List.of(), List.of(), List.of()),
                new Title("tt0000012", "tvSeries", "Les Misérables", "Les misérables", false, 2010, 2012, 1, 4.5, 34, List.of(), List.of(), List.of(), List.of())
                //TODO complete the data with episodes
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
