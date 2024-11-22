package me.wonka01.ServerQuests.questcomponents.hologram;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.checkerframework.checker.units.qual.C;

import eu.decentsoftware.holograms.api.DHAPI;
import eu.decentsoftware.holograms.api.actions.ClickType;
import eu.decentsoftware.holograms.api.holograms.Hologram;
import eu.decentsoftware.holograms.event.HologramClickEvent;
import me.wonka01.ServerQuests.ServerQuests;
import me.wonka01.ServerQuests.gui.ViewMenu;

public class DecentHologramsDisplay {
    private FileConfiguration config;
    private Hologram holo;
    private ServerQuests plugin;

    public DecentHologramsDisplay(ServerQuests plugin) {
        this.config = plugin.config().get();
        this.plugin = plugin;
        this.holo = null;
    }

    public void reloadConfig() {
        this.config = plugin.config().get();
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
            DHAPI.addHologramLine(holo, line);
        }
    }

    public void removeHologram() {
        if (holo != null) {
            holo.delete();
        }
    }

    public void onPlayerClick(HologramClickEvent event) {
        if (event.getClick().equals(ClickType.RIGHT)) {
            new ViewMenu(plugin, event.getPlayer()).open();
        }
    }
}
