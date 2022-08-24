package me.woodsmc.livessmpplugin.crafts;

import me.woodsmc.livessmpplugin.LivesSMPPlugin;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

public class ReviveBeaconCraft extends ShapedRecipe {
    private final LivesSMPPlugin plugin = LivesSMPPlugin.getPlugin(LivesSMPPlugin.class);


    public ReviveBeaconCraft(NamespacedKey key, ItemStack result) {
        super(key, result);
        this.shape("ABC", "DEF", "GHI");
        this.setIngredient('A', Material.getMaterial(plugin.getConfig().getString("revive_beacon.1")));
        this.setIngredient('B', Material.getMaterial(plugin.getConfig().getString("revive_beacon.2")));
        this.setIngredient('C', Material.getMaterial(plugin.getConfig().getString("revive_beacon.3")));
        this.setIngredient('D', Material.getMaterial(plugin.getConfig().getString("revive_beacon.4")));
        this.setIngredient('E', Material.getMaterial(plugin.getConfig().getString("revive_beacon.5")));
        this.setIngredient('F', Material.getMaterial(plugin.getConfig().getString("revive_beacon.6")));
        this.setIngredient('G', Material.getMaterial(plugin.getConfig().getString("revive_beacon.7")));
        this.setIngredient('H', Material.getMaterial(plugin.getConfig().getString("revive_beacon.8")));
        this.setIngredient('I', Material.getMaterial(plugin.getConfig().getString("revive_beacon.9")));
    }
}
