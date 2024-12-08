package me.wonka01.ServerQuests.gui;

import me.knighthat.apis.menus.Menu;
import me.knighthat.apis.utils.Utils;
import me.wonka01.ServerQuests.ServerQuests;
import me.wonka01.ServerQuests.configuration.QuestHistoryManager;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ViewHistoryMenu extends Menu {
    private final QuestHistoryManager historyManager;

    public ViewHistoryMenu(ServerQuests plugin, Player owner) {
        super(plugin, owner, "historyMenu", 54); // Use full chest inventory
        this.historyManager = plugin.getQuestHistoryManager();
    }

    @Override
    protected void setContents() {
        List<Map<String, Object>> recentQuests = historyManager.getRecentQuests(45); // Get up to 45 recent quests
        Bukkit.getLogger().info("Recent quests: " + recentQuests.size());
        for (int i = 0; i < recentQuests.size() && i < 45; i++) {
            Map<String, Object> questData = recentQuests.get(i);

            // Choose material based on quest type
            Material material = Material.valueOf((String) questData.get("displayItem"));
            ItemStack questItem = createQuestHistoryItem(material, questData);
            getInventory().setItem(i, questItem);
        }
    }

    private ItemStack createQuestHistoryItem(Material material, Map<String, Object> questData) {
        String questName = (String) questData.get("questName");

        List<String> lore = new ArrayList<>();

        long completionTime = (Long) questData.get("completionTime");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        lore.add(color(getPlugin().messages().string("completed") + "&f" + sdf.format(new Date(completionTime))));

        lore.add(color(getPlugin().messages().string("topContributors")));
        List<?> topContributors = (List<?>) questData.get("topContributors");
        for (int i = 0; i < Math.min(topContributors.size(), 5); i++) {
            Map<?, ?> contributor = (Map<?, ?>) topContributors.get(i);
            String contributorName = (String) contributor.get("name");
            String contribution = Utils.decimalToString((Double) contributor.get("contribution"));
            lore.add(color("&7" + (i + 1) + ") &f" + contributorName + " &7- &6" + contribution));
        }
        return super.createItemStack(material, color("&6" + questName), lore);
    }
}