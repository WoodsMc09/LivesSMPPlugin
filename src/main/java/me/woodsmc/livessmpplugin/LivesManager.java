package me.woodsmc.livessmpplugin;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class LivesManager {

    private LivesSMPPlugin plugin;

    public LivesManager(LivesSMPPlugin plugin){
        this.plugin = plugin;
    }

    public void setLives(Player p, int i){
        plugin.getLivesYML().getConfig().set(String.valueOf(p.getUniqueId()), i);
        plugin.getLivesYML().saveConfig();
    }

    public void removeLives(Player p, int i){
        plugin.getLivesYML().getConfig().set(String.valueOf(p.getUniqueId()), plugin.getLivesYML().getConfig().getInt(String.valueOf(p.getUniqueId())) - i);
        plugin.getLivesYML().saveConfig();
    }

    public void addLives(Player p, int i){
        plugin.getLivesYML().getConfig().set(String.valueOf(p.getUniqueId()), plugin.getLivesYML().getConfig().getInt(String.valueOf(p.getUniqueId())) + i);
        plugin.getLivesYML().saveDefaultConfig();
    }

    public int getLives(Player p){
        return plugin.getLivesYML().getConfig().getInt(String.valueOf(p.getUniqueId()));
    }

    public void setLives(OfflinePlayer p, int i){
        plugin.getLivesYML().getConfig().set(String.valueOf(p.getUniqueId()), i);
        plugin.getLivesYML().saveConfig();
    }

    public void removeLives(OfflinePlayer p, int i){
        plugin.getLivesYML().getConfig().set(String.valueOf(p.getUniqueId()), plugin.getLivesYML().getConfig().getInt(String.valueOf(p.getUniqueId())) - i);
        plugin.getLivesYML().saveConfig();
    }

    public void addLives(OfflinePlayer p, int i){
        plugin.getLivesYML().getConfig().set(String.valueOf(p.getUniqueId()), plugin.getLivesYML().getConfig().getInt(String.valueOf(p.getUniqueId())) + i);
        plugin.getLivesYML().saveDefaultConfig();
    }

    public int getLives(OfflinePlayer p){
        return plugin.getLivesYML().getConfig().getInt(String.valueOf(p.getUniqueId()));
    }
}
