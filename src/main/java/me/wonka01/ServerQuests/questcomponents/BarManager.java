package me.wonka01.ServerQuests.questcomponents;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class BarManager implements Listener {

    private static UUID[] questsToShow = new UUID[2];

    public static void stopShowingPlayersBar(UUID questId) {
        if (!isSlotFree()) {
            return;
        }
        QuestController controller = ActiveQuests.getActiveQuestsInstance().getQuestById(questId);
        if (controller == null) {
            return;
        }

        controller.getQuestBar().removeBossBar();

        for (int i = 0; i < questsToShow.length; i++) {
            if (questId.equals(questsToShow[i])) {
                questsToShow[i] = null;
                break;
            }
        }
    }

    // Start showing bar for new quest if more than 2 quests
    // are active, this needs to get called before the object is destroyed
    public static void startShowingPlayersBar(UUID questId) {
        QuestController controller = ActiveQuests.getActiveQuestsInstance().getQuestById(questId);
        if (controller == null) {
            return;
        }

        for (int i = 0; i < questsToShow.length; i++) {
            if (questsToShow[i] == null) {
                questsToShow[i] = questId;
                break;
            }
        }

        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            controller.getQuestBar().showBossBar(player);
        }
    }

    private static boolean isSlotFree() {
        for (int i = 0; i < questsToShow.length; i++) {
            if (questsToShow[i] == null) {
                return true;
            }
        }
        return false;
    }

    @EventHandler
    public void onPlayerLogin(PlayerJoinEvent joinEvent) {
        for (int i = 0; i < questsToShow.length; i++) {
            if (questsToShow[i] == null) {
                continue;
            }
            QuestController controller = ActiveQuests.getActiveQuestsInstance().getQuestById(questsToShow[i]);
            controller.getQuestBar().showBossBar(joinEvent.getPlayer());
        }
    }

    @EventHandler
    public void onPlayerLogout(PlayerQuitEvent quitEvent) {
        for (int i = 0; i < questsToShow.length; i++) {
            if (questsToShow[i] == null) {
                continue;
            }
            QuestController controller = ActiveQuests.getActiveQuestsInstance().getQuestById(questsToShow[i]);
            controller.getQuestBar().hideBossBar(quitEvent.getPlayer());
        }
    }
}
