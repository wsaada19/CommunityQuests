package me.wonka01.ServerQuests.events.questevents;

import me.wonka01.ServerQuests.enums.ObjectiveType;
import me.wonka01.ServerQuests.questcomponents.ActiveQuests;
import me.wonka01.ServerQuests.questcomponents.QuestController;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;

import java.util.List;

public class EnchantItemQuestEvent extends QuestListener implements Listener {
    private final ObjectiveType TYPE = ObjectiveType.ENCHANT_ITEM;

    public EnchantItemQuestEvent(ActiveQuests activeQuests) {
        super(activeQuests);
    }

    @EventHandler
    public void onEnchantItem(EnchantItemEvent event) {
        Player player = event.getEnchanter();

        List<QuestController> controllers = tryGetControllersOfEventType(TYPE);
        for (QuestController controller : controllers) {
            updateQuest(controller, player, 1);
        }
    }
}
