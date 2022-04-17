package me.wonka01.ServerQuests.events.questevents;

import me.wonka01.ServerQuests.enums.ObjectiveType;
import me.wonka01.ServerQuests.questcomponents.ActiveQuests;
import me.wonka01.ServerQuests.questcomponents.QuestController;
import me.wonka01.ServerQuests.util.MaterialUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;

import java.util.List;

public class ConsumeItemQuestEvent extends QuestListener implements Listener {
    private final ObjectiveType TYPE = ObjectiveType.CONSUME_ITEM;

    public ConsumeItemQuestEvent(ActiveQuests activeQuests) {
        super(activeQuests);
    }

    @EventHandler
    public void onConsumeItem(PlayerItemConsumeEvent event) {
        Player player = event.getPlayer();
        String materialName = event.getItem().getType().toString();

        List<QuestController> controllers = tryGetControllersOfEventType(TYPE);
        for (QuestController controller : controllers) {
            List<String> materials = controller.getEventConstraints().getMaterialNames();
            if (materials.isEmpty() || MaterialUtil.containsMaterial(materialName, materials)) {
                updateQuest(controller, player, 1);
            }
        }
    }
}
