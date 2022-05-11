package me.knighthat.apis.events.instances;

import lombok.NonNull;
import me.knighthat.apis.events.EventBase;
import me.wonka01.ServerQuests.ServerQuests;
import me.wonka01.ServerQuests.enums.ObjectiveType;
import me.wonka01.ServerQuests.questcomponents.QuestController;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Range;

public class MoneyDonateEvent extends EventBase<Event> {

    private final @NonNull Economy economy;

    public MoneyDonateEvent(ServerQuests plugin) {
        super(plugin, ObjectiveType.GIVE_MONEY);
        this.economy = plugin.getEconomy();
    }

    public boolean attemptToUpdate(@Range(from = 0, to = Long.MAX_VALUE) double amount, @NonNull Player player) {

        boolean canWithraw = false;
        double money = amount;

        for (QuestController ctrl : super.getControllers()) {

            int goal = ctrl.getQuestData().getQuestGoal();
            double completed = ctrl.getQuestData().getAmountCompleted();

            if (goal > 0 && (completed + amount) > goal) {

                money = amount - completed - goal;
                economy.depositPlayer(player, completed + money - goal);
            }

            super.update(ctrl, player, money);
            canWithraw = true;
        }

        if (canWithraw)
            economy.withdrawPlayer(player, money);

        return canWithraw;
    }

    @Deprecated
    @Override
    public void event(Event event) {
    }
}
