package me.wonka01.ServerQuests.gui;

import lombok.NonNull;
import me.knighthat.apis.menus.Menu;
import me.wonka01.ServerQuests.ServerQuests;
import me.wonka01.ServerQuests.configuration.QuestLibrary;
import me.wonka01.ServerQuests.configuration.QuestModel;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StartMenu extends Menu {

    public StartMenu(ServerQuests plugin, @NonNull Player owner) {
        super(plugin, owner, "startQuest", 27);
    }

    @Override
    protected void setContents() {

        for (String key : getLibrary().getAllQuestKeys()) {

            QuestModel model = getQuestModel(key);

            List<String> lore = Arrays.asList(model.getEventDescription());
            int goalTime = model.getQuestGoal(), completeTime = model.getCompleteTime();
            if (goalTime > 0) {

                String goal = getPlugin().messages().string("goal");
                lore.add(goal + ": &c" + goalTime);
            }
            if (completeTime > 0) {

                String duration = getPlugin().messages().string("duration");
                lore.add(duration + ": &c " + completeTime);
            }
            String clickToStart = getPlugin().messages().string("clickToStart");
            lore.add(clickToStart);

            ItemStack item = super.createItemStack(model.getDisplayItem(), model.getDisplayName(), lore);
            getInventory().addItem(item);
        }
    }

    @Override
    protected void onItemClick(@NonNull InventoryClickEvent event) {

        try {

            int slot = event.getRawSlot();
            List<String> keys = new ArrayList<>(getLibrary().getAllQuestKeys());
            QuestModel model = getQuestModel(keys.get(slot));

            new TypeMenu(getPlugin(), getOwner(), model);
        } catch (NullPointerException | IndexOutOfBoundsException ignored) {
        }
    }

    private @NonNull QuestLibrary getLibrary() {
        return getPlugin().config().getQuestLibrary();
    }

    private @NonNull QuestModel getQuestModel(@NonNull String id) {
        return getLibrary().getQuestModelById(id);
    }
}
