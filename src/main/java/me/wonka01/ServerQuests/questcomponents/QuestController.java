package me.wonka01.ServerQuests.questcomponents;

import me.wonka01.ServerQuests.enums.ObjectiveType;
import me.wonka01.ServerQuests.enums.PermissionConstants;
import me.wonka01.ServerQuests.questcomponents.players.BasePlayerComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.UUID;

public class QuestController {

    private QuestBar questBar;
    private QuestData questData;
    private BasePlayerComponent playerComponent;
    private EventConstraints eventConstraints;
    private UUID questId;
    private ObjectiveType objective;

    public QuestController(QuestData questData, QuestBar questBar,
                           BasePlayerComponent playerComponent, EventConstraints eventConstraints,
                           ObjectiveType objective) {
        this.questData = questData;
        this.questBar = questBar;
        this.playerComponent = playerComponent;
        questId = UUID.randomUUID();
        this.eventConstraints = eventConstraints;
        this.objective = objective;
    }

    public boolean updateQuest(int count, Player player) {
        int amountToAdd = count;
        if (count > questData.getQuestGoal() - questData.getAmountCompleted()) {
            amountToAdd = questData.getQuestGoal() - questData.getAmountCompleted();
        }
        questData.addToQuestProgress(amountToAdd);

        playerComponent.savePlayerAction(player, amountToAdd);
        updateBossBar();
        sendPlayerMessage(player);

        return questData.isQuestComplete();
    }

    public void handleQuestComplete() {
        broadcastVictoryMessage();
        playerComponent.sendLeaderString();
        playerComponent.giveOutRewards(questData.getQuestGoal());
        questBar.removeBossBar();
        ActiveQuests.getActiveQuestsInstance().endQuest(questId);
    }

    public void removeBossBar() {
        questBar.removeBossBar();
    }

    public UUID getQuestId() {
        return questId;
    }

    public QuestData getQuestData() {
        return questData;
    }

    public BasePlayerComponent getPlayerComponent() {
        return playerComponent;
    }

    public EventConstraints getEventConstraints() {
        return eventConstraints;
    }

    public ObjectiveType getObjectiveType() {
        return objective;
    }

    public boolean isCompetitive() {
        return (questData instanceof CompetitiveQuestData);
    }

    public String getQuestType() {
        return questData.getQuestType();
    }

    public QuestBar getQuestBar() {
        return questBar;
    }

    private void updateBossBar() {
        double barProgress = questData.getPercentageComplete();
        questBar.updateBarProgress(barProgress);
    }

    private void sendPlayerMessage(Player player) {
        if (player.hasPermission(PermissionConstants.SHOW_MESSAGES)) {
            String message = ChatColor.translateAlternateColorCodes('&', "&a+1 for the quest " + getQuestData().getDisplayName());
            player.sendMessage(message);
        }
    }

    public void broadcastStartMessage() {
        String message = ChatColor.translateAlternateColorCodes('&', "&a&lA new server-wide quest has been started &f- " + questData.getDisplayName());
        String questInfo = ChatColor.translateAlternateColorCodes('&', questData.getDescription());
        Bukkit.getServer().broadcastMessage(message);
        Bukkit.getServer().broadcastMessage(questInfo);
    }

    private void broadcastVictoryMessage() {
        String message = ChatColor.translateAlternateColorCodes('&', "&a&lQUEST COMPLETE &f(" + questData.getDisplayName() + "&f)");
        String questInfo = ChatColor.translateAlternateColorCodes('&', "Congrats on completing the quest! Below you can see a list of the top players.");
        Bukkit.getServer().broadcastMessage(message);
        Bukkit.getServer().broadcastMessage(questInfo);
    }
}
