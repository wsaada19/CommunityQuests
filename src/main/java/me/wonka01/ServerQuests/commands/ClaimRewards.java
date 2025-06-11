package me.wonka01.ServerQuests.commands;

import lombok.NonNull;
import me.wonka01.ServerQuests.ServerQuests;
import me.wonka01.ServerQuests.questcomponents.rewards.RewardEntry;
import me.wonka01.ServerQuests.questcomponents.rewards.RewardManager;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ClaimRewards extends PluginCommand {
    public ClaimRewards(ServerQuests plugin) {
        super(plugin, false);
    }

    @Override
    public @NonNull String getName() {
        return "claim";
    }

    @Override
    public @NonNull String getPermission() {
        return "communityquests.claim";
    }

    @Override
    public void execute(@NonNull CommandSender sender, @NotNull @NonNull String[] args) {
        if (args.length > 1) {
            if (!sender.hasPermission("communityquests.claim.others") && (sender instanceof Player)) {
                String noPermission = getPlugin().messages().message("noPermission");
                sender.sendMessage(noPermission);
                return;
            }

            String playerName = args[1];
            Player target = Bukkit.getPlayer(playerName);
            if (target != null && target.isOnline()) {
                RewardManager rewards = RewardManager.getInstance();

                if (rewards.getRewards(target.getUniqueId()).isEmpty()) {
                    String noRewards = getPlugin().messages().message("noRewards");
                    sender.sendMessage(noRewards);
                    return;
                }

                for (RewardEntry reward : rewards.getRewards(target.getUniqueId())) {
                    reward.getReward().giveRewardToPlayer(target, reward.getRatio());
                }
                rewards.removeRewards(target.getUniqueId());
            } else {
                String playerNotOnline = getPlugin().messages().message("playerNotOnline");
                sender.sendMessage(playerNotOnline);
            }
            return;
        } else if (sender instanceof Player) {
            Player player = (Player) sender;
            RewardManager rewards = RewardManager.getInstance();

            if (!sender.hasPermission("communityquests.claim") && (sender instanceof Player)) {
                String noPermission = getPlugin().messages().message("noPermission");
                sender.sendMessage(noPermission);
                return;
            }
            if (rewards.getRewards(player.getUniqueId()).isEmpty()) {
                String noRewards = getPlugin().messages().message("noRewards");
                player.sendMessage(noRewards);
                return;
            }

            for (RewardEntry reward : rewards.getRewards(player.getUniqueId())) {
                reward.getReward().giveRewardToPlayer(player, reward.getRatio());
            }
            rewards.removeRewards(player.getUniqueId());
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        // TODO Auto-generated method stub
        return null;
    }
}
