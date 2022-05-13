package me.wonka01.ServerQuests.events;

import me.knighthat.apis.utils.Utils;
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

            List<String> materials = controller.getEventConstraints().getMaterialNames();
            int goal = controller.getQuestData().getQuestGoal();
            int completed = controller.getQuestData().getQuestGoal();

            if (materials.isEmpty() || Utils.contains(materials, itemsToAdd.getType())) {
                if (goal > 0 && completed + itemsToAdd.getAmount() > goal) {
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
}
