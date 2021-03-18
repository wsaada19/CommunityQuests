package me.wonka01.ServerQuests.questcomponents;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

// This class smells pretty bad
public class BarManager implements Listener {

    private static final String HIDE_BAR_PERMISSION = "serverevents.bossbar.hide";
    private static UUID[] questsToShow = new UUID[2];

    public static void initializeDisplayBar() {
        ActiveQuests quests = ActiveQuests.getActiveQuestsInstance();

        int questsIndex = 0;
        for (QuestController quest : quests.getActiveQuestsList()) {
            questsToShow[questsIndex] = quest.getQuestId();
            questsIndex++;
            if (questsIndex > 1) {
                break;
            }
        }
    }

    public static void closeBar(UUID questId) {
        for(int i = 0; i < questsToShow.length; i++) {
            if(questsToShow[i] != null && questsToShow[i].equals(questId)) {
                questsToShow[i] = null;
            }
        }
    }

    public static void closeBar() {
        for (UUID questId : questsToShow) {
            if (questId != null) {
                QuestController controller = ActiveQuests.getActiveQuestsInstance().getQuestById(questId);
                if(controller != null){
                    controller.removeBossBar();
                }
            }
        }
    }

    public static void toggleShowPlayerBar(Player player) {
        for (UUID id : questsToShow) {
            QuestController controller = ActiveQuests.getActiveQuestsInstance().getQuestById(id);
            if (controller != null) {
                controller.getQuestBar().toggleBossBar(player);
            }
        }
    }

    public static void stopShowingPlayerBar(Player player) {
        for (UUID id : questsToShow) {
            QuestController controller = ActiveQuests.getActiveQuestsInstance().getQuestById(id);
            if (controller != null) {
                controller.getQuestBar().hideBossBar(player);
            }
        }
    }

    public static void startShowingPlayerBar(Player player) {
        for (UUID id : questsToShow) {
            QuestController controller = ActiveQuests.getActiveQuestsInstance().getQuestById(id);
            if (controller != null) {
                controller.getQuestBar().showBossBar(player);
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

        if (isSlotFree()) {
            int openSlot = 0;
            for (int i = 0; i < questsToShow.length; i++) {
                if (questsToShow[i] == null) {
                    openSlot = i;
                }
            }

            questsToShow[openSlot] = controller.getQuestId();
            for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                if (player.getPlayer().hasPermission(HIDE_BAR_PERMISSION)) {
                    return;
                }
                controller.getQuestBar().showBossBar(player);
            }
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
        if (joinEvent.getPlayer().hasPermission(HIDE_BAR_PERMISSION)) {
            return;
        }
        startShowingPlayerBar(joinEvent.getPlayer());
    }

    @EventHandler
    public void onPlayerLogout(PlayerQuitEvent quitEvent) {
        if (quitEvent.getPlayer().hasPermission(HIDE_BAR_PERMISSION)) {
            return;
        }
        stopShowingPlayerBar(quitEvent.getPlayer());
    }
}
