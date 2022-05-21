package me.wonka01.ServerQuests.commands;

import lombok.NonNull;
import me.knighthat.apis.commands.PluginCommand;
import me.wonka01.ServerQuests.ServerQuests;
import me.wonka01.ServerQuests.enums.ObjectiveType;
import me.wonka01.ServerQuests.questcomponents.ActiveQuests;
import me.wonka01.ServerQuests.questcomponents.QuestController;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
            if (!deposit(money, player)) {
                String noActiveDonateQuests = getPlugin().messages().message("noActiveDonateQuests");
                player.sendMessage(noActiveDonateQuests);
            }
        } catch (NumberFormatException exception) {
            String message = color(args[1] + " is not a valid number!");
            player.sendMessage(message);
        }
    }

    private boolean deposit(@Range(from = 0, to = Long.MAX_VALUE) double amount, @NonNull Player player) {

        Economy economy = getPlugin().getEconomy();
        boolean canWithdraw = false;
        double money = amount;

        for (QuestController ctrl : this.getControllers()) {

            int goal = ctrl.getQuestData().getQuestGoal();
            double completed = ctrl.getQuestData().getAmountCompleted();

            if (goal > 0 && (completed + amount) > goal) {

                money = amount - completed - goal;
                economy.depositPlayer(player, completed + money - goal);
            }

            canWithdraw = true;

            if (ctrl.updateQuest(money, player))
                ctrl.endQuest();
        }

        if (canWithdraw)
            economy.withdrawPlayer(player, money);


        return canWithdraw;
    }

    private @NonNull List<QuestController> getControllers() {

        ActiveQuests activeQuests = ActiveQuests.getActiveQuestsInstance();
        Stream<QuestController> result = activeQuests.getActiveQuestsList().stream();
        result = result.filter(ins -> ins.getObjective().equals(ObjectiveType.GIVE_MONEY));
        return result.collect(Collectors.toList());
    }
}
