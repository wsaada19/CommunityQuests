package me.wonka01.ServerQuests.questcomponents.rewards;

import lombok.Getter;
import me.knighthat.apis.utils.Colorization;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.json.simple.JSONObject;

public class RewardMessage implements Reward, Colorization {

    @Getter
    private final String message;

    public RewardMessage(String message) {
        this.message = message;
    }

    public void giveRewardToPlayer(OfflinePlayer player, double rewardPercentage) {
        if (player.isOnline()) {
            ((Player) player).sendMessage(color(message));
        }
    }

    @Override
    public String toString() {
        return "Message \n" + message;
    }

    @Override
    public String getType() {
        return "message";
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("type", getType());
        json.put("message", message);
        return json;
    }
}
