package com.github.antonfedoruk.mtb.quickpowerclient;


import com.github.antonfedoruk.mtb.quickpowerclient.dto.Station;

import java.util.List;

public interface ScraperService {
    public List<Station> extractStations();
}