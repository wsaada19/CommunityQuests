package me.knighthat.apis.files;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import me.wonka01.ServerQuests.ServerQuests;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.logging.Level;

public abstract class PluginFiles {

    @Getter(value = AccessLevel.PROTECTED)
    private final ServerQuests plugin;

    private File file;
    private FileConfiguration yaml;

    protected PluginFiles(ServerQuests plugin) {
        this.plugin = plugin;
        startup();
    }

    protected @NonNull String getFileName() {
        return getClass().getSimpleName().toLowerCase(Locale.ROOT) + ".yml";
    }

    private void startup() {

        file = new File(plugin.getDataFolder(), getFileName());
        createIfNotExist();

        reload();
    }

    public void reload() {

        if (file == null)
            startup();

        yaml = YamlConfiguration.loadConfiguration(file);
    }

    public @NonNull FileConfiguration get() {

        if (yaml == null)
            reload();

        return yaml;
    }

    public boolean save() {

        if (file == null || yaml == null)
            reload();

        try {

            yaml.save(file);

            return true;
        } catch (IOException e) {

            String message = MessageFormat.format("Could not save {0} due to: {1}", getFileName(), e.getMessage());
            Bukkit.getLogger().log(Level.FINER, message);
            e.printStackTrace();
            return false;
        }
    }

    private void createIfNotExist() {

        if (!file.exists())
            plugin.saveResource(getFileName(), false);
    }
}
