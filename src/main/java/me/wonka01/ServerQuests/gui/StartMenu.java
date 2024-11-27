package me.wonka01.ServerQuests.gui;

import lombok.NonNull;
import me.knighthat.apis.menus.Menu;
import me.wonka01.ServerQuests.ServerQuests;
import me.wonka01.ServerQuests.configuration.QuestLibrary;
import me.wonka01.ServerQuests.configuration.QuestModel;
import me.wonka01.ServerQuests.questcomponents.schedulers.ParseDurationString;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class StartMenu extends Menu {

    public StartMenu(ServerQuests plugin, @NonNull Player owner) {
        super(plugin, owner, "startQuest", 36);
    }

    @Override
    protected void setContents() {
        int index = 0;
        for (String key : getLibrary().getAllQuestKeys()) {
            QuestModel model = getQuestModel(key);
            List<String> lore = new ArrayList<>();

            String[] eventDescriptionArr = model.getEventDescriptionArray();
            for (String s : eventDescriptionArr) {
                lore.add(s);
            }

            int completeTime = model.getCompleteTime();

            if (completeTime > 0) {
                String duration = getPlugin().messages().string("duration");
                lore.add(duration + ": &c" + ParseDurationString.formatSecondsToString(completeTime));
            }
            String clickToStart = getPlugin().messages().string("clickToStart");
            lore.add(clickToStart);

            ItemStack item = super.createItemStack(model.getDisplayItem(), model.getDisplayName(), lore);
            getInventory().setItem(index, item);
            index++;
        }
    }

    @Override
    protected void onItemClick(@NonNull InventoryClickEvent event) {
        try {
            int slot = event.getRawSlot();
            List<String> keys = new ArrayList<>(getLibrary().getAllQuestKeys());
            QuestModel model = getQuestModel(keys.get(slot));

            new TypeMenu(getPlugin(), getOwner(), model).open();
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
