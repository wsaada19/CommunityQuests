package me.wonka01.ServerQuests.gui;

import lombok.NonNull;
import me.knighthat.apis.menus.Menu;
import me.knighthat.apis.utils.Utils;
import me.wonka01.ServerQuests.ServerQuests;
import me.wonka01.ServerQuests.questcomponents.ActiveQuests;
import me.wonka01.ServerQuests.questcomponents.QuestController;
import me.wonka01.ServerQuests.questcomponents.QuestData;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class StopMenu extends Menu {

    public StopMenu(ServerQuests plugin, @NonNull Player owner) {
        super(plugin, owner, "stopQuest", 36);
    }

    @Override
    protected void setContents() {
        int index = 0;
        for (QuestController controller : getControllers()) {
            String completed = Utils.decimalToString(controller.getQuestData().getAmountCompleted()),
                    progressStr = getPlugin().messages().string("progress");
            QuestData data = controller.getQuestData();

            List<String> lore = new ArrayList<>();
            String[] eventDescriptionArr = data.getDescriptionArr();
            for (String s : eventDescriptionArr) {
                lore.add(s);
            }
            lore.add(" ");
            lore.add(progressStr + ": &a" + completed + "/" + Utils.decimalToString(data.getQuestGoal()));
            lore.add(getPlugin().messages().string("endQuestText"));

            ItemStack item = super.createItemStack(data.getDisplayItem(), data.getDisplayName(), lore);
            getInventory().setItem(index, item);
            index++;
        }
    }

    @Override
    protected void onItemClick(@NonNull InventoryClickEvent event) {
        try {
            UUID id = getControllers().get(event.getRawSlot()).getQuestId();
            ActiveQuests.getActiveQuestsInstance().endQuest(id);
            getOwner().closeInventory();
        } catch (NullPointerException | IndexOutOfBoundsException ignored) {
        }
    }
}
