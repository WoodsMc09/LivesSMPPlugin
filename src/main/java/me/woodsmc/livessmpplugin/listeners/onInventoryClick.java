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
                Bukkit.getBanList(BanList.Type.NAME).pardon(dead.getName());
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
}
