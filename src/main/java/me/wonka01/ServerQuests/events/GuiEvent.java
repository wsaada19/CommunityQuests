package me.wonka01.ServerQuests.events;

import me.wonka01.ServerQuests.enums.ObjectiveType;
import me.wonka01.ServerQuests.objectives.Objective;
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
        List<QuestController> controllers = tryGetControllersOfObjectiveType(ObjectiveType.GUI);
        boolean isItemUsed = false;
        for (QuestController controller : controllers) {
            for (Objective objective : controller.getQuestData().getObjectives()) {
                if (objective.getType() != ObjectiveType.GUI)
                    continue;

                // TODO: there's two checks happening but we need this one to get the amount
                if (objective.getMaterials().isEmpty() || objective.getMaterials().contains(itemsToAdd.getType())) {
                    int goal = (int) controller.getQuestData().getQuestGoalByType(ObjectiveType.GUI);
                    int completed = (int) controller.getQuestData().getAmountCompletedByType(ObjectiveType.GUI);

                    if (goal > 0 && completed + itemsToAdd.getAmount() > goal) {
                        int difference = completed + itemsToAdd.getAmount() - goal;
                        ItemStack itemsToReturn = new ItemStack(itemsToAdd.getType(), difference);
                        player.getInventory().addItem(itemsToReturn);
                    }

                    updateQuest(controller, player, itemsToAdd.getAmount(), ObjectiveType.GUI);
                    isItemUsed = true;
                }
            }
        }
        return isItemUsed;
    }
}
