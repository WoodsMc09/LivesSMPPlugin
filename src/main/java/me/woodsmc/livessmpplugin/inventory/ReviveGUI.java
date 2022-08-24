package me.woodsmc.livessmpplugin.inventory;

import me.woodsmc.livessmpplugin.LivesSMPPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

public class ReviveGUI {
    private Inventory inventory;
    private final LivesSMPPlugin plugin = LivesSMPPlugin.getPlugin(LivesSMPPlugin.class);
    private List<ItemStack> playerItems = new ArrayList<>();

    public Inventory openGUI(){
        inventory = Bukkit.createInventory(null, 45, ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("revive-gui-title")));
        for(String s : plugin.getLivesYML().getConfig().getKeys(false)){
            OfflinePlayer player = Bukkit.getOfflinePlayer(UUID.fromString(s));
            if(plugin.getLivesYML().getConfig().getInt(String.valueOf(player.getUniqueId())) == 0){
                inventory.addItem(createDeadItem(player));
            }
        }
        return inventory;
    }

    public ItemStack createDeadItem(OfflinePlayer p){
        ItemStack plrItem;
        SkullMeta meta;
        plrItem = new ItemStack(Material.PLAYER_HEAD, 1);
        meta = (SkullMeta) plrItem.getItemMeta();
        meta.setOwningPlayer(p);
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("revive-player-item.name").replace("[player]", p.getName())));
        meta.setCustomModelData(102847);
        List<String> lore = new ArrayList<>();
        for(String s : plugin.getConfig().getStringList("revive-player-item.lore")){
            lore.add(ChatColor.translateAlternateColorCodes('&', s.replace("[player]", p.getName())));
        }
        meta.setLore(lore);
        plrItem.setItemMeta(meta);
        return plrItem;
    }

}
