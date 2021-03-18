package me.wonka01.ServerQuests.questcomponents.rewards;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ItemReward implements Reward {

    private String materialName;
    private int amount;
    private String displayName;

    public ItemReward(int count, String materialName, String displayName) {
        this.amount = count;
        this.materialName = materialName;
        if (displayName == null || displayName.length() < 1) {
            this.displayName = materialName;
        } else {
            this.displayName = displayName;
        }
    }

    public void giveRewardToPlayer(OfflinePlayer player, double rewardPercentage) {
        ItemStack itemStack = new ItemStack(Material.getMaterial(materialName), amount);
        if (player.isOnline()) {
            Player realPlayer = (Player) player;
            realPlayer.getInventory().addItem(itemStack);
            realPlayer.sendMessage(ChatColor.translateAlternateColorCodes('&', "- " + amount + " " + displayName));
        }
    }
}
