package me.wonka01.ServerQuests.questcomponents.schedulers;

import me.wonka01.ServerQuests.ServerQuests;
import me.wonka01.ServerQuests.questcomponents.QuestController;
import me.wonka01.ServerQuests.questcomponents.QuestData;
import org.bukkit.scheduler.BukkitRunnable;

public class QuestTimer {
    // checks every 2 seconds to see if the quest is complete (TODO make this configurable)
    private final int INTERVAL = 2; // in seconds

    private final QuestController controller;

    public QuestTimer(ServerQuests plugin, QuestController questController) {
        controller = questController;

        if (controller.getQuestData().getQuestDuration() <= 0) {
            throw new IllegalArgumentException("A quest timer cannot be initiated if the time remaining is 0 or negative.");
        }
        createScheduler(plugin);
    }

    private void createScheduler(ServerQuests plugin) {

        new BukkitRunnable() {

            final QuestData data = controller.getQuestData();

            @Override
            public void run() {

                if (!data.isGoalComplete()) {

                    data.decreaseDuration(INTERVAL);
                    if (data.getQuestDuration() <= 0) {

                        controller.endQuest();
                        this.cancel();
                    }
                } else
                    this.cancel();
            }
        }.runTaskTimer(plugin, 0L, 20L * INTERVAL);
    }
}
