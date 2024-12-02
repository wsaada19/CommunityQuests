package me.wonka01.ServerQuests.questcomponents.players;

import me.knighthat.apis.utils.Colorization;
import me.knighthat.apis.utils.Utils;
import me.wonka01.ServerQuests.ServerQuests;
import me.wonka01.ServerQuests.questcomponents.rewards.RewardManager;
import me.wonka01.ServerQuests.questcomponents.rewards.RewardMessage;
import me.wonka01.ServerQuests.questcomponents.rewards.types.Reward;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.google.gson.Gson;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

public class BasePlayerComponent implements Colorization {
    @Setter
    @Getter
    private static int leaderBoardSize = 5;

    @Getter
    private final Map<UUID, PlayerData> playerMap;
    private final Map<String, ArrayList<Reward>> rankedRewards;
    private final int rewardsLimit;

    public BasePlayerComponent(int rewardLimit,
            Map<String, ArrayList<Reward>> rankedRewards) {
        this.playerMap = new TreeMap<>();
        this.rewardsLimit = rewardLimit;
        this.rankedRewards = rankedRewards;
    }

    public BasePlayerComponent(Map<UUID, PlayerData> map, int rewardLimit,
            Map<String, ArrayList<Reward>> rankedRewards) {
        this.playerMap = map;
        this.rewardsLimit = rewardLimit;
        this.rankedRewards = rankedRewards;
    }

    public void savePlayerAction(Player player, double count, Integer objectiveId) {
        if (playerMap.containsKey(player.getUniqueId())) {
            PlayerData playerData = playerMap.get(player.getUniqueId());
            playerData.increaseContribution(count, objectiveId);

            // update display name so ranks and such are handled better
            playerData.setName(player.getDisplayName());
        } else {
            PlayerData playerData = new PlayerData(player.getDisplayName(), player.getUniqueId());
            playerData.increaseContribution(count, objectiveId);
            playerMap.put(player.getUniqueId(), playerData);
        }
    }

    public String getLeaderString() {
        if (leaderBoardSize <= 0 || this.playerMap.size() < 1) {
            return "";
        }

        ServerQuests plugin = JavaPlugin.getPlugin(ServerQuests.class);
        StringBuilder result = new StringBuilder(plugin.messages().string("topContributorsTitle"));
        TreeMap<UUID, PlayerData> map = new TreeMap<>(new SortByContributions(this.playerMap));
        map.putAll(this.playerMap);

        int count = 1;
        for (UUID key : map.keySet()) {
            if (count == leaderBoardSize + 1) {
                break;
            }

            result.append("\n &f#");
            result.append(count);
            result.append(") &a");
            result.append(map.get(key).getName());
            result.append(" &7- &f");
            result.append(Utils.decimalToString(map.get(key).getAmountContributed()));

            count++;
        }
        return color(result.toString());
    }

    public void sendLeaderString() {
        Bukkit.getServer().broadcastMessage(getLeaderString());
    }

    public PlayerData getTopPlayer() {
        TreeMap<UUID, PlayerData> map = new TreeMap<>(new SortByContributions(this.playerMap));
        map.putAll(this.playerMap);

        for (UUID key : map.keySet()) {
            return map.get(key);
        }
        return null;
    }

    public ArrayList<PlayerData> getTopPlayers(int amount) {
        TreeMap<UUID, PlayerData> map = new TreeMap<>(new SortByContributions(this.playerMap));
        map.putAll(this.playerMap);
        ArrayList<PlayerData> top = new ArrayList<>();
        int count = 0;
        for (UUID key : map.keySet()) {
            top.add(map.get(key));
            count++;
            if (count > amount - 1) {
                break;
            }
        }
        return top;
    }

    public double getAmountContributed(Player player) {
        if (playerMap.containsKey(player.getUniqueId())) {
            return playerMap.get(player.getUniqueId()).getAmountContributed();
        }
        return 0;
    }

    public int getPlayerRank(Player player) {
        TreeMap<UUID, PlayerData> map = new TreeMap<>(new SortByContributions(this.playerMap));
        map.putAll(this.playerMap);
        int rank = 1;
        for (UUID key : map.keySet()) {
            if (key.equals(player.getUniqueId())) {
                return rank;
            }
            rank++;
        }

        return 0;
    }

    public double getAmountContributedByObjectiveId(Player player, int id) {
        if (playerMap.containsKey(player.getUniqueId())) {
            return playerMap.get(player.getUniqueId()).getAmountContributedByObjectiveId(id);
        }
        return 0;
    }

    public JSONArray toJSONArray() {
        JSONArray jArray = new JSONArray();
        for (UUID key : playerMap.keySet()) {
            JSONObject jsonObject = new JSONObject();
            Gson gson = new Gson();
            String jsonString = gson.toJson(playerMap.get(key).getObjectiveContributions());
            jsonObject.put(key.toString(), jsonString);
            jsonObject.put("name", playerMap.get(key).getName());
            jArray.add(jsonObject);
        }
        return jArray;
    }

    public void giveOutRewards(double questGoal, String completeMessage) {
        List<PlayerData> players;
        if (rewardsLimit > 0) {
            players = getTopPlayers(rewardsLimit);
        } else {
            players = getTopPlayers(playerMap.size());
        }
        for (PlayerData playerData : players) {
            double playerContributionRatio;
            double playerContribution = playerData.getAmountContributed();
            if (questGoal > 0) {
                playerContributionRatio = playerContribution / (double) questGoal;
            } else {
                playerContributionRatio = playerContribution / getTopPlayer().getAmountContributed();
            }

            String playerRank = "" + (players.indexOf(playerData) + 1);

            List<Reward> rankedReward = rankedRewards.get(playerRank);

            if (rankedReward == null) {
                rankedReward = rankedRewards.get("*");
            } else {
                playerContributionRatio = 1.0; // Dont use ratio if player is in a ranked reward group
            }

            if (rankedReward == null) {
                continue;
            }

            OfflinePlayer player = Bukkit.getServer().getOfflinePlayer(playerData.getUuid());
            RewardManager rewardManager = RewardManager.getInstance();

            if (player.isOnline()) {
                Player onlinePlayer = (Player) player;
                if (rankedReward.size() > 0) {
                    // ServerQuests plugin = JavaPlugin.getPlugin(ServerQuests.class);
                    // String rewardsMessage = plugin.messages().message("rewardsMessage");
                    onlinePlayer.sendMessage("rewards size " + rankedReward.size());
                    for (Reward reward : rankedReward) {
                        reward.giveRewardToPlayer(onlinePlayer, playerContributionRatio);
                    }
                }
            } else {
                RewardMessage rewardMessage = new RewardMessage(completeMessage);
                rewardManager.addReward(player.getUniqueId(), rewardMessage, playerContributionRatio);
                for (Reward reward : rankedReward) {
                    rewardManager.addReward(player.getUniqueId(), reward, playerContributionRatio);
                }
            }
        }
    }
}
