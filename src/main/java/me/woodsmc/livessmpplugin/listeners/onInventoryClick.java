package me.woodsmc.livessmpplugin.listeners;

import com.sun.tools.javac.code.Type;
import me.woodsmc.livessmpplugin.LivesManager;
import me.woodsmc.livessmpplugin.LivesSMPPlugin;
import me.woodsmc.livessmpplugin.Message;
import me.woodsmc.livessmpplugin.inventory.ReviveGUI;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.HashMap;
import java.util.Locale;

public class onInventoryClick implements Listener {
    private final LivesSMPPlugin plugin = LivesSMPPlugin.getPlugin(LivesSMPPlugin.class);
    private final LivesManager lives = new LivesManager(plugin);
    private final ReviveGUI gui = new ReviveGUI();

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        Player p = (Player) event.getWhoClicked();
        if(event.getInventory() == null){
            return;
        }
        if(event.getCurrentItem() == null){
            return;
        }
        if(event.getView() == null){
            return;
        }
        if(event.getCurrentItem().getItemMeta() == null){
            return;
        }
        if(!event.getCurrentItem().getItemMeta().hasCustomModelData()){
            return;
        }
        ItemStack item = event.getCurrentItem();
        ItemMeta meta = item.getItemMeta();
        if(event.getView().getTitle().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("revive-gui-title")))){
            event.setCancelled(true);
            if(meta.getCustomModelData() == 102847){
                SkullMeta skullMeta = (SkullMeta) meta;
                OfflinePlayer dead = skullMeta.getOwningPlayer();
                getBanned().remove(dead.getName().toLowerCase());
                lives.setLives(dead, 1);
                p.sendMessage(Message.REVIVED_PLAYER.replace("[revived]", dead.getName()));
                if(plugin.getConfig().getBoolean("Lives-Revive-Player-Broadcast.enabled")){
                    Bukkit.broadcastMessage(Message.REVIVE_BROADCAST.replace("[revived]", dead.getName()).replace("[reviver]", p.getDisplayName()));
                }
                if(p.getGameMode() != GameMode.CREATIVE) {
                    p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount() - 1);
                }
                p.closeInventory();
            }
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
