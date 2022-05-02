package me.wonka01.ServerQuests.questcomponents.rewards;

import lombok.Getter;
import me.wonka01.ServerQuests.ServerQuests;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.text.MessageFormat;

public class ExperienceReward implements Reward {

    @Getter
    private final int experience;

    public ExperienceReward(int experience) {
        this.experience = experience;
    }

    public void giveRewardToPlayer(OfflinePlayer offlinePlayer, double rewardPercentage) {

        if (!offlinePlayer.isOnline()) return;

        Player player = offlinePlayer.getPlayer();
        int exp = (int) (rewardPercentage * experience);

        player.giveExp(exp);

        ServerQuests plugin = JavaPlugin.getPlugin(ServerQuests.class);
        String message = MessageFormat.format("- &a{0} {1}", exp, plugin.getMessages().message("experience"));
        player.sendMessage(message);
    }
}
