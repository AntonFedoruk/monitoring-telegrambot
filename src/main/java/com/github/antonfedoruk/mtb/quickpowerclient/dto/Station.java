package com.github.antonfedoruk.mtb.quickpowerclient.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.util.List;

/**
 * Station information.
 */
@Data
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Station {
    Integer id;
    String name;
    String address;
    StationStatus stationStatus;
    List<SubStation> subStations;
}
