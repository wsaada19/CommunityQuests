package me.wonka01.ServerQuests.events;

import me.wonka01.ServerQuests.enums.ObjectiveType;
import me.wonka01.ServerQuests.questcomponents.ActiveQuests;
import me.wonka01.ServerQuests.questcomponents.QuestController;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;

import java.util.List;

public class EnchantItemQuestEvent extends QuestListener implements Listener {

    public EnchantItemQuestEvent(ActiveQuests activeQuests) {
        super(activeQuests);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onEnchantItem(EnchantItemEvent event) {
        Player player = event.getEnchanter();

        List<QuestController> controllers = tryGetControllersOfObjectiveType(ObjectiveType.ENCHANT_ITEM);
        for (QuestController controller : controllers) {
            updateQuest(controller, player, 1, ObjectiveType.ENCHANT_ITEM, event.getItem().getType());
        }
    }
}
