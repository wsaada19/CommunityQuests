package me.wonka01.ServerQuests.questcomponents.rewards;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class RewardManager {
    private static RewardManager instance;
    private HashMap<UUID, ArrayList<RewardEntry>> playerRewards = new HashMap<>();

    public static RewardManager getInstance() {
        if (instance == null) {
            instance = new RewardManager();
        }
        return instance;
    }

    public void addReward(UUID playerId, Reward reward) {
        if (playerRewards.containsKey(playerId)) {
            ArrayList<RewardEntry> rewards = playerRewards.get(playerId);
            rewards.add(new RewardEntry(reward, 1.0));
        } else {
            ArrayList<RewardEntry> rewards = new ArrayList<>();
            rewards.add(new RewardEntry(reward, 1.0));
            playerRewards.put(playerId, rewards);
        }
    }

    public ArrayList<RewardEntry> getRewards(UUID playerId) {
        if (playerRewards.containsKey(playerId)) {
            return playerRewards.get(playerId);
        }
        return new ArrayList<>();
    }

    public void removeRewards(UUID playerId) {
        if (playerRewards.containsKey(playerId)) {
            playerRewards.put(playerId, new ArrayList<>());
        }
    }

    public boolean hasRewards(UUID playerId) {
        return playerRewards.containsKey(playerId) && playerRewards.get(playerId).size() > 0;
    }

    public boolean giveRewardToPlayer(UUID playerId) {
        OfflinePlayer player = Bukkit.getOfflinePlayer(playerId);
        if (playerRewards.containsKey(playerId)) {
            ArrayList<RewardEntry> rewards = playerRewards.get(playerId);
            for (RewardEntry rewardEntry : rewards) {
                rewardEntry.getReward().giveRewardToPlayer(player, rewardEntry.getRatio());
            }
            playerRewards.remove(playerId);
            return true;
        }
        return false;
    }

    public void saveToJsonFile(File path) {
        JSONArray jsonArray = new JSONArray();
        for (HashMap.Entry<UUID, ArrayList<RewardEntry>> entry : playerRewards.entrySet()) {
            UUID playerId = entry.getKey();
            ArrayList<RewardEntry> rewards = entry.getValue();

            // Create a JSON object for each player
            JSONObject playerJson = new JSONObject();

            // Create a JSON array to hold reward entries for the player
            JSONArray rewardsJson = new JSONArray();

            // Iterate over the reward entries
            for (RewardEntry reward : rewards) {
                // Create a JSON object for each reward entry
                JSONObject rewardJson = new JSONObject();
                rewardJson.put("reward", reward.getReward().toJson());
                rewardJson.put("rewardRatio", reward.getRatio());

                // Add the reward object to the rewards array
                rewardsJson.add(rewardJson);
            }

            // Add the rewards array to the player object
            playerJson.put("rewards", rewardsJson);
            playerJson.put("playerId", playerId.toString());

            // Add the player object to the main JSON array
            jsonArray.add(playerJson);
        }

        try {
            FileWriter file = new FileWriter(path + "/rewardSave.json");
            file.write(jsonArray.toJSONString());
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void populateFromJsonFile(File path, Logger logger) {
        JSONParser parser = new JSONParser();
        File fullPath = new File(path + "/rewardSave.json");
        try {
            if (!fullPath.exists()) {
                return;
            }
            Object obj = parser.parse(new FileReader(fullPath));

            JSONArray playerRewardsArray = (JSONArray) obj;
            for (Object playerRewardsObj : playerRewardsArray) {
                JSONObject playerRewardsJson = (JSONObject) playerRewardsObj;
                String playerId = (String) playerRewardsJson.get("playerId");
                JSONArray rewards = (JSONArray) playerRewardsJson.get("rewards");

                ArrayList<RewardEntry> rewardsList = new ArrayList<>();
                for (Object reward : rewards) {
                    JSONObject jsonReward = (JSONObject) reward;
                    JSONObject rewardObj = (JSONObject) jsonReward.get("reward");
                    String type = (String) rewardObj.get("type");
                    double ratio = (double) jsonReward.get("rewardRatio");
                    if (type.equals("experience")) {
                        long amount = (long) rewardObj.get("amount");
                        RewardEntry rewardEntry = new RewardEntry(new ExperienceReward((int) amount), ratio);
                        rewardsList.add(rewardEntry);
                    } else if (type.equals("money")) {
                        double moneyAmount = (double) rewardObj.get("amount");
                        RewardEntry rewardEntry = new RewardEntry(new MoneyReward(moneyAmount), ratio);
                        rewardsList.add(rewardEntry);
                    } else if (type.equals("item")) {
                        String materialName = (String) rewardObj.get("material");
                        long count = (long) rewardObj.get("amount");
                        String displayName = (String) rewardObj.get("displayName");
                        RewardEntry rewardEntry = new RewardEntry(
                                new ItemReward((int) count, materialName, displayName),
                                ratio);
                        rewardsList.add(rewardEntry);
                    } else if (type.equals("command")) {
                        String command = (String) rewardObj.get("command");
                        rewardsList.add(new RewardEntry(new CommandReward(command), ratio));
                    } else if (type.equals("message")) {
                        String message = (String) rewardObj.get("message");
                        rewardsList.add(new RewardEntry(new RewardMessage(message), ratio));
                    }
                }
                this.playerRewards.put(UUID.fromString(playerId), rewardsList);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
