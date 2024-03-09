package me.wonka01.ServerQuests;

import lombok.Getter;
import lombok.NonNull;
import me.knighthat.apis.files.Config;
import me.knighthat.apis.files.Messages;
import me.knighthat.apis.menus.MenuEvents;
import me.wonka01.ServerQuests.commands.CommandManager;
import me.wonka01.ServerQuests.configuration.JsonQuestSave;
import me.wonka01.ServerQuests.events.*;
import me.wonka01.ServerQuests.questcomponents.ActiveQuests;
import me.wonka01.ServerQuests.questcomponents.bossbar.BarManager;
import me.wonka01.ServerQuests.questcomponents.rewards.RewardManager;
import me.wonka01.placeholders.CommunityQuestsPlaceholders;
import net.milkbowl.vault.economy.Economy;

import org.bukkit.Bukkit;

import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import io.lumine.mythic.bukkit.MythicBukkit;

public class ServerQuests extends JavaPlugin {

    private final @NonNull Config config = new Config(this);
    private final @NonNull Messages messages = new Messages(this);
    @Getter
    private Economy economy;
    private MythicBukkit mythicBukkit;
    private JsonQuestSave jsonSave;

    @Override
    public void onEnable() {
        new CommandManager(this);

        loadSaveData();

        if (!setupEconomy()) {
            getLogger().info(
                    "Warning! No economy plugin found, a cash reward can not be added to a quest in Community Quests.");
        }

        if (!setupMythicMobs()) {
            getLogger().info("Warning! MythicMobs not found, MythicMobs events will not work.");
        }

        registerPlaceholders();
        registerGuiEvents();
        registerQuestEvents();
        RewardManager.getInstance().populateFromJsonFile(getDataFolder(), getLogger());
        getLogger().info("Plugin is enabled");
    }

    @Override
    public void onDisable() {
        jsonSave.saveQuestsInProgress();
        RewardManager.getInstance().saveToJsonFile(getDataFolder());
        BarManager.closeBar();
        getLogger().info("Plugin is disabled");
    }

    public @NonNull Config config() {
        return this.config;
    }

    public @NonNull Messages messages() {
        return this.messages;
    }

    private void loadSaveData() {
        jsonSave = new JsonQuestSave(this, getDataFolder());
        if (jsonSave.getOrCreateQuestFile()) {
            jsonSave.readAndInitializeQuests();
            BarManager.initializeDisplayBar();
        }
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        economy = rsp.getProvider();
        return economy != null;
    }

    private boolean setupMythicMobs() {
        mythicBukkit = (MythicBukkit) Bukkit.getPluginManager().getPlugin("MythicMobs");
        return mythicBukkit != null;
    }

    private void registerQuestEvents() {
        ActiveQuests activeQuests = config.getActiveQuests();
        getServer().getPluginManager().registerEvents(new BarManager(), this);
        getServer().getPluginManager().registerEvents(new BreakEvent(activeQuests), this);
        getServer().getPluginManager().registerEvents(new CatchFishEvent(activeQuests), this);
        getServer().getPluginManager().registerEvents(new KillPlayerEvent(activeQuests), this);
        getServer().getPluginManager().registerEvents(new MobKillEvent(activeQuests), this);
        getServer().getPluginManager().registerEvents(new ProjectileKillEvent(activeQuests), this);
        getServer().getPluginManager().registerEvents(new PlaceEvent(activeQuests), this);
        getServer().getPluginManager().registerEvents(new ShearEvent(activeQuests), this);
        getServer().getPluginManager().registerEvents(new TameEvent(activeQuests), this);
        getServer().getPluginManager().registerEvents(new MilkCowEvent(activeQuests), this);
        getServer().getPluginManager().registerEvents(new CraftItemQuestEvent(activeQuests), this);
        getServer().getPluginManager().registerEvents(new ConsumeItemQuestEvent(activeQuests), this);
        getServer().getPluginManager().registerEvents(new EnchantItemQuestEvent(activeQuests), this);
        try {
            getServer().getPluginManager().registerEvents(new ExperienceEvent(activeQuests), this);
            getServer().getPluginManager().registerEvents(new HarvestEvent(activeQuests), this);
        } catch (Error e) {
            getLogger().info("Warning! No class found, Harvest and Experience events will not work.");
        }
        if (mythicBukkit != null) {
            getServer().getPluginManager().registerEvents(new MythicMobKillEvent(activeQuests), this);
        }
    }

    private void registerGuiEvents() {
        getServer().getPluginManager().registerEvents(new MenuEvents(), this);
    }

    private void registerPlaceholders() {
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new CommunityQuestsPlaceholders().register();
        }
    }
}
