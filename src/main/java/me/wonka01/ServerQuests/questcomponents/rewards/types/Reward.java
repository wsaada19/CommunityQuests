package me.wonka01.ServerQuests.questcomponents.rewards.types;

import org.bukkit.OfflinePlayer;
import org.json.simple.JSONObject;

public interface Reward {
    void giveRewardToPlayer(OfflinePlayer player, double rewardPercentage);

    String getType();

    JSONObject toJson();
}
