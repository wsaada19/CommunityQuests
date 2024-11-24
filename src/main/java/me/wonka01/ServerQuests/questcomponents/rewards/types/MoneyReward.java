package me.wonka01.ServerQuests.questcomponents.rewards;

import lombok.Getter;
import me.knighthat.apis.utils.Colorization;
import me.wonka01.ServerQuests.ServerQuests;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.java.JavaPlugin;
import org.json.simple.JSONObject;

public class MoneyReward implements Reward, Colorization {

    @Getter
    private final double amount;

    public MoneyReward(double amount) {
        this.amount = amount;
    }

    public void giveRewardToPlayer(OfflinePlayer player, double rewardPercentage) {
        if (amount <= 0) {
            return;
        }
        Economy economy = JavaPlugin.getPlugin(ServerQuests.class).getEconomy();
        if (economy == null) {
            return;
        }
        double weightedAmount = rewardPercentage * amount;
        economy.depositPlayer(player, weightedAmount);

        // if (player.isOnline()) {
        // String message = "- " + weightedAmount + " " + economy.currencyNamePlural();
        // ((Player) player).sendMessage(color(message));
        // }
    }

    @Override
    public String toString() {
        return "Money \n Amount: " + amount;
    }

    @Override
    public String getType() {
        return "money";
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("type", getType());
        json.put("amount", amount);
        return json;
    }
}
