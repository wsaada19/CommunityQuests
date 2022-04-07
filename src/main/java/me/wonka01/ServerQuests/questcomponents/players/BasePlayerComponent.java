package me.wonka01.ServerQuests.questcomponents.players;

import me.wonka01.ServerQuests.configuration.messages.LanguageConfig;
import me.wonka01.ServerQuests.questcomponents.rewards.Reward;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

public class BasePlayerComponent {

    private static int leaderBoardSize = 5;

    private Map<UUID, PlayerData> playerMap;
    private ArrayList<Reward> rewardsList;

    public BasePlayerComponent(ArrayList<Reward> rewardsList) {
        this.rewardsList = rewardsList;
        this.playerMap = new TreeMap<>();
    }

    public BasePlayerComponent(ArrayList<Reward> rewardsList, Map<UUID, PlayerData> map) {
        this.rewardsList = rewardsList;
        this.playerMap = map;
    }

    public static void setLeaderBoardSize(int size) {
        leaderBoardSize = size;
    }

    public void savePlayerAction(Player player, int count) {
        if (playerMap.containsKey(player.getUniqueId())) {
            PlayerData playerData = playerMap.get(player.getUniqueId());
            playerData.increaseContribution(count);
        } else {
            PlayerData playerData = new PlayerData(player.getDisplayName());
            playerData.increaseContribution(count);

            playerMap.put(player.getUniqueId(), playerData);
        }

    }

    public void sendLeaderString() {
        if (leaderBoardSize <= 0) {
            return;
        }

        if(this.playerMap.size() < 1) {
            return;
        }
        StringBuilder result = new StringBuilder(LanguageConfig.getConfig().getMessages().getTopContributorsTitle());
        TreeMap<UUID, PlayerData> map = new TreeMap<UUID, PlayerData>(new SortByContributions(this.playerMap));
        map.putAll(this.playerMap);

        int count = 1;
        for (UUID key : map.keySet()) {
            if (count == leaderBoardSize + 1) {
                break;
            }

            result.append("\n &f#");
            result.append(count);
            result.append(") &a");
            result.append(map.get(key).getDisplayName());
            result.append(" &7- &f");
            result.append(map.get(key).getAmountContributed());

            count++;
        }
        Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', result.toString()));
    }

    public PlayerData getTopPlayerData() {
        TreeMap<UUID, PlayerData> map = new TreeMap<UUID, PlayerData>(new SortByContributions(this.playerMap));
        map.putAll(this.playerMap);

        for (UUID key : map.keySet()) {
            return map.get(key);
        }
        return null;
    }

    public int getAmountContributed(Player player) {
        if (playerMap.containsKey(player.getUniqueId())) {
            return playerMap.get(player.getUniqueId()).getAmountContributed();
        }
        return 0;
    }

    public JSONArray getPlayerDataInJson() {
        JSONArray jArray = new JSONArray();
        for (UUID key : playerMap.keySet()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(key.toString(), playerMap.get(key).getAmountContributed());
            jArray.add(jsonObject);
        }
        return jArray;
    }

    public void giveOutRewards(int questGoal) {
        for (UUID key : playerMap.keySet()) {
            double playerContributionRatio;
            double playerContribution = (double)playerMap.get(key).getAmountContributed();
            if(questGoal > 0) {
                playerContributionRatio = playerContribution / (double) questGoal;
            } else {
                playerContributionRatio = playerContribution / (double) getTopPlayerData().getAmountContributed();
            }

            OfflinePlayer player = Bukkit.getServer().getOfflinePlayer(key);

            if (player.isOnline()) {
                Player onlinePlayer = (Player) player;
                if (rewardsList.size() > 0) {
                    onlinePlayer.sendMessage(ChatColor.translateAlternateColorCodes('&', LanguageConfig.getConfig().getMessages().getRewardsTitle()));
                }
            }

            for (Reward reward : rewardsList) {
                reward.giveRewardToPlayer(player, playerContributionRatio);
            }
        }
    }
}
