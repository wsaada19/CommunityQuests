package me.wonka01.ServerQuests.configuration;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;

import me.wonka01.ServerQuests.ServerQuests;

public class JsonQuestHistory {
    private final ServerQuests plugin;
    private final File path;

    public JsonQuestHistory(ServerQuests plugin, File path) {
        this.plugin = plugin;
        this.path = new File(path + "/questSave.json");
    }

    public boolean getOrCreateQuestFile() {
        if (path.exists()) {
            return true;
        } else {
            try {
                path.createNewFile();
            } catch (IOException e) {
                Bukkit.getServer().getConsoleSender().sendMessage(e.getMessage());
            }
        }
        return false;
    }
}
