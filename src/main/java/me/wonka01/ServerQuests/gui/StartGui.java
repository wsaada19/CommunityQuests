package me.wonka01.ServerQuests.gui;

import com.sun.istack.internal.NotNull;
import me.wonka01.ServerQuests.ServerQuests;
import me.wonka01.ServerQuests.configuration.QuestLibrary;
import me.wonka01.ServerQuests.configuration.QuestModel;
import me.wonka01.ServerQuests.util.ObjectiveTypeUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Set;

public class StartGui extends BaseGui implements InventoryHolder, Listener {

    private Inventory inventory;
    private QuestLibrary questLibrary;
    private TypeGui typeGui;

    public StartGui(TypeGui typeGui) {
        inventory = Bukkit.createInventory(this, 27, "Begin Server Event");
        questLibrary = JavaPlugin.getPlugin(ServerQuests.class).getQuestLibrary();
        ;
        this.typeGui = typeGui;
    }

    @NotNull
    public Inventory getInventory() {
        return inventory;
    }

    public void initializeItems() {
        inventory.clear();
        questLibrary = JavaPlugin.getPlugin(ServerQuests.class).getQuestLibrary();
        Set<String> keys = questLibrary.getAllQuestKeys();
        int count = 0;
        for (String key : keys) {
            QuestModel model = questLibrary.getQuestModelById(key);
            Material material = ObjectiveTypeUtil.getEventTypeDefaultMaterial(model.getObjective());

            inventory.setItem(count, createGuiItem(material, ChatColor.GREEN + model.getDisplayName(),
                    ChatColor.WHITE + model.getEventDescription(),
                    ChatColor.GRAY + "Goal: " + model.getQuestGoal()));
            count++;
        }
    }

    public void openInventory(Player p) {
        p.openInventory(inventory);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {

        if (!clickEventCheck(e, this)) {
            return;
        }
        Player player = (Player) e.getWhoClicked();

        int clickedSlot = e.getRawSlot();
        QuestModel model = null;
        int count = 0;

        for (String modelKeys : questLibrary.getAllQuestKeys()) {
            if (clickedSlot == count) {
                model = questLibrary.getQuestModelById(modelKeys);
                break;
            }
            count++;
        }

        if (model == null) {
            return;
        }
        player.closeInventory();
        typeGui.openInventory(player, model);
    }
}
