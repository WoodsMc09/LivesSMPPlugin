package me.woodsmc.livessmpplugin;

import org.bukkit.plugin.java.JavaPlugin;

public final class LivesSMPPlugin extends JavaPlugin {

    private LivesYML livesYML;

    @Override
    public void onEnable() {
        // Plugin startup logic


        //files
        livesYML = new LivesYML(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }


    public LivesYML getLivesYML(){
        return livesYML;
    }
}
