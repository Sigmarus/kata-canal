package com.example.akkastreams;

import com.example.akkastreams.domain.entities.*;
import com.example.akkastreams.domain.repositories.TitleRepository;
import com.example.akkastreams.usecase.ImdbService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
        Title title = getTitles().get(1);

        List<Person> expected = title.getCrew().stream()
                .map(Crew::getPerson)
                .collect(Collectors.toList());

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
        List<Title> tvSeries3 = getTvSeries().subList(0, 3);

        List<Title> expected = tvSeries3.stream()
                .sorted(Comparator.comparingInt(t -> t.getEpisodes().size()))
                .collect(Collectors.toList());

        Mockito.when(titleRepositoryMock.findAllByMaxEpisodeNumberLimit10())
                .thenReturn(tvSeries3);

        List<Title> result = imdbService.find10FirstSeriesOrderedByNumberOfEpisodes();

        assertEquals(result, expected);
    }

    @Test
    void givenMoreThan10TitlesOfTypeTvSerie_whenLookingForThe10TvSeriesWithMaxNumberOfEpisodes_thenShouldReturnTheProperTitlesList() {
        //FIXME not relevant because depends on a sql query
        List<Title> tvSeriesAll = getTvSeries();

        List<Title> expected = getTvSeries().stream()
                .sorted((t1, t2) -> Integer.compare(t2.getEpisodes().size(), t1.getEpisodes().size()))
                .collect(Collectors.toList());

        Mockito.when(titleRepositoryMock.findAllByMaxEpisodeNumberLimit10())
                .thenReturn(tvSeriesAll);

        List<Title> result = imdbService.find10FirstSeriesOrderedByNumberOfEpisodes();

        assertEquals(result, expected);
    }

    @Test
    void givenTitlesOfTypeTvSerieWithSameEpisodeNumber_whenLookingForThe10TvSeriesWithMaxNumberOfEpisodes_thenShouldOrderTheTitlesByAverageRatings() {
        List<Title> tvSeriesAll = getTvSeries().stream().filter(t-> t.getEpisodes().size() == 4).collect(Collectors.toList());

        List<Title> expected = tvSeriesAll.stream()
                .sorted((t1, t2) -> -Double.compare(t2.getAverageRatings(), t1.getAverageRatings()))
                .collect(Collectors.toList());

        Mockito.when(titleRepositoryMock.findAllByMaxEpisodeNumberLimit10())
                .thenReturn(tvSeriesAll);

        List<Title> result = imdbService.find10FirstSeriesOrderedByNumberOfEpisodes();

        assertTrue(tvSeriesAll.size() > 1);
        assertEquals(result, expected);
    }


    private Map<Integer, List<Episode>> getEpisodes() {
        return Map.of(
                0,
                List.of(
                        new Episode("tt4200001", null, 1, 1)
                ),
                1,
                List.of(
                        new Episode("tt4200011", null, 1, 1),
                        new Episode("tt4200012", null, 1, 2)
                ),
                2,
                List.of(
                        new Episode("tt4200021", null, 1, 1),
                        new Episode("tt4200022", null, 1, 2),
                        new Episode("tt4200023", null, 1, 3)
                ),
                3,
                List.of(
                        new Episode("tt4200031", null, 1, 1),
                        new Episode("tt4200032", null, 1, 2),
                        new Episode("tt4200033", null, 1, 3),
                        new Episode("tt4200034", null, 2, 1)
                ),
                4,
                List.of(
                        new Episode("tt4200041", null, 1, 1),
                        new Episode("tt4200042", null, 1, 2),
                        new Episode("tt4200043", null, 1, 3),
                        new Episode("tt4200044", null, 1, 4)
                ),
                5,
                List.of(
                        new Episode("tt4200051", null, 1, 1),
                        new Episode("tt4200052", null, 1, 2),
                        new Episode("tt4200053", null, 1, 3),
                        new Episode("tt4200054", null, 1, 4),
                        new Episode("tt4200054", null, 1, 5)
                ),
                6,
                List.of(
                        new Episode("tt4200051", null, 1, 1),
                        new Episode("tt4200052", null, 1, 2),
                        new Episode("tt4200053", null, 1, 3),
                        new Episode("tt4200054", null, 1, 4),
                        new Episode("tt4200054", null, 1, 5),
                        new Episode("tt4200054", null, 1, 6),
                        new Episode("tt4200054", null, 1, 7),
                        new Episode("tt4200054", null, 1, 8)
                )
                //TODO fill with episodes to be able to get a list of top 10 series
        );
    }

    private List<Person> getPersons() {
        return List.of(
                new Person("nm0000001", "Toto", 1999, null, "", List.of(), List.of(), List.of()),
                new Person("nm0000002", "Tata", 1999, null, "", List.of(), List.of(), List.of())
        );
    }

    private List<Crew> getCrew() {
        List<Person> persons = getPersons();
        //cannot getTitles() because of infinite cycle
        return List.of(
                new Crew(new CrewKey("tt0000001", persons.get(0).getId()), null, persons.get(0), CrewType.DIRECTOR),
                new Crew(new CrewKey("tt0000001", persons.get(1).getId()), null, persons.get(1), CrewType.WRITER)
        );
    }

    private List<Title> getTitles() {
        Map<Integer, List<Episode>> episodes = getEpisodes();
        return List.of(
                new Title("tt0000000", "movie", "toto", "toto", false, 2010, 2012, 1, 4.5, 34, List.of(), List.of(), List.of(), List.of()),
                new Title("tt0000001", "movie", "Bohemios", "Bohemios", false, 2010, 2012, 1, 4.5, 34, getCrew().stream().map(Crew::getPerson).collect(Collectors.toList()), List.of(), List.of(), List.of()),
                new Title("tt0000002", "tvSeries", "Voice of Firestone Televues", "Voice of Firestone Televues", false, 2010, 2012, 1, 4.8, 34, List.of(), List.of(), List.of(), episodes.get(0)),
                new Title("tt0000003", "tvSeries", "Les Misérables", "Les misérables", false, 2010, 2012, 1, 4.5, 34, List.of(), List.of(), List.of(), episodes.get(1)),
                new Title("tt0000004", "tvSeries", "The German Weekly Review", "Die Deutsche Wochenschau", false, 2010, 2012, 1, 4.5, 34, List.of(), List.of(), List.of(), episodes.get(2)),
                new Title("tt0000005", "tvSeries", "You Are an Artist", "You Are an Artist", false, 2010, 2012, 1, 3.8, 34, List.of(), List.of(), List.of(), episodes.get(3)),
                new Title("tt0000006", "tvSeries", "Americana", "Americana", false, 2010, 2012, 1, 4.5, 34, List.of(), List.of(), List.of(), episodes.get(4)),
                new Title("tt0000007", "tvSeries", "Birthday Party", "Birthday Party", false, 2010, 2012, 1, 4.5, 34, List.of(), List.of(), List.of(), episodes.get(5)),
                new Title("tt0000008", "tvSeries", "The Borden Show", "The Borden Show", false, 2010, 2012, 1, 4.5, 34, List.of(), List.of(), List.of(), episodes.get(6)),
                new Title("tt0000009", "tvSeries", "Kraft Theatre", "Kraft Television Theatre", false, 2010, 2012, 1, 4.5, 34, List.of(), List.of(), List.of(), List.of()),
                new Title("tt0000010", "tvSeries", "Party Line", "Party Line", false, 2010, 2012, 1, 4.5, 34, List.of(), List.of(), List.of(), List.of()),
                new Title("tt0000011", "tvSeries", "Public Prosecutor", "Public Prosecutor", false, 2010, 2012, 1, 4.5, 34, List.of(), List.of(), List.of(), List.of()),
                new Title("tt0000012", "tvSeries", "Actor's Studio", "Actor's Studio", false, 2010, 2012, 1, 4.5, 34, List.of(), List.of(), List.of(), List.of()),
                new Title("tt0000013", "tvSeries", "The Adventures of Oky Doky", "The Adventures of Oky Doky", false, 2010, 2012, 1, 4.5, 34, List.of(), List.of(), List.of(), List.of())
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
