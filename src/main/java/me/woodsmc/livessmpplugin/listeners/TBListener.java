package me.woodsmc.livessmpplugin.listeners;

import me.woodsmc.livessmpplugin.LivesSMPPlugin;
import me.woodsmc.livessmpplugin.commands.CommandExec;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import java.util.HashMap;

public class TBListener implements Listener {


    public LivesSMPPlugin plugin;
    public TBListener(LivesSMPPlugin p){
        plugin = p;
    }

    @EventHandler
    public void onJoin(PlayerLoginEvent e){
        Player player = e.getPlayer();

        if(getBanned().containsKey(player.getName().toLowerCase())){
            if(getBanned().get(player.getName().toLowerCase()) != null){
                long endOfBan = getBanned().get(player.getName().toLowerCase());
                long now = System.currentTimeMillis();
                long diff = endOfBan - now;

                String string = "";
                for(String s : plugin.getConfig().getStringList("life-ban-reason")){
                    string = string + "\n" + s;
                }

                if(diff<=0){
                    getBanned().remove(player.getName().toLowerCase());
                }else{
                    e.disallow(PlayerLoginEvent.Result.KICK_OTHER, ChatColor.translateAlternateColorCodes('&', "You are banned from the server.\nReason:\n" + string + "\nDuration: " + CommandExec.getMSG(endOfBan)));
                }
            }
        }
    }

    public void tell(Player player, String m){
        player.sendMessage(m);
    }

    public HashMap<String, Long> getBanned(){
        return LivesSMPPlugin.banned;
    }
}
