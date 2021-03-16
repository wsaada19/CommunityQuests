package me.wonka01.ServerQuests.events.questevents;

import me.wonka01.ServerQuests.gui.BaseGui;
import me.wonka01.ServerQuests.handlers.EventListenerHandler;
import me.wonka01.ServerQuests.questcomponents.ActiveQuests;
import me.wonka01.ServerQuests.questcomponents.QuestController;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class GuiEvent extends QuestListener {

    private BaseGui guiMenu;
    private int[] glassLocations = {12, 13, 14, 21, 23, 30, 31, 32};

    public GuiEvent(ActiveQuests activeQuests) {
        super(activeQuests);
    }

    public boolean tryAddItemsToQuest(ItemStack itemsToAdd, Player player) {
        List<QuestController> controllers = tryGetControllersOfEventType(EventListenerHandler.EventListenerType.GUI);
        for(QuestController controller : controllers) {
            // Add logic to check the item
            updateQuest(controller, player, itemsToAdd.getAmount());
            return true;
        }
        return false;
    }
}
