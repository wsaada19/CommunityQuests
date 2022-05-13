package me.wonka01.ServerQuests.events;

import me.knighthat.apis.utils.Utils;
import me.wonka01.ServerQuests.enums.ObjectiveType;
import me.wonka01.ServerQuests.questcomponents.ActiveQuests;
import me.wonka01.ServerQuests.questcomponents.QuestController;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;

import java.util.List;

public class CraftItemQuestEvent extends QuestListener implements Listener {

    public CraftItemQuestEvent(ActiveQuests activeQuests) {
        super(activeQuests);
    }

    @EventHandler
    public void onCraftItem(CraftItemEvent event) {
        Player player = (Player) event.getWhoClicked();

        int amount = event.getRecipe().getResult().getAmount();

        List<QuestController> controllers = tryGetControllersOfEventType(ObjectiveType.CRAFT_ITEM);
        for (QuestController controller : controllers) {
            List<String> materials = controller.getEventConstraints().getMaterialNames();

            if (materials.isEmpty() || Utils.contains(materials, event.getRecipe().getResult().getType()))
                updateQuest(controller, player, amount);
        }
    }
}
