package me.wonka01.ServerQuests.events;

import me.wonka01.ServerQuests.enums.ObjectiveType;
import me.wonka01.ServerQuests.questcomponents.ActiveQuests;
import me.wonka01.ServerQuests.questcomponents.QuestController;
import me.wonka01.ServerQuests.util.EntityUtil;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.List;

public class ProjectileKillEvent extends QuestListener implements Listener {

    private final ObjectiveType TYPE = ObjectiveType.PROJ_KILL;

    public ProjectileKillEvent(ActiveQuests activeQuests) {
        super(activeQuests);
    }

    @EventHandler
    public void onProjectileKill(EntityDeathEvent event) {
        if (!(event.getEntity().getLastDamageCause() instanceof EntityDamageByEntityEvent)) {
            return;
        }

        EntityDamageByEntityEvent damageEvent = (EntityDamageByEntityEvent) event.getEntity().getLastDamageCause();
        Entity damager = damageEvent.getDamager();

        if (damager instanceof Projectile) {
            Projectile projectile = (Projectile) damager;
            if (projectile.getShooter() != null && projectile.getShooter() instanceof Player) {
                Player player = (Player) projectile.getShooter();

                List<QuestController> controllers = tryGetControllersOfEventType(TYPE);
                for (QuestController controller : controllers) {
                    List<String> entities = controller.getEventConstraints().getMobNames();
                    if(entities.isEmpty() || EntityUtil.containsEntity(event.getEntity().getType().toString(), entities)) {
                        updateQuest(controller, player, 1);
                    }
                }
            }
        }

    }

}
