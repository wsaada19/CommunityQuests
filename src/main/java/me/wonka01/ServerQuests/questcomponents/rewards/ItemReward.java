package me.wonka01.ServerQuests.questcomponents.rewards;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ItemReward implements Reward {

    private String materialName;
    private int amount;

    public ItemReward(int count, String materialName) {
        this.amount = count;
        this.materialName = materialName;
    }

    public void giveRewardToPlayer(OfflinePlayer player, double rewardPercentage) {
        ItemStack itemStack = new ItemStack(Material.getMaterial(materialName), amount);
        Bukkit.getServer().getConsoleSender().sendMessage(materialName);
        if (player.isOnline()) {
            Player realPlayer = (Player) player;
            realPlayer.getInventory().addItem(itemStack);
            realPlayer.sendMessage(amount + " " + itemStack.getItemMeta().getDisplayName());
        }
    }
}
