package me.wonka01.ServerQuests.configuration;

import me.wonka01.ServerQuests.ServerQuests;
import me.wonka01.ServerQuests.questcomponents.players.PlayerData;

import org.bukkit.Material;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;

public class QuestHistoryManager {
    private final File historyFile;
    private final Logger logger;

    // TODO: move to config
    private static final int MAX_HISTORY_ENTRIES = 50;

    public QuestHistoryManager(ServerQuests plugin, File dataFolder) {
        this.historyFile = new File(dataFolder, "questHistory.json");
        this.logger = plugin.getLogger();
        createHistoryFileIfNotExists();
    }

    private void createHistoryFileIfNotExists() {
        if (!historyFile.exists()) {
            try {
                historyFile.createNewFile();
                saveToFile(new JSONArray());
            } catch (IOException e) {
                logger.warning("Failed to create quest history file: " + e.getMessage());
            }
        }
    }

    public void saveCompletedQuest(String questId, String questName, Map<UUID, PlayerData> playerData,
            Material displayItem) {
        JSONArray historyArray = loadHistoryArray();

        JSONObject questEntry = new JSONObject();
        questEntry.put("questId", questId);
        questEntry.put("questName", questName);
        questEntry.put("completionTime", System.currentTimeMillis());
        questEntry.put("displayItem", displayItem.toString());

        JSONArray topPlayers = new JSONArray();
        playerData.entrySet().stream()
                .sorted((e1, e2) -> {
                    double total1 = e1.getValue().getAmountContributed();
                    double total2 = e2.getValue().getAmountContributed();
                    return Double.compare(total2, total1);
                })
                .limit(10) // TODO - read from leaderboard size config
                .forEach(entry -> {
                    JSONObject playerInfo = new JSONObject();
                    playerInfo.put("uuid", entry.getKey().toString());
                    playerInfo.put("name", entry.getValue().getName());
                    playerInfo.put("contribution", entry.getValue().getAmountContributed());
                    topPlayers.add(playerInfo);
                });

        questEntry.put("topContributors", topPlayers);

        historyArray.add(0, questEntry);
        while (historyArray.size() > MAX_HISTORY_ENTRIES) {
            historyArray.remove(historyArray.size() - 1);
        }

        saveToFile(historyArray);
    }

    public List<Map<String, Object>> getPlayerQuestHistory(UUID playerId) {
        List<Map<String, Object>> playerHistory = new ArrayList<>();
        JSONArray historyArray = loadHistoryArray();

        for (Object entry : historyArray) {
            JSONObject questEntry = (JSONObject) entry;
            JSONArray topContributors = (JSONArray) questEntry.get("topContributors");

            for (Object contributor : topContributors) {
                JSONObject playerInfo = (JSONObject) contributor;
                if (playerInfo.get("uuid").equals(playerId.toString())) {
                    Map<String, Object> historyEntry = new HashMap<>();
                    historyEntry.put("questId", questEntry.get("questId"));
                    historyEntry.put("questName", questEntry.get("questName"));
                    historyEntry.put("completionTime", questEntry.get("completionTime"));
                    historyEntry.put("contribution", playerInfo.get("contribution"));
                    playerHistory.add(historyEntry);
                    break;
                }
            }
        }

        return playerHistory;
    }

    public List<Map<String, Object>> getRecentQuests(int limit) {
        List<Map<String, Object>> recentQuests = new ArrayList<>();
        JSONArray historyArray = loadHistoryArray();

        int count = 0;
        for (Object entry : historyArray) {
            if (count >= limit)
                break;

            JSONObject questEntry = (JSONObject) entry;
            Map<String, Object> questInfo = new HashMap<>();
            questInfo.put("questId", questEntry.get("questId"));
            questInfo.put("questName", questEntry.get("questName"));
            questInfo.put("completionTime", questEntry.get("completionTime"));
            questInfo.put("topContributors", questEntry.get("topContributors"));
            questInfo.put("displayItem", questEntry.get("displayItem"));

            recentQuests.add(questInfo);
            count++;
        }

        return recentQuests;
    }

    private JSONArray loadHistoryArray() {
        if (!historyFile.exists()) {
            return new JSONArray();
        }

        try (FileReader reader = new FileReader(historyFile)) {
            JSONParser parser = new JSONParser();
            return (JSONArray) parser.parse(reader);
        } catch (Exception e) {
            logger.warning("Failed to load quest history: " + e.getMessage());
            return new JSONArray();
        }
    }

    private void saveToFile(JSONArray historyArray) {
        try (FileWriter writer = new FileWriter(historyFile)) {
            writer.write(historyArray.toJSONString());
        } catch (IOException e) {
            logger.warning("Failed to save quest history: " + e.getMessage());
        }
    }
}