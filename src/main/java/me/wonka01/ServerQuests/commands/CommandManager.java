package me.wonka01.ServerQuests.commands;

import lombok.Getter;
import lombok.NonNull;
import me.wonka01.ServerQuests.ServerQuests;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CommandManager implements CommandExecutor {

    private final ServerQuests plugin;

    private final @NonNull List<PluginCommand> commands = new ArrayList<>(9);

    @Getter
    private QuestScheduler questScheduler;

    public CommandManager(ServerQuests plugin) {

        plugin.getCommand("communityquests").setExecutor(this);

        this.plugin = plugin;

        commands.add(new HelpCommand(plugin));
        commands.add(new ReloadCommand(plugin));
        commands.add(new StartCommand(plugin));
        commands.add(new StopCommand(plugin));
        commands.add(new ViewCommand(plugin));
        commands.add(new DonateCommand(plugin));
        commands.add(new MoneyCommand(plugin));
        commands.add(new ToggleBarCommand(plugin));
        commands.add(new ToggleMessageCommand(plugin));
        commands.add(new RewardsCommand(plugin));
        commands.add(new EndAllCommand(plugin));
        commands.add(new ClaimRewards(plugin));
        commands.add(new ClearRewardsCommand(plugin));
        this.questScheduler = new QuestScheduler(plugin);
        commands.add(this.questScheduler);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        try {
            if (args.length >= 1) {
                getCommand(sender, args[0]).execute(sender, args);
            } else {
                getCommand(sender, "help").execute(sender, args);
            }
        } catch (NullPointerException ignored) {
        }
        return true;
    }

    private @Nullable PluginCommand getCommand(@NonNull CommandSender sender, @NonNull String arg) {
        for (PluginCommand cmd : commands)
            if (cmd.getName().equalsIgnoreCase(arg)) {

                if (cmd.isRequiresPlayer() && !(sender instanceof Player))
                    break;

                if (cmd.getPermission().isEmpty() || sender.hasPermission(cmd.getPermission()))
                    return cmd;

                String message = plugin.messages().message("noPermission");
                sender.sendMessage(message);
            }
        return null;
    }
}
