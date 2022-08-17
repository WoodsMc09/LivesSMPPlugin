package me.woodsmc.livessmpplugin.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class onJoin implements Listener {


    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player p = event.getPlayer();
        Bukkit.getConsoleSender().sendMessage("§fLoading " + p.getName() + " life count..");
        if(p.hasPlayedBefore()){
            //player has played before
        }
        else{
            //player is new to the server
        }
    }
}
