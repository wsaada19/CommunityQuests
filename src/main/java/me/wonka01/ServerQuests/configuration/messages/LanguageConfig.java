package me.wonka01.ServerQuests.configuration.messages;

import me.wonka01.ServerQuests.ServerQuests;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class LanguageConfig {
    private static LanguageConfig config;
    private YamlConfiguration yamlConfiguration;
    private Messages messages;


    public static LanguageConfig getConfig() {
        if (config == null) {
            config = new LanguageConfig();
        }
        return config;
    }

    private ServerQuests plugin;
    private File configFile;

    public LanguageConfig() {
        plugin = ServerQuests.getPlugin(ServerQuests.class);
        configFile = new File(plugin.getDataFolder(), "messages.yml");

        configFile = new File(plugin.getDataFolder(), "messages.yml");
        if (!configFile.exists()) {
            configFile.getParentFile().mkdirs();
            plugin.saveResource("messages.yml", false);
        }

        yamlConfiguration = new YamlConfiguration();
        try {
            yamlConfiguration.load(configFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Messages getMessages() {
        return messages;
    }

    public void setUpLanguageConfig() {
        String noPermission = yamlConfiguration.getString("noPermission");
        String invalidCommand = yamlConfiguration.getString("invalidCommand");
        String reloadCommand = yamlConfiguration.getString("reloadCommand");
        String helpMessage = yamlConfiguration.getString("helpMessage");
        String noDonateQuests = yamlConfiguration.getString("noActiveDonateQuests");
        String invalidQuestName = yamlConfiguration.getString("invalidQuestName");
        String invalidQuestType = yamlConfiguration.getString("invalidQuestType");
        String noActiveQuests = yamlConfiguration.getString("noActiveQuests");

        messages = new Messages(noPermission, invalidCommand, reloadCommand, helpMessage,
                noDonateQuests, invalidQuestName, invalidQuestType, noActiveQuests);
        config = this;
    }
}
