package me.wonka01.ServerQuests.commands;

import lombok.NonNull;
import me.wonka01.ServerQuests.ServerQuests;
import me.wonka01.ServerQuests.events.MoneyQuest;
import me.wonka01.ServerQuests.questcomponents.ActiveQuests;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class MoneyCommand extends PluginCommand {

    public MoneyCommand(ServerQuests plugin) {
        super(plugin, true);
    }

    @Override
    public @NonNull String getName() {
        return "deposit";
    }

    @Override
    public @NonNull String getPermission() {
        return "communityquests.money";
    }

    @Override
    public void execute(@NonNull CommandSender sender, @NotNull @NonNull String[] args) {
        Player player = (Player) sender;

        if (args.length < 2) {
            String invalidCmd = getPlugin().messages().message("invalidCommand");
            player.sendMessage(invalidCmd);
            return;
        }

        try {
            double money = Double.parseDouble(args[1]);
            MoneyQuest moneyQuest = new MoneyQuest(ActiveQuests.getActiveQuestsInstance(), getPlugin().getEconomy());

            if (!moneyQuest.tryAddItemsToQuest(money, player)) {
                String noActiveDepositQuests = getPlugin().messages().message("noActiveDepositQuests");
                player.sendMessage(noActiveDepositQuests);
            }
        } catch (NumberFormatException exception) {
            String message = color(args[1] + " is not a valid number!");
            player.sendMessage(message);
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return null;
    }
}
