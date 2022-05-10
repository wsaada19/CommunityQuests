package me.wonka01.ServerQuests.configuration;

import lombok.Getter;
import me.wonka01.ServerQuests.enums.ObjectiveType;
import me.wonka01.ServerQuests.questcomponents.rewards.Reward;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

@Getter
public class QuestModel {

    private final String questId;
    private final String displayName;
    private final String eventDescription;
    private final int completeTime;
    private final int questGoal;
    private final ObjectiveType objective;
    private final List<String> mobNames;
    private final ArrayList<Reward> rewards;
    private final List<String> itemNames;
    private Material displayItem;
    private List<String> worlds;

    public QuestModel(String questId, String displayName, String eventDescription,
                      int completeTime, int questGoal, ObjectiveType objective,
                      List<String> mobNames, ArrayList<Reward> rewards, List<String> itemNames, String displayItem, List<String> worlds) {
        this.questId = questId;
        this.displayName = displayName;
        this.eventDescription = eventDescription;
        this.completeTime = completeTime;
        this.questGoal = questGoal;
        this.objective = objective;
        this.mobNames = mobNames;
        this.rewards = rewards;
        this.itemNames = itemNames;
        this.worlds = worlds;
        this.displayItem = Material.getMaterial(displayItem.toUpperCase());
        if (this.displayItem == null)
            this.displayItem = objective.getDefaultMaterial();
    }
}
