package me.woodsmc.livessmpplugin;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class LivesSMPPlugin extends JavaPlugin {

    private LivesYML livesYML;

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
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }


    public LivesYML getLivesYML(){
        return livesYML;
    }
}
