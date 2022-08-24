package me.woodsmc.livessmpplugin;

import com.sun.tools.javac.jvm.Items;
import me.woodsmc.livessmpplugin.items.Life;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class LivesManager {

    private LivesSMPPlugin plugin;

    public LivesManager(LivesSMPPlugin plugin){
        this.plugin = plugin;
    }

    public void setLives(Player p, int i){
        plugin.getLivesYML().getConfig().set(String.valueOf(p.getUniqueId()), i);
        plugin.getLivesYML().saveConfig();
        updateTab(p);
    }

    public void removeLives(Player p, int i){
        plugin.getLivesYML().getConfig().set(String.valueOf(p.getUniqueId()), plugin.getLivesYML().getConfig().getInt(String.valueOf(p.getUniqueId())) - i);
        plugin.getLivesYML().saveConfig();
        updateTab(p);
    }

    public void addLives(Player p, int i){
        plugin.getLivesYML().getConfig().set(String.valueOf(p.getUniqueId()), plugin.getLivesYML().getConfig().getInt(String.valueOf(p.getUniqueId())) + i);
        plugin.getLivesYML().saveConfig();
        updateTab(p);
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
        plugin.getLivesYML().saveConfig();
    }

    public int getLives(OfflinePlayer p){
        return plugin.getLivesYML().getConfig().getInt(String.valueOf(p.getUniqueId()));
    }

    public void withdrawLives(Player p, int i){
        Material material = Material.getMaterial(plugin.getConfig().getString("life-item.type"));
        if(material == null){
            Bukkit.getServer().getLogger().log(Level.SEVERE, "MATERIAL TYPE: \"" + plugin.getConfig().getString("life-item.type") + "\" DOES NOT EXIST AT: LivesSMPPlugin.config#yml.life-item.type:65");
            if(p.isOp()){
                p.sendMessage("§c[LivesSMPPlugin] AN ISSUE OCCURRED WHEN PREFORMING THIS ACTION! PLEASE CHECK CONSOLE/LOGGER!");
            }
            return;
        }
        if(p.getInventory().firstEmpty() == -1){
            p.sendMessage(Message.WITHDRAW_FULL_INVENTORY);
            return;
        }
        removeLives(p, i);
        ItemStack item = Life.life;
        item.setAmount(i);
        p.getInventory().addItem(item);
    }

    public void updateTab(Player p){
        p.setPlayerListName(p.getDisplayName() + " " + ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("tablist-suffix").replace("[lives]", String.valueOf(getLives(p)))));
    }
}
