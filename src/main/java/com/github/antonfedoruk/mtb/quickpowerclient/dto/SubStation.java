package com.github.antonfedoruk.mtb.quickpowerclient.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SubStation {
    String name;
    StationModel model;
    StationFirmware firmware;
    SubStationStatus subStationStatus;
    List<Connector> connectors;
}
