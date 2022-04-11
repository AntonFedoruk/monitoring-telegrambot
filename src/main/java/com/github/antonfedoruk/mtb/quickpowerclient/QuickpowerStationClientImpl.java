package com.github.antonfedoruk.mtb.quickpowerclient;

import com.github.antonfedoruk.mtb.quickpowerclient.dto.Station;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of the {@link QuickpowerStationClient} interface.
 */
@Service
public class QuickpowerStationClientImpl implements QuickpowerStationClient {
    private final ScraperService scraperService;

    public QuickpowerStationClientImpl(ScraperService scraperService) {
        this.scraperService = scraperService;
    }

    @Override
    public List<Station> getStationList() {
        return scraperService.extractStations();
    }

    @Override
    public Station getStationById(Integer id) {
        return scraperService.extractStations().stream().filter(station -> id.equals(station.getId())).findFirst().get();
    }
}