package me.wonka01.ServerQuests.questcomponents.bossbar;

import me.wonka01.ServerQuests.questcomponents.ActiveQuests;
import me.wonka01.ServerQuests.questcomponents.QuestController;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class BarManager implements Listener {

    private static final UUID[] questsToShow = new UUID[1];
    private static boolean disabled = false;

    public static void initializeDisplayBar() {
        ActiveQuests quests = ActiveQuests.getActiveQuestsInstance();

        int questsIndex = 0;
        for (QuestController quest : quests.getActiveQuestsList()) {
            if (!quest.getQuestData().hasGoal()) {
                continue;
            }
            questsToShow[questsIndex] = quest.getQuestId();
            questsIndex++;
            if (questsIndex >= questsToShow.length) {
                break;
            }
        }
    }

    public static void closeBar(UUID questId) {
        for (int i = 0; i < questsToShow.length; i++) {
            if (questsToShow[i] != null && questsToShow[i].equals(questId)) {
                questsToShow[i] = null;
            }
        }
    }

    public static void closeBar() {
        for (UUID questId : questsToShow) {
            if (questId != null) {
                QuestController controller = ActiveQuests.getActiveQuestsInstance().getQuestById(questId);
                if (controller != null) {
                    controller.removeBossBar();
                }
            }
        }
    }

    public static void toggleShowPlayerBar(Player player) {
        BossbarPlayerInfo.getInstance().togglebar(player.getUniqueId());

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
        if (disabled)
            return;

        if (BossbarPlayerInfo.getInstance().hasPlayer(player.getUniqueId())) {
            return;
        }
        for (UUID id : questsToShow) {
            QuestController controller = ActiveQuests.getActiveQuestsInstance().getQuestById(id);
            if (controller != null && controller.getQuestData().hasGoal()) {
                controller.getQuestBar().showBossBar(player);
            }
        }
    }

    // Start showing bar for new quest if more than 2 quests
    // are active, this needs to get called before the object is destroyed
    public static void startShowingPlayersBar(UUID questId) {
        QuestController controller = ActiveQuests.getActiveQuestsInstance().getQuestById(questId);
        if (controller == null || !isSlotFree() || !controller.getQuestData().hasGoal()) {
            return;
        }

        for (int i = 0; i < questsToShow.length; i++) {
            if (questsToShow[i] == null) {
                questsToShow[i] = questId;
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (BossbarPlayerInfo.getInstance().hasPlayer(player.getUniqueId())) {
                        continue;
                    }
                    startShowingPlayerBar(player);
                }
            }
        }
    }

    private static boolean isSlotFree() {
        for (UUID questId : questsToShow) {
            if (questId == null)
                return true;
        }
        return false;
    }

    public static void setDisableBossBar(boolean disabled) {
        BarManager.disabled = disabled;
    }

    @EventHandler
    public void onPlayerLogin(PlayerJoinEvent joinEvent) {
        if (disabled) {
            return;
        }
        startShowingPlayerBar(joinEvent.getPlayer());
    }

    @EventHandler
    public void onPlayerLogout(PlayerQuitEvent quitEvent) {
        if (disabled) {
            return;
        }
        stopShowingPlayerBar(quitEvent.getPlayer());
    }
}
