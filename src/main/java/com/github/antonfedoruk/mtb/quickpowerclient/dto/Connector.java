package com.github.antonfedoruk.mtb.quickpowerclient.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Connector {
    private ConnectorsType type;
    private ConnectorsStatus status;
}
