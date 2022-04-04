package com.github.antonfedoruk.mtb.quickpowerclient;


import java.util.List;

public interface ScraperService {
    public List<SeleniumScraperServiceImpl.Station> extractStations();
}
