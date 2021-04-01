package me.wonka01.ServerQuests.events.questevents;

import me.wonka01.ServerQuests.enums.ObjectiveType;
import me.wonka01.ServerQuests.questcomponents.ActiveQuests;
import me.wonka01.ServerQuests.questcomponents.QuestController;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class GuiEvent extends QuestListener {

    public GuiEvent(ActiveQuests activeQuests) {
        super(activeQuests);
    }

    public boolean tryAddItemsToQuest(ItemStack itemsToAdd, Player player) {
        List<QuestController> controllers = tryGetControllersOfEventType(ObjectiveType.GUI);
        boolean isItemUsed = false;
        for (QuestController controller : controllers) {

            // Add logic to check the item
            List<String> materials = controller.getQuestConstraints().getMaterialNames();
            if (materials.isEmpty() || containsMaterial(itemsToAdd.getType().toString(), materials)) {
                updateQuest(controller, player, itemsToAdd.getAmount());
                isItemUsed = true;
            }
        }
        return isItemUsed;
    }

    private boolean containsMaterial(String material, List<String> materials) {
        for (String targetMaterial : materials) {
            if (material.contains(targetMaterial.toUpperCase())) {
                return true;
            }
        }
        return false;
    }
}
