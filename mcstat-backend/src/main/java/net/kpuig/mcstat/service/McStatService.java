package net.kpuig.mcstat.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.stereotype.Service;

import me.dilley.MineStat;
import net.kpuig.mcstat.datamodel.McServer;
import net.kpuig.mcstat.datamodel.McServerStatus;

@Service
public class McStatService {
    private long lastUpdated = 0l;
    private List<McServerStatus> statuses;
    private List<McServer> serverList;
    
    public List<McServerStatus> getServerStatuses() {
        updateServerStatusesIfOutdated();
        return statuses;
    }

    private synchronized void updateServerStatusesIfOutdated() {
        if (System.currentTimeMillis() - lastUpdated > 3 * 1000) {
            updateServerStatuses();
            lastUpdated = System.currentTimeMillis();
        }
    }

    private synchronized void updateServerStatuses() {
        updateServerListFromFile();
        statuses = new ArrayList<>();
        for (McServer server : serverList) {
            MineStat stat = new MineStat(server.getDomain(), server.getPort());
            McServerStatus status = new McServerStatus();
            status.setDomain(server.getDomain());
            status.setPort(server.getPort());
            status.setOnline(stat.isServerUp());
            status.setPlayerCount(stat.getCurrentPlayers());
            status.setPlayerMax(stat.getMaximumPlayers());
            status.setServerName(stat.getMotd());
            status.setVersion(stat.getVersion());
            statuses.add(status);
        }
    }

    private final String fileSourceStr = "servers.txt";
    private synchronized void updateServerListFromFile() {
        File fileSource = new File(fileSourceStr);
        if (fileSource.exists() && fileSource.isFile()) {
            Scanner fileScanner;
            try {
                fileScanner = new Scanner(fileSource);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return;
            }
            
            serverList = new ArrayList<>();
            while (fileScanner.hasNextLine()) {
                String serverCandidate = fileScanner.nextLine();
                String[] serverCandidateComponents = serverCandidate.split(":");
                if (serverCandidateComponents.length == 1) {
                    McServer server = new McServer(serverCandidateComponents[0], 25565);
                    serverList.add(server);
                    continue;
                }
                if (serverCandidateComponents.length != 2) continue;

                McServer server = new McServer(serverCandidateComponents[0], Integer.parseInt(serverCandidateComponents[1]));
                serverList.add(server);
            }
            fileScanner.close();
        }
    }
}
