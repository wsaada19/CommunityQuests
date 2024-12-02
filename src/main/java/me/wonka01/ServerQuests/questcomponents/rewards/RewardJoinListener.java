package me.wonka01.ServerQuests.questcomponents.rewards;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.ChatColor;

public class RewardJoinListener implements Listener {
    private final RewardManager rewardManager;
    private final Boolean enabled;

    public RewardJoinListener(Boolean enabled) {
        this.rewardManager = RewardManager.getInstance();
        this.enabled = enabled;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (!enabled) {
            return;
        }

        Player player = event.getPlayer();

        if (rewardManager.hasRewards(player.getUniqueId())) {
            // Give rewards to the player
            rewardManager.giveRewardToPlayer(player.getUniqueId());

            // Send completion message
            player.sendMessage(ChatColor.GREEN + "You have received your quest rewards!");
        }
    }
}