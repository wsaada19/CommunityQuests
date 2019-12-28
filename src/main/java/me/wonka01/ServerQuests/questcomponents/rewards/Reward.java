package me.wonka01.ServerQuests.questcomponents.rewards;

import org.bukkit.OfflinePlayer;

public interface Reward {

    void giveRewardToPlayer(OfflinePlayer player,  double rewardPercentage);
}
