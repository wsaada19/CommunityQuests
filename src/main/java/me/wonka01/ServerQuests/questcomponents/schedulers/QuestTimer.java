package me.wonka01.ServerQuests.questcomponents.schedulers;

import me.wonka01.ServerQuests.ServerQuests;
import me.wonka01.ServerQuests.questcomponents.QuestController;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

public class QuestTimer {
    // checks every 2 seconds to see if the quest is complete (TODO make this configurable)
    private final long IntervalInSeconds = 2L;

    private QuestController controller;
    private BukkitTask task;

    public QuestTimer(QuestController questController) {
        controller = questController;

        if (controller.getQuestData().getQuestDuration() <= 0) {
            throw new IllegalArgumentException("A quest timer cannot be initiated if the time remaining is 0 or negative.");
        }
        createScheduler();
    }

    private void createScheduler() {
        BukkitScheduler scheduler = Bukkit.getScheduler();
        this.task = scheduler.runTaskTimer(JavaPlugin.getPlugin(ServerQuests.class), () -> {
            if (controller.getQuestData().isGoalComplete()) {
                task.cancel();
            }

            controller.getQuestData().decreaseDuration((int) IntervalInSeconds);
            if (controller.getQuestData().getQuestDuration() <= 0) {
                controller.endQuest();
                task.cancel();
            }
        }, 0L, 20L * IntervalInSeconds);
    }
}
