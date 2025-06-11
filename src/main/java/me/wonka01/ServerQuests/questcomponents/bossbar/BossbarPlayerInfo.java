package me.wonka01.ServerQuests.questcomponents.bossbar;

import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

// Class that manages the players that have hidden the bossbar
public class BossbarPlayerInfo {
    private static BossbarPlayerInfo instance;
    private List<UUID> playersToHideBossbar;

    private BossbarPlayerInfo() {
        playersToHideBossbar = new ArrayList<>();
    }

    public static BossbarPlayerInfo getInstance() {
        if (instance == null) {
            instance = new BossbarPlayerInfo();
        }
        return instance;
    }

    public void addPlayer(UUID playerId) {
        playersToHideBossbar.add(playerId);
    }

    public void removePlayer(UUID playerId) {
        playersToHideBossbar.remove(playerId);
    }

    public boolean hasPlayer(UUID playerId) {
        return playersToHideBossbar.contains(playerId);
    }

    public void togglebar(UUID playerId) {
        if (hasPlayer(playerId)) {
            removePlayer(playerId);
        } else {
            addPlayer(playerId);
        }
    }

    public void clear() {
        playersToHideBossbar.clear();
    }

    public void saveToJsonFile(File path) {
        // save the list of players to a json file
        JSONArray jsonArray = new JSONArray();
        playersToHideBossbar.forEach(playerId -> jsonArray.add(playerId.toString()));
        try {
            FileWriter file = new FileWriter(path + "/togglebar.json");
            file.write(jsonArray.toJSONString());
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadFromJsonFile(File path) {
        JSONParser parser = new JSONParser();
        File fullPath = new File(path + "/togglebar.json");
        try {
            if (!fullPath.exists()) {
                return;
            }
            JSONArray jsonArray = (JSONArray) parser.parse(new FileReader(fullPath));
            jsonArray.forEach(playerId -> playersToHideBossbar.add(UUID.fromString((String) playerId)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
