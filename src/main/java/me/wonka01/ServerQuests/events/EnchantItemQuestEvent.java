package me.wonka01.ServerQuests.events;

import me.wonka01.ServerQuests.enums.ObjectiveType;
import me.wonka01.ServerQuests.questcomponents.ActiveQuests;
import me.wonka01.ServerQuests.questcomponents.QuestController;
import me.wonka01.ServerQuests.utils.MaterialUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;

import java.util.List;

public class EnchantItemQuestEvent extends QuestListener implements Listener {

    public EnchantItemQuestEvent(ActiveQuests activeQuests) {
        super(activeQuests);
    }

    @EventHandler
    public void onEnchantItem(EnchantItemEvent event) {
        Player player = event.getEnchanter();

        String materialName = event.getItem().getType().toString();

        List<QuestController> controllers = tryGetControllersOfEventType(ObjectiveType.ENCHANT_ITEM);
        for (QuestController controller : controllers) {
            List<String> materials = controller.getEventConstraints().getMaterialNames();
            if (materials.isEmpty() || MaterialUtil.containsMaterial(materialName, materials)) {
                updateQuest(controller, player, 1);
            }
        }
    }
}
