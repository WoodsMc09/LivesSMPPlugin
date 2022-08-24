package me.woodsmc.livessmpplugin.listeners;

import me.woodsmc.livessmpplugin.LivesManager;
import me.woodsmc.livessmpplugin.LivesSMPPlugin;
import me.woodsmc.livessmpplugin.Message;
import me.woodsmc.livessmpplugin.inventory.ReviveGUI;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class onDeath implements Listener {
    private final LivesSMPPlugin plugin = LivesSMPPlugin.getPlugin(LivesSMPPlugin.class);
    private final LivesManager lives = new LivesManager(plugin);
    private final ReviveGUI gui = new ReviveGUI();

    @EventHandler
    public void onDeath(PlayerDeathEvent event){
        Player p = event.getEntity();
        if(lives.getLives(p) == 1){
            String string = "";
            for(String s : plugin.getConfig().getStringList("life-ban-reason")){
                string = string + "\n" + s;
            }
            Bukkit.getBanList(BanList.Type.NAME).addBan(p.getName(), ChatColor.translateAlternateColorCodes('&',  string), null, null);
            if(plugin.getConfig().getBoolean("Lives-Ban-Broadcast.enabled")){
                Bukkit.broadcastMessage(Message.BAN_BROADCAST.replace("[banned]", p.getDisplayName()));
            }
            p.kickPlayer(ChatColor.translateAlternateColorCodes('&', "You are banned from the server.\nReason:\n" + string));
        }
        lives.removeLives(p, 1);
        p.sendMessage(Message.LOSE_LIFE);
        if(p.getKiller() != null){
            Player killer = p.getKiller();
            if(lives.getLives(killer) == plugin.getConfig().getInt("maximum-lives")){
                killer.sendMessage(Message.MAXIMUM_LIVES.replace("[max]", String.valueOf(plugin.getConfig().getInt("maximum-lives"))));
                return;
            }
            lives.addLives(killer, 1);
            killer.sendMessage(Message.EARN_LIFE);
        }
    }
}
