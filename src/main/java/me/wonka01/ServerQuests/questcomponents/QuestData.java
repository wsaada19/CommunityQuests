package me.wonka01.ServerQuests.questcomponents;

import lombok.Getter;
import lombok.NonNull;
import me.clip.placeholderapi.PlaceholderAPI;
import me.knighthat.apis.utils.Colorization;
import me.wonka01.ServerQuests.ServerQuests;
import me.wonka01.ServerQuests.enums.EventType;
import me.wonka01.ServerQuests.enums.ObjectiveType;
import me.wonka01.ServerQuests.objectives.Objective;

import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.ChatColor;
import org.bukkit.Material;

import eu.decentsoftware.holograms.api.utils.scheduler.S;

@Getter
public class QuestData implements Colorization {

    private final @NonNull String questId;
    private final String questType;
    private final String displayName;
    private final String description;
    private final Material displayItem;
    private final @NonNull EventType eventType = EventType.COLLAB;
    private int questDuration;
    private String afterQuestCommand;
    private String beforeQuestCommand;
    private List<Objective> objectives;
    private String questFailedCommand;

    public QuestData(String displayName, String description, String questType,
            int questDuration, Material displayItem, @NonNull String questId, String afterQuestCommand,
            String beforeQuestCommand, List<Objective> objectives, String questFailedCommand) {
        this.questDuration = questDuration;
        this.displayName = displayName;
        this.description = description == null ? "" : description;
        this.questId = questId;
        this.questType = questType;
        this.displayItem = displayItem;
        this.afterQuestCommand = afterQuestCommand;
        this.beforeQuestCommand = beforeQuestCommand;
        this.objectives = objectives;
        this.questFailedCommand = questFailedCommand;
    }

    public double getAmountCompleted() {
        return objectives.stream().mapToDouble(Objective::getAmountComplete).sum();
    }

    public double getQuestGoal() {
        return objectives.stream().mapToDouble(Objective::getGoal).sum();
    }

    public String getDescription() {
        String placeholderDescription = description;
        if (ServerQuests.getPlugin(ServerQuests.class).isPlaceholderApiEnabled()) {
            placeholderDescription = PlaceholderAPI.setPlaceholders(null, description);
        }

        return placeholderDescription;
    }

    public String[] getDescriptionArr() {
        if (getDescription() == null)
            return new String[0];

        return getDescription().split("\\\\n|\\r?\\n");
    }

    public String getDisplayName() {
        String placeholderDisplayName = displayName;
        if (ServerQuests.getPlugin(ServerQuests.class).isPlaceholderApiEnabled()) {
            placeholderDisplayName = PlaceholderAPI.setPlaceholders(null, displayName);
        }

        return placeholderDisplayName;
    }

    public double getAmountCompletedByType(ObjectiveType type) {
        return objectives.stream().filter(objective -> objective.getType() == type)
                .mapToDouble(Objective::getAmountComplete)
                .sum();
    }

    public double getQuestGoalByType(ObjectiveType type) {
        double result = objectives.stream().filter(objective -> objective.getType() == type)
                .mapToDouble(Objective::getGoal)
                .sum();
        return result;
    }

    public double getPercentageComplete() {
        return getAmountCompleted() / getQuestGoal();
    }

    public void addToQuestProgress(double amountToIncrease, Objective objective) {
        objective.addToObjectiveProgress(amountToIncrease);
    }

    public List<ObjectiveType> getObjectiveTypes() {
        return objectives.stream().map(Objective::getType).collect(Collectors.toList());
    }

    public void decreaseDuration(int amountToDecrease) {
        questDuration -= amountToDecrease;
    }

    public boolean isGoalComplete(Objective objective) {
        return objective.isGoalComplete();
    }

    // Always false if no goal is set and the quest is using a timer...
    public boolean isGoalComplete() {
        return (hasGoal() && getAmountCompleted() >= getQuestGoal());
    }

    public boolean hasGoal() {
        return getQuestGoal() > 0;
    }

    public String getProgressIndicator() {
        StringBuilder speedDisplay = new StringBuilder();
        double ratio = getAmountCompleted() / getQuestGoal();

        int result = 40 - (int) (40.0 - (ratio * 40.0));
        for (int i = 0; i < 40; i++) {
            if (i < result) {
                speedDisplay.append(ChatColor.GREEN);
                speedDisplay.append("|");
            } else {
                speedDisplay.append(ChatColor.GRAY);
                speedDisplay.append("|");
            }
        }
        return color(speedDisplay.toString());
    }
}
