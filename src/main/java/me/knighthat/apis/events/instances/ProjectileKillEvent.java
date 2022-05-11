package me.knighthat.apis.events.instances;

import lombok.NonNull;
import me.knighthat.apis.events.instances.entity.EntityEvent;
import me.wonka01.ServerQuests.ServerQuests;
import me.wonka01.ServerQuests.enums.ObjectiveType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;

public class ProjectileKillEvent extends EntityEvent<EntityDeathEvent> {

    public ProjectileKillEvent(ServerQuests plugin, @NonNull ObjectiveType objectiveType) {
        super(plugin, objectiveType);
    }

    @Override
    @EventHandler
    public void event(EntityDeathEvent e) {

        LivingEntity entity = e.getEntity();

        if (!(entity.getLastDamageCause() instanceof EntityDamageByEntityEvent))
            return;

        EntityDamageByEntityEvent event = (EntityDamageByEntityEvent) entity.getLastDamageCause();
        if (!(event.getDamager() instanceof Projectile)) return;

        Projectile projectile = (Projectile) event.getDamager();
        if (!(projectile.getShooter() instanceof Player)) return;

        attemptToUpdate((Player) projectile.getShooter(), entity);
    }
}
