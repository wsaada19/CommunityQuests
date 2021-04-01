package me.wonka01.ServerQuests.questcomponents.rewards;

import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class ExperienceReward implements Reward {

    private int experience;

    public ExperienceReward(int experience) {
        this.experience = experience;
    }

    public void giveRewardToPlayer(OfflinePlayer player, double rewardPercentage) {
        if (player.isOnline()) {
            int weightedExperience = (int) (rewardPercentage * (double) experience);
            Player realPlayer = (Player) player;
            realPlayer.giveExp(weightedExperience);
            ((Player) player).sendMessage(ChatColor.translateAlternateColorCodes('&', "- &a" + experience + "&e experience"));
        }
    }
}
