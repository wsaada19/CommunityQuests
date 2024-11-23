package me.wonka01.ServerQuests.questcomponents.hologram;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import eu.decentsoftware.holograms.api.DHAPI;
import eu.decentsoftware.holograms.api.actions.ClickType;
import eu.decentsoftware.holograms.api.holograms.Hologram;
import eu.decentsoftware.holograms.event.HologramClickEvent;
import me.clip.placeholderapi.PlaceholderAPI;
import me.wonka01.ServerQuests.ServerQuests;
import me.wonka01.ServerQuests.gui.ViewMenu;

public class DecentHologramsDisplay implements Listener {
    private FileConfiguration config;
    private Hologram holo;
    private ServerQuests plugin;
    private BukkitTask refreshTask;

    public DecentHologramsDisplay(ServerQuests plugin) {
        this.config = plugin.config().get();
        this.plugin = plugin;
        this.holo = null;
        startRefreshTimer();
    }

    public void reloadConfig() {
        this.config = plugin.config().get();
        // Restart timer with new config values
        stopRefreshTimer();
        startRefreshTimer();
    }

    private void startRefreshTimer() {
        long refreshInterval = config.getLong("hologram.refresh-interval", 0L);
        if (refreshInterval == 0) {
            return;
        }
        long refreshTicks = refreshInterval * 20L;

        refreshTask = new BukkitRunnable() {
            @Override
            public void run() {
                displayHologram();
            }
        }.runTaskTimer(plugin, refreshTicks, refreshTicks);
    }

    private void stopRefreshTimer() {
        if (refreshTask != null && !refreshTask.isCancelled()) {
            refreshTask.cancel();
            refreshTask = null;
        }
    }

    public void displayHologram() {
        if (holo != null) {
            holo.delete();
        }
        Location loc = new Location(Bukkit.getWorld(config.getString("hologram.location.world")),
                config.getDouble("hologram.location.x"), config.getDouble("hologram.location.y"),
                config.getDouble("hologram.location.z"));
        holo = DHAPI.createHologram("ServerQuestsHologram", loc);
        for (String line : config.getStringList("hologram.text")) {
            // Kinda messy but this enabled new lines from placeholders to be used which
            // is helpful for showing leaderboard and multi line descriptions
            if (line.contains("%communityquests")) {
                String placeholderValue = PlaceholderAPI.setPlaceholders(null, line);
                String[] lines = placeholderValue.split("\\\\n|\\r?\\n");
                for (String l : lines) {
                    DHAPI.addHologramLine(holo, l);
                }
            } else {
                DHAPI.addHologramLine(holo, line);
            }
        }
    }

    public void removeHologram() {
        stopRefreshTimer();
        if (holo != null) {
            holo.delete();
        }
    }

    @EventHandler
    public void onPlayerClick(HologramClickEvent event) {
        if (event.getClick().equals(ClickType.RIGHT)) {
            Bukkit.getScheduler().runTask(plugin, () -> {
                new ViewMenu(plugin, event.getPlayer()).open();
            });
        }
    }
}