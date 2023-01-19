package com.github.antonfedoruk.mtb.quickpowerclient.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Connector {
    private ConnectorsType type;
    private ConnectorsStatus status;
}
