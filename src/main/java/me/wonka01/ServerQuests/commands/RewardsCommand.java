package me.wonka01.ServerQuests.commands;

import lombok.NonNull;
import me.wonka01.ServerQuests.ServerQuests;
import me.wonka01.ServerQuests.gui.RewardsMenu;
import me.wonka01.ServerQuests.questcomponents.rewards.RewardManager;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class RewardsCommand extends PluginCommand {
    public RewardsCommand(ServerQuests plugin) {
        super(plugin, true);
    }

    @Override
    public @NonNull String getName() {
        return "rewards";
    }

    @Override
    public @NonNull String getPermission() {
        return "communityquests.rewards";
    }

    @Override
    public void execute(@NonNull CommandSender sender, @NotNull @NonNull String[] args) {
        Player player = (Player) sender;

        if (!RewardManager.getInstance().hasRewards(player.getUniqueId())) {
            String noRewards = getPlugin().messages().message("noRewards");
            player.sendMessage(noRewards);
            return;
        }
        new RewardsMenu(getPlugin(), player).open();
    }
}
