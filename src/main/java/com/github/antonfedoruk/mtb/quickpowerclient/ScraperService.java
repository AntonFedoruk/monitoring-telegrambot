package com.github.antonfedoruk.mtb.quickpowerclient;


import com.github.antonfedoruk.mtb.quickpowerclient.dto.Station;

import java.util.List;
/**
 * ScraperService to collect {@link Station} .
 */
public interface ScraperService {
    List<Station> extractStations();
}