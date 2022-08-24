package me.woodsmc.livessmpplugin;

import me.woodsmc.livessmpplugin.commands.LivesCommand;
import me.woodsmc.livessmpplugin.crafts.LifeCraft;
import me.woodsmc.livessmpplugin.crafts.ReviveBeaconCraft;
import me.woodsmc.livessmpplugin.items.Life;
import me.woodsmc.livessmpplugin.items.ReviveBeacon;
import me.woodsmc.livessmpplugin.listeners.onDeath;
import me.woodsmc.livessmpplugin.listeners.onInteract;
import me.woodsmc.livessmpplugin.listeners.onInventoryClick;
import me.woodsmc.livessmpplugin.listeners.onJoin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class LivesSMPPlugin extends JavaPlugin {

    private LivesYML livesYML;
    private Message message;
    private final LivesManager lives = new LivesManager(this);

    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getConsoleSender().sendMessage("§fLoading custom crafts..");
        getServer().getConsoleSender().sendMessage("§fLoading lives manager..");
        getServer().getConsoleSender().sendMessage("§fLoading files..");
        getServer().getConsoleSender().sendMessage("§fLivesSMP Optimized");


        //files
        livesYML = new LivesYML(this);
        getConfig().options().copyDefaults();
        saveDefaultConfig();

        //commands
        getCommand("lives").setExecutor(new LivesCommand());
        getCommand("lives").setTabCompleter(new LivesCommand());

        //listeners
        getServer().getPluginManager().registerEvents(new onJoin(), this);
        getServer().getPluginManager().registerEvents(new onInteract(), this);
        getServer().getPluginManager().registerEvents(new onDeath(), this);
        getServer().getPluginManager().registerEvents(new onInventoryClick(), this);

        //items
        Life.init();
        ReviveBeacon.init();
        //crafts
        NamespacedKey life = new NamespacedKey(this, "life");
        NamespacedKey revive_beacon = new NamespacedKey(this, "revive_beacon");
        Bukkit.addRecipe(new LifeCraft(life, Life.life));
        Bukkit.addRecipe(new ReviveBeaconCraft(revive_beacon, ReviveBeacon.reviveBeacon));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }


    public LivesYML getLivesYML(){
        return livesYML;
    }
}
