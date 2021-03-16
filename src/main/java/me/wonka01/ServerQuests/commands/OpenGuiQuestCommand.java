package me.wonka01.ServerQuests.commands;

import me.wonka01.ServerQuests.handlers.EventListenerHandler;
import me.wonka01.ServerQuests.questcomponents.ActiveQuests;
import me.wonka01.ServerQuests.questcomponents.QuestController;
import org.bukkit.entity.Player;

import java.util.List;

public class OpenGuiQuestCommand extends SubCommand {
    public void onCommand(Player player, String[] args) {

        List<QuestController> activeQuests = ActiveQuests.getActiveQuestsInstance().getActiveQuestsList();

        for (QuestController controller : activeQuests) {
            if (controller.getQuestType().equals(EventListenerHandler.EventListenerType.GUI.toString())) {
                player.sendMessage("There is an active GUI Event");
            }
        }
    }
}
