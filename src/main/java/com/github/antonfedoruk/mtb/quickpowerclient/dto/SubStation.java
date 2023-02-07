package com.github.antonfedoruk.mtb.quickpowerclient.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SubStation {
    String name;
    StationModel model;
    StationFirmware firmware;
    SubStationStatus subStationStatus;
    List<Connector> connectors;
}
