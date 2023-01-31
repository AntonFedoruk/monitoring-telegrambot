package com.github.antonfedoruk.mtb.quickpowerclient;

import com.github.antonfedoruk.mtb.quickpowerclient.dto.Station;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import java.util.List;
import java.util.NoSuchElementException;

@DisplayName("Unit-level testing for QuickpowerStationClient")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class QuickpowerStationClientTest {

    ScraperService scraperService;
    QuickpowerStationClient quickpowerStationClient;

    @BeforeEach
    public void init() {
        scraperService = Mockito.mock(ScraperService.class);
        quickpowerStationClient = new QuickpowerStationClientImpl(scraperService);
    }

    @Test
    @DisplayName("Should throw NoSuchElementException")
    void shouldThrowNoSuchElementException() {
        // given when
        Station station1 = new Station();
        station1.setId(1);
        Station station2 = new Station();
        station2.setId(2);
        Station station3 = new Station();
        station3.setId(3);

        List<Station> stations = List.of(station1, station2, station3);

        Mockito.when(scraperService.extractStations()).thenReturn(stations);
        // then
        Assertions.assertThrows(NoSuchElementException.class, () -> quickpowerStationClient.getStationByIdOrThrowNoSuchElementException(5));
    }

    @Test
    @DisplayName("Should return station by id")
    void shouldReturnStationById() {
        // given
        Station station1 = new Station();
        station1.setId(1);
        Station station2 = new Station();
        station2.setId(2);
        Station station3 = new Station();
        station3.setId(3);

        List<Station> stations = List.of(station1, station2, station3);

        Mockito.when(scraperService.extractStations()).thenReturn(stations);
        // when
        int expectedId = 2;
        Station stationById = quickpowerStationClient.getStationByIdOrThrowNoSuchElementException(expectedId);
        // then
//        Assertions.assertNotNull(stationById);
        Assertions.assertEquals(expectedId, stationById.getId());
    }

    @Test
    @DisplayName("Should properly get station count")
    void shouldProperlyGetStationCount() {
        // given
        Station station1 = new Station();
        Station station2 = new Station();
        Station station3 = new Station();

        List<Station> stations = List.of(station1, station2, station3);

        Mockito.when(scraperService.extractStations()).thenReturn(stations);
        // when
        int stationCount = scraperService.extractStations().size();
        // then
        Assertions.assertEquals(3, stationCount);
    }
}
