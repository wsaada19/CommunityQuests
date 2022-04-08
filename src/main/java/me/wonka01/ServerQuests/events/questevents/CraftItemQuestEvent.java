package me.wonka01.ServerQuests.events.questevents;

import me.wonka01.ServerQuests.enums.ObjectiveType;
import me.wonka01.ServerQuests.questcomponents.ActiveQuests;
import me.wonka01.ServerQuests.questcomponents.QuestController;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;

import java.util.List;

public class CraftItemQuestEvent extends QuestListener implements Listener {
    private final ObjectiveType TYPE = ObjectiveType.CRAFT_ITEM;

    public CraftItemQuestEvent(ActiveQuests activeQuests) {
        super(activeQuests);
    }

    @EventHandler
    public void onCraftItem(CraftItemEvent event) {
        Player player = (Player)event.getWhoClicked();

        int amount = event.getRecipe().getResult().getAmount();
        String materialName = event.getRecipe().getResult().getType().toString();
        Bukkit.getServer().broadcastMessage("Crafted item " + materialName + " with amount " + amount);

        List<QuestController> controllers = tryGetControllersOfEventType(TYPE);
        for (QuestController controller : controllers) {
            List<String> materials = controller.getEventConstraints().getMaterialNames();
            if (materials.isEmpty() || containsMaterial(materialName, materials)) {
                updateQuest(controller, player, amount);
            }
        }
    }

    private boolean containsMaterial(String material, List<String> materials) {
        for (String targetMaterial : materials) {
            if (material.contains(targetMaterial.toUpperCase())) {
                return true;
            }
        }
        return false;
    }
}
