package me.wonka01.ServerQuests.objectives;

import java.util.List;

import org.bukkit.Material;

import lombok.Getter;
import me.wonka01.ServerQuests.enums.ObjectiveType;

@Getter
public class Objective {
    private final ObjectiveType type;
    private final double goal;
    private double amountComplete;
    private List<String> mobNames;
    private List<Material> materials;

    public Objective(ObjectiveType type, double goal, double amountComplete, List<String> mobNames,
            List<Material> materials) {
        this.type = type;
        this.goal = goal;
        this.amountComplete = amountComplete;
        this.mobNames = mobNames;
        this.materials = materials;
    }

    public boolean isGoalComplete() {
        return amountComplete >= goal;
    }

    public void addToObjectiveProgress(double amount) {
        amountComplete += amount;
    }

    public void addToObjectiveProgress(double amount, Material material) {
        if (materials.contains(material)) {
            addToObjectiveProgress(amount);
        }
    }

    public void addToObjectiveProgress(double amount, String mobName) {
        if (mobNames.contains(mobName)) {
            addToObjectiveProgress(amount);
        }
    }

    public boolean hasGoal() {
        return goal > 0;
    }

}
