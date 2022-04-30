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

    public void reloadConfig() {
        if (configFile == null){
            configFile = new File(plugin.getDataFolder(), "messages.yml");
        }
        yamlConfiguration = YamlConfiguration.loadConfiguration(configFile);
        setUpLanguageConfig();
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
        String questLimitReached = yamlConfiguration.getString("questLimitReached");

        String questComplete = yamlConfiguration.getString("questCompleteMessage");
        String questFailed = yamlConfiguration.getString("questFailureMessage");
        String questStart = yamlConfiguration.getString("questStartMessage");
        String contributionMessage = yamlConfiguration.getString("contributionMessage");
        String topContributorsTitle = yamlConfiguration.getString("topContributorsTitle");
        String rewardsTitle = yamlConfiguration.getString("rewardsTitle");
        String experience = yamlConfiguration.getString("experience");

        String competitive = yamlConfiguration.getString("competitive");
        String cooperative = yamlConfiguration.getString("cooperative");
        String goal = yamlConfiguration.getString("goal");
        String you = yamlConfiguration.getString("you");
        String leader = yamlConfiguration.getString("leader");
        String progress = yamlConfiguration.getString("progress");
        String cantDonate = yamlConfiguration.getString("cantDonateItem");
        String view = yamlConfiguration.getString("viewQuests");
        String start = yamlConfiguration.getString("startQuest");
        String stop = yamlConfiguration.getString("stopQuest");
        String typeMenu = yamlConfiguration.getString("typeMenu");
        String donateMenu = yamlConfiguration.getString("donateMenu");
        String endQuestText = yamlConfiguration.getString("endQuestText");
        String goBack = yamlConfiguration.getString("goBack");
        String goBackText = yamlConfiguration.getString("goBackText");
        String clickToStart = yamlConfiguration.getString("clickToStart");
        String cooperativeQuestWithoutGoalMessage = yamlConfiguration.getString("cooperativeQuestMustHaveAGoal", "&cCould not start quest: quests without a goal cannot be cooperative!");
        String timeRemaining = yamlConfiguration.getString("timeRemaining", "&fTime remaining: &e");
        String duration = yamlConfiguration.getString("duration", "Duration");

        messages = new Messages(noPermission, invalidCommand, reloadCommand, helpMessage,
                noDonateQuests, invalidQuestName, invalidQuestType, noActiveQuests, questComplete,
                questStart, contributionMessage, topContributorsTitle, rewardsTitle, questLimitReached, experience, competitive,
                cooperative, goal, you, leader, progress, cantDonate, view, start, stop, typeMenu, donateMenu,
                endQuestText, goBack, goBackText, clickToStart, cooperativeQuestWithoutGoalMessage, timeRemaining, questFailed,
                duration);
        config = this;
    }
}
