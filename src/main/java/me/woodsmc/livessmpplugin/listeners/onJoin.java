package me.woodsmc.livessmpplugin.listeners;

import me.woodsmc.livessmpplugin.LivesManager;
import me.woodsmc.livessmpplugin.LivesSMPPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Random;

public class onJoin implements Listener {
    private final LivesSMPPlugin plugin = LivesSMPPlugin.getPlugin(LivesSMPPlugin.class);
    private final LivesManager lives = new LivesManager(plugin);


    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player p = event.getPlayer();
        Bukkit.getConsoleSender().sendMessage("§fLoading " + p.getName() + "s life count..");
        lives.updateTab(p);
        if(p.hasPlayedBefore()){
            //player has played before
        }
        else{
            //player is new to the server
            Random random = new Random();
            int i = random.nextInt(plugin.getConfig().getInt("lives-randomization.maximum")) + plugin.getConfig().getInt("lives-randomization.minimum");
            lives.setLives(p, i);
        }
    }
}
