package net.kpuig.mcstat.datamodel;

import lombok.Data;

@Data
public class McServerStatus {
    private String serverName;
    private String domain;
    private int port;
    private boolean online;
    private String version;
    private int playerCount;
    private int playerMax;
}
