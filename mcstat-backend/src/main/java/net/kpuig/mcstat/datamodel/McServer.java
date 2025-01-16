package net.kpuig.mcstat.datamodel;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class McServer {
    private String domain;
    private int port;
}
