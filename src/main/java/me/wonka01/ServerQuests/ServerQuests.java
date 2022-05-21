package me.wonka01.ServerQuests;

import lombok.Getter;
import lombok.NonNull;
import me.knighthat.apis.files.Config;
import me.knighthat.apis.files.Messages;
import me.knighthat.apis.menus.MenuEvents;
import me.wonka01.ServerQuests.commands.CommandManager;
import me.wonka01.ServerQuests.configuration.JsonQuestSave;
import me.wonka01.ServerQuests.events.entities.*;
import me.wonka01.ServerQuests.events.materials.ConsumeEvent;
import me.wonka01.ServerQuests.events.materials.CraftingEvent;
import me.wonka01.ServerQuests.events.materials.EnchantEvent;
import me.wonka01.ServerQuests.events.materials.blocks.BreakEvent;
import me.wonka01.ServerQuests.events.materials.blocks.PlaceEvent;
import me.wonka01.ServerQuests.questcomponents.bossbar.BarManager;
import me.wonka01.placeholders.CommunityQuestsPlaceholders;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class ServerQuests extends JavaPlugin {

    private final @NonNull Config config = new Config(this);
    private final @NonNull Messages messages = new Messages(this);
    @Getter
    private Economy economy;
    private JsonQuestSave jsonSave;

    @Override
    public void onEnable() {
        new CommandManager(this);

        loadSaveData();

        if (!setupEconomy()) {
            getLogger().info("Warning! No economy plugin found, a cash reward can not be added to a quest in Community Quests.");
        }

        registerPlaceholders();
        registerGuiEvents();
        registerQuestEvents();


        getLogger().info("Plugin is enabled");
    }

    @Override
    public void onDisable() {
        jsonSave.saveQuestsInProgress();
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

    private void registerQuestEvents() {

        new MobKillEvent(this);
        new BreakEvent(this);
        new CraftingEvent(this);
        new KillPlayerEvent(this);
        new MilkingEvent(this);
        new PlaceEvent(this);
        new ProjectileKillEvent(this);
        new ShearEvent(this);
        new TameEvent(this);
        new ConsumeEvent(this);
        new CatchFishEvent(this);
        new EnchantEvent(this);
        new NPCInteractEvent(this);

        getServer().getPluginManager().registerEvents(new BarManager(), this);
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
