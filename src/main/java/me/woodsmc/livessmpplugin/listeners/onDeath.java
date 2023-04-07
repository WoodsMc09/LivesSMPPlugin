package me.woodsmc.livessmpplugin.listeners;

import me.woodsmc.livessmpplugin.LivesManager;
import me.woodsmc.livessmpplugin.LivesSMPPlugin;
import me.woodsmc.livessmpplugin.Message;
import me.woodsmc.livessmpplugin.TempBanUnit;
import me.woodsmc.livessmpplugin.inventory.ReviveGUI;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.HashMap;

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

            if(plugin.getConfig().getBoolean("Lives-Ban-Broadcast.enabled")){
                Bukkit.broadcastMessage(Message.BAN_BROADCAST.replace("[banned]", p.getDisplayName()));
            }

            long endOfBan;
            if(plugin.getConfig().getInt("life-ban-punishment.duration") == -1){
                endOfBan = System.currentTimeMillis() + TempBanUnit.getTicks("day", 365000);
            }
            else{
                endOfBan = System.currentTimeMillis() + TempBanUnit.getTicks("day", plugin.getConfig().getInt("life-ban-punishment.duration"));
            }

            String reason = ChatColor.translateAlternateColorCodes('&', "You are banned from the server.\nReason:\n" + string);
            ChatColor.stripColor(reason);

            long now = System.currentTimeMillis();
            long diff = endOfBan - now;

            if(diff > 0){
                setBanned(p.getName().toLowerCase(), endOfBan);

                String message = getMSG(endOfBan);

                String finalString = string;
                Bukkit.getScheduler().runTaskLater(plugin, () -> {
                    p.kickPlayer(ChatColor.translateAlternateColorCodes('&', "You are banned from the server.\nReason:\n" + finalString + "\nDuration: " + message));
                }, 10);
            }else{
                System.out.println("§cUnit or time not valid.");
            }
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


    public HashMap<String, Long> getBanned(){
        return LivesSMPPlugin.banned;
    }

    public void setBanned(String name, long end){
        getBanned().put(name, end);
    }

    public static String getMSG(long endOfBan){
        String message = "";

        long now = System.currentTimeMillis();
        long diff = endOfBan - now;
        int seconds = (int) (diff / 1000);

        if(seconds >= 60*60*24){
            int days = seconds / (60*60*24);
            seconds = seconds % (60*60*24);

            message += days + " Day(s) ";
        }
        if(seconds >= 60*60){
            int hours = seconds / (60*60);
            seconds = seconds % (60*60);

            message += hours + " Hour(s) ";
        }
        if(seconds >= 60){
            int min = seconds / 60;
            seconds = seconds % 60;

            message += min + " Minute(s) ";
        }
        if(seconds >= 0){
            message += seconds + " Second(s) ";
        }

        return message;
    }
}
