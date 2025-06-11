package me.wonka01.ServerQuests.commands;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.wonka01.ServerQuests.ServerQuests;
import me.wonka01.ServerQuests.questcomponents.rewards.RewardManager;

public class ClearRewardsCommand extends PluginCommand {
    public ClearRewardsCommand(ServerQuests plugin) {
        super(plugin, false);
    }

    @Override
    public String getName() {
        return "clearrewards";
    }

    @Override
    public String getPermission() {
        return "communityquests.clearrewards";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!sender.hasPermission("communityquests.clearrewards")) {
            String noPermission = getPlugin().messages().message("noPermission");
            sender.sendMessage(noPermission);
            return;
        }

        if (args.length > 1) {
            String playerName = args[1];
            Player target = Bukkit.getPlayer(playerName);
            if (target != null) {
                RewardManager rewards = RewardManager.getInstance();

                if (rewards.getRewards(target.getUniqueId()).isEmpty()) {
                    String noRewards = getPlugin().messages().message("noRewards");
                    sender.sendMessage(noRewards);
                    return;
                }

                rewards.removeRewards(target.getUniqueId());
                String rewardsCleared = getPlugin().messages().message("rewardsCleared");
                sender.sendMessage(rewardsCleared);
            } else {
                String playerNotFound = getPlugin().messages().message("playerNotFound");
                sender.sendMessage(playerNotFound);
            }
            return;
        } else if (sender instanceof Player) {
            RewardManager.getInstance().clearRewards();
            String rewardsCleared = getPlugin().messages().message("rewardsCleared");
            sender.sendMessage(rewardsCleared);
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        // TODO Auto-generated method stub
        return null;
    }
}
