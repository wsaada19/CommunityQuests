package me.wonka01.ServerQuests.questcomponents.rewards;

import lombok.Getter;
import me.knighthat.apis.utils.Colorization;
import me.wonka01.ServerQuests.ServerQuests;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.json.simple.JSONObject;

import java.text.MessageFormat;

public class ExperienceReward implements Reward, Colorization {

    @Getter
    private final int experience;

    public ExperienceReward(int experience) {
        this.experience = experience;
    }

    public void giveRewardToPlayer(OfflinePlayer offlinePlayer, double rewardPercentage) {
        if (!offlinePlayer.isOnline() || experience <= 0)
            return;

        Player player = offlinePlayer.getPlayer();
        int exp = (int) (rewardPercentage * experience);

        player.giveExp(exp);

        ServerQuests plugin = JavaPlugin.getPlugin(ServerQuests.class);
        // String message = MessageFormat.format("- &a{0} {1}", exp,
        // plugin.messages().message("experience"));
        // player.sendMessage(color(message));
    }

    @Override
    public String toString() {
        return "Experience \n Amount: " + experience;
    }

    @Override
    public String getType() {
        return "experience";
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("type", getType());
        json.put("amount", experience);
        return json;
    }
}
