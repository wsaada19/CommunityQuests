package me.wonka01.ServerQuests.questcomponents;

import lombok.Getter;
import lombok.NonNull;
import me.wonka01.ServerQuests.ServerQuests;
import me.wonka01.ServerQuests.enums.EventType;
import me.wonka01.ServerQuests.enums.ObjectiveType;
import me.wonka01.ServerQuests.objectives.Objective;
import me.wonka01.ServerQuests.questcomponents.bossbar.QuestBar;
import me.wonka01.ServerQuests.questcomponents.players.PlayerContributionMap;
import me.wonka01.ServerQuests.questcomponents.schedulers.QuestTimer;
import me.wonka01.ServerQuests.utils.Colorization;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;
import java.util.List;
import java.util.UUID;

@Getter
public class QuestController implements Colorization {

    private final QuestBar questBar;
    private final QuestData questData;
    private final PlayerContributionMap playerComponent;
    private final List<String> worlds;
    private final UUID questId;
    private final ServerQuests plugin;

    public QuestController(ServerQuests plugin, QuestData questData, QuestBar questBar,
            PlayerContributionMap playerComponent, List<String> worlds) {
        this.plugin = plugin;
        this.questData = questData;
        this.questBar = questBar;
        this.playerComponent = playerComponent;
        questId = UUID.randomUUID();
        this.worlds = worlds;

        if (questData.getQuestDuration() > 0) {
            new QuestTimer(this);
        }
    }

    public boolean updateQuest(double count, Player player, Objective objective, Integer objectiveId) {
        double amountToAdd = count;

        // Make sure individuals can't exceed the goal of an objective
        if (questData.getEventType().equals(EventType.COMPETITIVE)) {
            CompetitiveQuestData competitiveQuestData = (CompetitiveQuestData) questData;
            if (competitiveQuestData.isGoalComplete(objective, player, objectiveId)) {
                return false;
            }
        } else if (questData.getEventType().equals(EventType.COLLECTIVE)) {
            CollectiveQuestData goalQuest = (CollectiveQuestData) questData;
            if (goalQuest.isGoalComplete(objective, player, objectiveId)) {
                return false;
            }
        } else {
            if (questData.isGoalComplete()) {
                return false;
            }
        }

        if (questData.hasGoal()) {
            double amountComplete = objective.getAmountComplete();

            if (questData.getEventType().equals(EventType.COMPETITIVE)) {
                CompetitiveQuestData competitiveQuestData = (CompetitiveQuestData) questData;
                amountComplete = competitiveQuestData.getPlayers().getAmountContributedByObjectiveId(player,
                        objectiveId);
            } else if (questData.getEventType().equals(EventType.COLLECTIVE)) {
                CollectiveQuestData goalQuest = (CollectiveQuestData) questData;
                amountComplete = goalQuest.getPlayers().getAmountContributedByObjectiveId(player, objectiveId);
            }

            if (amountToAdd > objective.getGoal() - amountComplete) {
                amountToAdd = objective.getGoal() - amountComplete;
            }
        }

        if (amountToAdd <= 0) {
            return false;
        }

        questData.addToQuestProgress(amountToAdd, objective);

        playerComponent.savePlayerAction(player, amountToAdd, objectiveId);
        updateBossBar();
        sendPlayerMessage(player, amountToAdd);

        // save to file every 100 actions
        if (questData.getAmountCompleted() % 100 == 0) {
            plugin.getJsonSave().saveQuestsInProgress();
        }

        if (getQuestData().isGoalComplete()) {
            Bukkit.getLogger().info("Quest complete");
            endQuest();
        }
        return getQuestData().isGoalComplete();
    }

    public void endQuest() {
        if (questData.hasGoal() && !questData.isGoalComplete() && questData.getEventType().equals(EventType.COLLAB)) {
            broadcast("questFailureMessage");
            playerComponent.sendLeaderString();
            if (questData.getQuestFailedCommand() != null && !questData.getQuestFailedCommand().isEmpty()) {
                plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(),
                        questData.getQuestFailedCommand());
            }
        } else {
            broadcast("questCompleteMessage");
            playerComponent.sendLeaderString();
            String completeMessage = color(plugin.messages().message("questCompleteMessage", questData));
            playerComponent.giveOutRewards(questData.getQuestGoal(), completeMessage, questData.getEventType());
            if (questData.getAfterQuestCommand() != null && !questData.getAfterQuestCommand().isEmpty()) {
                plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(),
                        questData.getAfterQuestCommand());
            }
            getPlugin().getQuestHistoryManager().saveCompletedQuest(questId.toString(), questData.getDisplayName(),
                    playerComponent.getPlayerMap(), questData.getDisplayItem());
        }

        ActiveQuests.getActiveQuestsInstance().endQuest(questId);
    }

    public void removeBossBar() {
        questBar.removeBossBar();
    }

    public boolean isCompetitive() {
        return (questData.getEventType().equals(EventType.COMPETITIVE));
    }

    public boolean isGoalQuest() {
        return (questData.getEventType().equals(EventType.COLLECTIVE));
    }

    private void updateBossBar() {
        double barProgress = questData.getPercentageComplete();
        questBar.updateBarProgress(barProgress);
    }

    public List<ObjectiveType> getObjectiveTypes() {
        return questData.getObjectiveTypes();
    }

    private final DecimalFormat decimalFormat = new DecimalFormat("#,###.##");

    private void sendPlayerMessage(Player player, double count) {
        if (!player.hasPermission("communityquests.showmessages"))
            return;

        String message = color(
                plugin.messages().message("contributionMessage", questData)
                        .replaceAll("contributionCount", decimalFormat.format(count)));
        player.sendMessage(message);
    }

    public void broadcast(@NonNull String messagePath) {
        String message = color(plugin.messages().message(messagePath, questData));
        plugin.getServer().broadcastMessage(message);
    }
}
