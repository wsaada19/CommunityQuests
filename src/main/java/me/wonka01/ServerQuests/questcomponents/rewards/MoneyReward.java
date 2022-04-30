package me.wonka01.ServerQuests.questcomponents.rewards;

import me.wonka01.ServerQuests.ServerQuests;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class MoneyReward implements Reward {

    private double amount;

    public MoneyReward(double amount) {
        this.amount = amount;
    }

    public void giveRewardToPlayer(OfflinePlayer player, double rewardPercentage) {

        Economy economy = JavaPlugin.getPlugin(ServerQuests.class).getEconomy();
        if (economy == null) {
            return;
        }
        double weightedAmount = rewardPercentage * amount;

        economy.depositPlayer(player, weightedAmount);

        if (player.isOnline()) {
            ((Player) player).sendMessage("- " + weightedAmount + " " + economy.currencyNamePlural());
        }
    }
}
