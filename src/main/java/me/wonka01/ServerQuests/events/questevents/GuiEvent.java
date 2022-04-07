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
            List<String> materials = controller.getEventConstraints().getMaterialNames();
            int goal = controller.getQuestData().getQuestGoal();
            int completed = controller.getQuestData().getAmountCompleted();
            if (materials.isEmpty() || containsMaterial(itemsToAdd.getType().toString(), materials)) {

                if(goal > 0 && completed + itemsToAdd.getAmount() > goal) {
                    int difference = completed + itemsToAdd.getAmount() - goal;
                    ItemStack itemsToReturn = new ItemStack(itemsToAdd.getType(), difference);
                    player.getInventory().addItem(itemsToReturn);
                }

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
