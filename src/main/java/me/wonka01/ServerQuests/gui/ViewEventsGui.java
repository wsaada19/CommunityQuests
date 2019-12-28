package me.wonka01.ServerQuests.gui;

import me.wonka01.ServerQuests.questcomponents.ActiveQuests;
import me.wonka01.ServerQuests.questcomponents.QuestController;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class ViewEventsGui extends BaseGui implements InventoryHolder {

    public Inventory inventory;
    public Player player;

    public ViewEventsGui(Player player)
    {
        this.player = player;
        inventory = Bukkit.createInventory(this, 36, "Active Quests");
        initializeItems();
    }

    public  ViewEventsGui(){
        inventory = Bukkit.createInventory(this, 36, "Active Quests");
    }

    @Override
    public void initializeItems() {
        List<QuestController> controllers = ActiveQuests.getActiveQuestsInstance().getActiveQuestsList();

        for(QuestController controller : controllers){
            if(controller.isCompetitive()){
                createItemStackForComp(controller);
            } else{
                createItemStackForCoop(controller);
            }
        }
    }

    public void createItemStackForCoop(QuestController controller){
        int progress = controller.getQuestData().getAmountCompleted();
        int goal = controller.getQuestData().getQuestGoal();

        int playerProgress = controller.getPlayerComponent().getAmountContributed(player);
        String progressString = ChatColor.GRAY + "Progress: " + ChatColor.GREEN + progress + "/" + goal;
        String playerProgressString = ChatColor.GRAY + "You: " + ChatColor.GREEN + playerProgress;

        ItemStack item = createGuiItem(Material.DIAMOND,
                ChatColor.YELLOW + controller.getQuestData().getDisplayName(),
                ChatColor.WHITE + controller.getQuestData().getDescription(),
                "",
                progressString,
                playerProgressString);

        inventory.addItem(item);
    }

    public void createItemStackForComp(QuestController controller){
        int progress = controller.getQuestData().getAmountCompleted();
        int goal = controller.getQuestData().getQuestGoal();

        int playerProgress = controller.getPlayerComponent().getAmountContributed(player);
        String leaders = ChatColor.GRAY + "Leader:";
        int topPlayerAmount = controller.getPlayerComponent().getTopPlayerData().getAmountContributed();
        String topPlayerName = controller.getPlayerComponent().getTopPlayerData().getDisplayName();
        String playerProgressString = ChatColor.YELLOW + "You: " + ChatColor.GREEN + playerProgress + "/" + goal;

        ItemStack item = createGuiItem(Material.DIAMOND,
                ChatColor.YELLOW + controller.getQuestData().getDisplayName(),
                ChatColor.WHITE + controller.getQuestData().getDescription(),
                "",
                leaders,
                ChatColor.GREEN + topPlayerName + ": " + topPlayerAmount + "/" + goal,
                playerProgressString,
                playerProgressString);

        inventory.addItem(item);
    }

    public void openInventory() {

        player.openInventory(inventory);
    }

    public Inventory getInventory() {
        return inventory;
    }
}
