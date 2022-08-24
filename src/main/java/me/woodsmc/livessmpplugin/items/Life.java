package me.woodsmc.livessmpplugin.items;

import me.woodsmc.livessmpplugin.LivesSMPPlugin;
import me.woodsmc.livessmpplugin.Message;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class Life {

    private static final LivesSMPPlugin plugin = LivesSMPPlugin.getPlugin(LivesSMPPlugin.class);

    public static ItemStack life;

    public static void init(){
        createLifeItem();
    }

    private static void createLifeItem(){
        Material material = Material.getMaterial(plugin.getConfig().getString("life-item.type"));
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("life-item.name")));
        List<String> lore = new ArrayList<>();
        for(String s : plugin.getConfig().getStringList("life-item.lore")){
            lore.add(ChatColor.translateAlternateColorCodes('&', s));
        }
        meta.setCustomModelData(219234);
        meta.setLore(lore);
        item.setItemMeta(meta);
        life = item;
    }
}
