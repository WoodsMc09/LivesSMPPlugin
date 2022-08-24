package me.woodsmc.livessmpplugin.listeners;

import me.woodsmc.livessmpplugin.LivesManager;
import me.woodsmc.livessmpplugin.LivesSMPPlugin;
import me.woodsmc.livessmpplugin.Message;
import me.woodsmc.livessmpplugin.inventory.ReviveGUI;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class onInteract implements Listener {

    private final LivesSMPPlugin plugin = LivesSMPPlugin.getPlugin(LivesSMPPlugin.class);
    private final LivesManager lives = new LivesManager(plugin);
    private final ReviveGUI gui = new ReviveGUI();

    @EventHandler
    public void onInteract(PlayerInteractEvent event){
        Player p = event.getPlayer();
        if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK){
            if(p.getInventory().getItemInMainHand() == null){
                return;
            }
            if(p.getInventory().getItemInMainHand().getItemMeta() == null){
                return;
            }
            if(!p.getInventory().getItemInMainHand().getItemMeta().hasCustomModelData()){
                return;
            }
            ItemStack item = p.getInventory().getItemInMainHand();
            if(event.getHand() == EquipmentSlot.HAND){
                if(item.getItemMeta().getCustomModelData() == 219234){
                    if(p.getGameMode() != GameMode.CREATIVE){
                        if(lives.getLives(p) == plugin.getConfig().getInt("maximum-lives")){
                            p.sendMessage(Message.MAXIMUM_LIVES.replace("[max]", String.valueOf(plugin.getConfig().getInt("maximum-lives"))));
                        }else {
                            p.sendMessage(Message.EARN_LIFE);
                            item.setAmount(item.getAmount() - 1);
                            p.getInventory().setItemInMainHand(item);
                            lives.addLives(p, 1);
                        }
                    }
                }
            }
        }
        if(event.getAction() == Action.RIGHT_CLICK_BLOCK){
            if(p.getInventory().getItemInMainHand() == null){
                return;
            }
            if(p.getInventory().getItemInMainHand().getItemMeta() == null){
                return;
            }
            if(!p.getInventory().getItemInMainHand().getItemMeta().hasCustomModelData()){
                return;
            }
            ItemStack item = p.getInventory().getItemInMainHand();
            if(event.getHand() == EquipmentSlot.HAND){
                if(item.getItemMeta().getCustomModelData() == 219235){
                    event.setCancelled(true);
                    if(p.getGameMode() != GameMode.CREATIVE){
                        p.openInventory(gui.openGUI());
                    }
                }
            }
        }
    }
}
