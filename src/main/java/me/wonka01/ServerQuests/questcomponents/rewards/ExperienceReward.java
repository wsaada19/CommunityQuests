package me.wonka01.ServerQuests.questcomponents.rewards;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class ExperienceReward implements Reward {

    private int experienceAmount;

    public ExperienceReward(int amount)
    {
        this.experienceAmount = amount;
    }

    public void giveRewardToPlayer(OfflinePlayer player, double rewardPercentage) {
        if(player.isOnline())
        {
            int weightedExperience = (int)(rewardPercentage * (double)experienceAmount);
            Player realPlayer = (Player)player;
            realPlayer.giveExp(weightedExperience);
        }

    }
}
