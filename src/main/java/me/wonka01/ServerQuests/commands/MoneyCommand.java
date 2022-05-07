package me.wonka01.ServerQuests.commands;

import lombok.NonNull;
import me.knighthat.apis.commands.PluginCommand;
import me.wonka01.ServerQuests.ServerQuests;
import me.wonka01.ServerQuests.events.MoneyQuest;
import me.wonka01.ServerQuests.questcomponents.ActiveQuests;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class MoneyCommand extends PluginCommand {

    public MoneyCommand(ServerQuests plugin) {
        super(plugin, true);
    }

    @Override
    public @NonNull String getName() {
        return "money";
    }

    @Override
    public @NonNull String getPermission() {
        return "communityquests.money";
    }

    @Override
    public void execute(@NonNull CommandSender sender, @NotNull @NonNull String[] args) {

        Player player = (Player) sender;

        if (args.length < 2) {
            String invalidCmd = getPlugin().getMessages().message("invalidCommand");
            player.sendMessage(invalidCmd);
            return;
        }

        try {

            double money = Double.parseDouble(args[1]);
            MoneyQuest moneyQuest = new MoneyQuest(ActiveQuests.getActiveQuestsInstance(), getPlugin().getEconomy());

            if (!moneyQuest.tryAddItemsToQuest(money, player)) {

                String noActiveDonateQuests = getPlugin().getMessages().message("noActiveDonateQuests");
                player.sendMessage(noActiveDonateQuests);
            }
        } catch (NumberFormatException exception) {

            String message = color(args[1] + " is not a valid number!");
            player.sendMessage(message);
        }
    }
}
