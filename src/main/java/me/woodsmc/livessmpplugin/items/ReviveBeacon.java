package me.woodsmc.livessmpplugin.items;

import me.woodsmc.livessmpplugin.LivesSMPPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class ReviveBeacon {


    private static final LivesSMPPlugin plugin = LivesSMPPlugin.getPlugin(LivesSMPPlugin.class);

    public static ItemStack reviveBeacon;

    public static void init(){
        createReviveBeaconItem();
    }

    private static void createReviveBeaconItem(){
        Material material = Material.getMaterial(plugin.getConfig().getString("revive_beacon-item.type"));
        if(material == null){
            Bukkit.getServer().getLogger().log(Level.SEVERE, "MATERIAL TYPE: \"" + plugin.getConfig().getString("revive_beacon-item.type") + "\" DOES NOT EXIST AT: LivesSMPPlugin.config#yml.revive_beacon-item.type:79");
            return;
        }
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("revive_beacon-item.name")));
        List<String> lore = new ArrayList<>();
        for(String s : plugin.getConfig().getStringList("revive_beacon-item.lore")){
            lore.add(ChatColor.translateAlternateColorCodes('&', s));
        }
        meta.setCustomModelData(219235);
        meta.setLore(lore);
        item.setItemMeta(meta);
        reviveBeacon = item;
    }
}
