package me.wonka01.ServerQuests.gui;

import me.wonka01.ServerQuests.configuration.messages.LanguageConfig;
import me.wonka01.ServerQuests.questcomponents.ActiveQuests;
import me.wonka01.ServerQuests.questcomponents.QuestController;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.UUID;

public class StopGui extends BaseGui implements InventoryHolder, Listener {
    private final int END_ALL = 17;
    private Inventory inventory;

    public StopGui() {
        inventory = Bukkit.createInventory(this, 36, ChatColor.translateAlternateColorCodes('&', LanguageConfig.getConfig().getMessages().getStopMenu()));
    }

    public void initializeItems() {
        inventory.clear();
        List<QuestController> controllers = ActiveQuests.getActiveQuestsInstance().getActiveQuestsList();
        int count = 0;
        for (QuestController controller : controllers) {
            double progress = controller.getQuestData().getAmountCompleted();
            int goal = controller.getQuestData().getQuestGoal();

            String progressString = LanguageConfig.getConfig().getMessages().getProgress() + ": &a" + progress + "/" + goal;

            ItemStack item = createGuiItem(Material.DIAMOND,
                controller.getQuestData().getDisplayName(),
                controller.getQuestData().getDescription(),
                "",
                progressString,
                LanguageConfig.getConfig().getMessages().getEndQuestText());

            inventory.setItem(count, item);
            count++;
        }

        //ItemStack redStone = createGuiItem(Material.RED_STAINED_GLASS_PANE, ChatColor.RED + "End All Quests", "");
        //inventory.setItem(END_ALL, redStone);
    }

    public void openInventory(Player p) {
        p.openInventory(inventory);
    }


    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {

        if (!clickEventCheck(e, this)) {
            return;
        }

        int slotNumber = e.getRawSlot();
        int counter = 0;
        QuestController controllerToRemove = null;

        if (slotNumber == END_ALL) {
            ActiveQuests.getActiveQuestsInstance().endAllQuests();
        }

        for (QuestController controller : ActiveQuests.getActiveQuestsInstance().getActiveQuestsList()) {
            if (counter == slotNumber) {
                controllerToRemove = controller;
                break;
            }
            counter++;
        }

        if (controllerToRemove != null) {
            UUID id = controllerToRemove.getQuestId();
            ActiveQuests.getActiveQuestsInstance().endQuest(id);
            e.getWhoClicked().closeInventory();
        }
    }

    public Inventory getInventory() {
        return inventory;
    }
}
