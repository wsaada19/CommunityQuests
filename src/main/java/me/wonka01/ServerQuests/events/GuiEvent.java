package me.wonka01.ServerQuests.events;

import me.wonka01.ServerQuests.enums.ObjectiveType;
import me.wonka01.ServerQuests.questcomponents.ActiveQuests;
import me.wonka01.ServerQuests.questcomponents.QuestController;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class GuiEvent extends QuestListener {

    public GuiEvent(ActiveQuests activeQuests) {
        super(activeQuests);
    }

    public boolean tryAddItemsToQuest(ItemStack itemsToAdd, Player player) {
        List<QuestController> controllers = tryGetControllersOfObjectiveType(ObjectiveType.GUI);
        boolean isItemUsed = false;
        for (QuestController controller : controllers) {

            List<Material> materials = controller.getQuestData().getObjectives().get(0).getMaterials();
            int goal = (int) controller.getQuestData().getQuestGoalByType(ObjectiveType.GUI);
            int completed = (int) controller.getQuestData().getAmountCompletedByType(ObjectiveType.GUI);

            if (materials.isEmpty() || materials.contains(itemsToAdd.getType())) {
                if (goal > 0 && completed + itemsToAdd.getAmount() > goal) {
                    int difference = completed + itemsToAdd.getAmount() - goal;
                    ItemStack itemsToReturn = new ItemStack(itemsToAdd.getType(), difference);
                    player.getInventory().addItem(itemsToReturn);
                }

                updateQuest(controller, player, itemsToAdd.getAmount(), ObjectiveType.GUI);
                isItemUsed = true;
            }
        }
        return isItemUsed;
    }
}
