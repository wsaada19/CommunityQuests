package me.wonka01.ServerQuests.events.entities;

import me.knighthat.apis.events.EntityEvent;
import me.wonka01.ServerQuests.ServerQuests;
import me.wonka01.ServerQuests.enums.ObjectiveType;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;

public class ProjectileKillEvent extends EntityEvent<EntityDeathEvent> {

    public ProjectileKillEvent(ServerQuests plugin) {
        super(plugin, ObjectiveType.PROJ_KILL);
    }

    @Override
    @EventHandler
    public void event(EntityDeathEvent event) {

        try {

            LivingEntity target = event.getEntity();

            if (!target.getLastDamageCause().getCause().equals(EntityDamageEvent.DamageCause.PROJECTILE))
                return;

            EntityDamageByEntityEvent e = (EntityDamageByEntityEvent) target.getLastDamageCause();
            Projectile projectile = (Projectile) e.getDamager();
            if (!(projectile.getShooter() instanceof Player)) return;

            super.attemptToUpdate((HumanEntity) projectile.getShooter(), target.getType());

        } catch (NullPointerException ignored) {
        }
    }
}
