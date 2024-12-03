package me.wonka01.ServerQuests.events;

import me.wonka01.ServerQuests.enums.ObjectiveType;
import me.wonka01.ServerQuests.objectives.Objective;
import me.wonka01.ServerQuests.objectives.ObjectiveFilters;
import me.wonka01.ServerQuests.questcomponents.ActiveQuests;
import me.wonka01.ServerQuests.questcomponents.QuestController;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

// TODO - rename updateQuest functions to be specific to their type
public abstract class QuestListener {
    protected ActiveQuests activeQuests;

    public QuestListener(ActiveQuests activeQuests) {
        this.activeQuests = activeQuests;
    }

    protected boolean updateQuest(QuestController controller, Player player, double amount, Objective obj,
            int objectiveId) {
        if (!isEnabledInWorld(controller.getWorlds(), player.getWorld().getName())) {
            return false;
        }
        return controller.updateQuest(amount, player, obj, objectiveId);
    }

    // General quests without filters
    protected void updateQuest(QuestController controller, Player player, double amount, ObjectiveType type) {
        List<Objective> objectives = controller.getQuestData().getObjectives();
        for (int i = 0; i < objectives.size(); i++) {
            Objective objective = objectives.get(i);
            if (objective.getType().equals(type)) {
                updateQuest(controller, player, amount, objective, i);
            }
        }
    }

    // Mob quests
    protected void updateQuest(QuestController controller, Player player, double amount, ObjectiveType type,
            Entity entity) {
        List<Objective> objectives = controller.getQuestData().getObjectives();
        for (int i = 0; i < objectives.size(); i++) {
            Objective objective = objectives.get(i);
            boolean matches = ObjectiveFilters.filter()
                    .withEntity(entity)
                    .withType(type)
                    .matches(objective);

            if (matches) {
                updateQuest(controller, player, amount, objective, i);
            }

            // if (objective.getType().equals(type)) {
            // if (objective.getMobNames().isEmpty()) {
            // if (objective.getCustomNames().isEmpty()) {
            // updateQuest(controller, player, amount, objective, i);
            // } else if (Utils.contains(objective.getCustomNames(), customName)) {
            // updateQuest(controller, player, amount, objective, i);
            // }
            // } else if (Utils.contains(objective.getMobNames(), mobName)) {
            // updateQuest(controller, player, amount, objective, i);
            // }
            // }
        }
    }

    // Mob quests with entity as a string
    protected void updateQuest(QuestController controller, Player player, double amount, ObjectiveType type,
            String entity) {
        List<Objective> objectives = controller.getQuestData().getObjectives();
        for (int i = 0; i < objectives.size(); i++) {
            Objective objective = objectives.get(i);
            boolean matches = ObjectiveFilters.filter()
                    .withEntity(entity)
                    .withType(type)
                    .matches(objective);

            if (matches) {
                updateQuest(controller, player, amount, objective, i);
            }
        }
    }

    // Potion quests
    protected void updateQuest(QuestController controller, Player player, double amount, ObjectiveType type,
            PotionType potion) {
        List<Objective> objectives = controller.getQuestData().getObjectives();
        for (int i = 0; i < objectives.size(); i++) {
            Objective objective = objectives.get(i);
            boolean matches = ObjectiveFilters.filter()
                    .withPotion(potion)
                    .withType(type)
                    .matches(objective);

            if (matches) {
                updateQuest(controller, player, amount, objective, i);
            }
        }
    }

    // enchantment quests
    protected void updateQuest(QuestController controller, Player player, double amount, ObjectiveType type,
            ItemStack item,
            Map<Enchantment, Integer> enchantments) {
        List<Objective> objectives = controller.getQuestData().getObjectives();
        for (int i = 0; i < objectives.size(); i++) {
            Objective objective = objectives.get(i);
            boolean matches = ObjectiveFilters.filter()
                    .withMaterial(item.getType()) // only check material bc of how we're using customItem names atm
                    .withEnchantments(enchantments)
                    .withType(type)
                    .matches(objective);

            if (matches) {
                updateQuest(controller, player, amount, objective, i);
            }
        }
    }

    // Block quests
    protected void updateQuest(QuestController controller, Player player, double amount, ObjectiveType type,
            Material material) {
        List<Objective> objectives = controller.getQuestData().getObjectives();
        for (int i = 0; i < objectives.size(); i++) {
            Objective objective = objectives.get(i);
            boolean matches = ObjectiveFilters.filter()
                    .withMaterial(material)
                    .withType(type)
                    .matches(objective);

            if (matches) {
                updateQuest(controller, player, amount, objective, i);
            }
        }
    }

    // Item quests
    protected void updateQuest(QuestController controller, Player player, int amount, ObjectiveType type,
            ItemStack item) {
        List<Objective> objectives = controller.getQuestData().getObjectives();
        for (int i = 0; i < objectives.size(); i++) {
            Objective objective = objectives.get(i);
            boolean matches = ObjectiveFilters.filter()
                    .withItem(item)
                    .withType(type)
                    .matches(objective);

            if (matches) {
                updateQuest(controller, player, amount, objective, i);
            }
        }
    }

    protected List<QuestController> tryGetControllersOfObjectiveType(ObjectiveType type) {
        List<QuestController> controllers = new ArrayList<>();
        for (QuestController controller : activeQuests.getActiveQuestsList()) {
            if (controller.getObjectiveTypes().contains(type)) {
                controllers.add(controller);
            }
        }
        return controllers;
    }

    private boolean isEnabledInWorld(List<String> worldList, String world) {
        return worldList.isEmpty() || worldList.contains(world);
    }
}
