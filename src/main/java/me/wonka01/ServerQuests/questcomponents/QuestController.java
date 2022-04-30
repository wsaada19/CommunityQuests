package me.wonka01.ServerQuests.questcomponents;

import lombok.NonNull;
import me.wonka01.ServerQuests.ServerQuests;
import me.wonka01.ServerQuests.enums.ObjectiveType;
import me.wonka01.ServerQuests.enums.PermissionNode;
import me.wonka01.ServerQuests.questcomponents.players.BasePlayerComponent;
import me.wonka01.ServerQuests.questcomponents.schedulers.QuestTimer;
import org.bukkit.entity.Player;

import java.util.UUID;

public class QuestController {

    private final QuestBar questBar;
    private final QuestData questData;
    private final BasePlayerComponent playerComponent;
    private final EventConstraints eventConstraints;
    private final UUID questId;
    private final ObjectiveType objective;
    private final ServerQuests plugin;

    public QuestController(ServerQuests plugin, QuestData questData, QuestBar questBar,
                           BasePlayerComponent playerComponent, EventConstraints eventConstraints,
                           ObjectiveType objective) {
        this.plugin = plugin;
        this.questData = questData;
        this.questBar = questBar;
        this.playerComponent = playerComponent;
        questId = UUID.randomUUID();
        this.eventConstraints = eventConstraints;
        this.objective = objective;

        if (questData.getQuestDuration() > 0) {
            new QuestTimer(plugin, this);
        }
    }

    public void updateQuest(int count, Player player) {
        int amountToAdd = count;

        if (questData.hasGoal()) {
            if (count > questData.getQuestGoal() - questData.getAmountCompleted()) {
                amountToAdd = questData.getQuestGoal() - questData.getAmountCompleted();
            }
            questData.addToQuestProgress(amountToAdd);
        }

        playerComponent.savePlayerAction(player, amountToAdd);
        updateBossBar();
        sendPlayerMessage(player);
    }

    public void endQuest() {
        if (questData.hasGoal() && !questData.isGoalComplete() && questData.getQuestType().equalsIgnoreCase("coop")) {

            broadcast("questFailureMessage");
            playerComponent.sendLeaderString();
        } else {

            broadcast("questCompleteMessage");
            playerComponent.sendLeaderString();
            playerComponent.giveOutRewards(questData.getQuestGoal());
        }

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

        if (!player.hasPermission(PermissionNode.SHOW_MESSAGES)) return;

        String message = plugin.getMessages().message("contributionMessage");
        player.sendMessage(message);
    }

    public void broadcast(@NonNull String messagePath) {

        String message = plugin.getMessages().message(messagePath, questData);
        plugin.getServer().broadcastMessage(message);
    }
}
