package me.woodsmc.livessmpplugin.commands;

import me.woodsmc.livessmpplugin.LivesManager;
import me.woodsmc.livessmpplugin.LivesSMPPlugin;
import me.woodsmc.livessmpplugin.Message;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class LivesCommand implements CommandExecutor, TabCompleter {
    private final LivesSMPPlugin plugin = LivesSMPPlugin.getPlugin(LivesSMPPlugin.class);
    private final LivesManager lives = new LivesManager(plugin);

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player p = (Player) sender;
            if(args.length < 2 || args.length > 3){
                p.sendMessage("§f§l/lives usages:");
                if(p.hasPermission("livessmp.lives.set.command")) {
                    p.sendMessage("§4/lives set <player> <amt> §f- (ADMIN) set a players lives");
                }
                if(p.hasPermission("livessmp.lives.get.command")){
                    p.sendMessage("§4/lives get <player> §f- (ADMIN) get a players lives");
                }
                if(p.hasPermission("livessmp.lives.add.command")){
                    p.sendMessage("§4/lives add <player> <amt> §f- (ADMIN) add lives to a player");
                }
                if(p.hasPermission("livessmp.lives.remove.command")){
                    p.sendMessage("§4/lives remove <player> <amt> §f- (ADMIN) remove lives from a player");
                }
                if(p.hasPermission("livessmp.lives.withdraw.command")){
                    p.sendMessage("§4/lives withdraw <amt> §f- withdraw lives from yourself");
                }
            }
            else if(args.length >= 2){
                OfflinePlayer offP = Bukkit.getOfflinePlayer(args[1]);
                OfflinePlayer player = Bukkit.getOfflinePlayer(offP.getUniqueId()); //if player changes their name this value won't change
                if(args[0].equalsIgnoreCase("set")){
                    if(!p.isOp()){
                        p.sendMessage(Message.INVALID_PERMISSIONS);
                        return true;
                    }
                    int i = Integer.parseInt(args[2]);
                    if(player.isOnline()){
                        p.sendMessage(Message.amount(Message.SET_LIVES_ADMIN, i).replace("[recipient]", player.getPlayer().getDisplayName()));
                        player.getPlayer().sendMessage(Message.amount(Message.SET_LIVES_RECIPIENT, Integer.parseInt(args[2])).replace("[cmd-sender]", p.getDisplayName()));
                        lives.setLives(player, i);
                        lives.updateTab(player.getPlayer());
                    }
                    else{
                        p.sendMessage(Message.amount(Message.SET_LIVES_ADMIN, i).replace("[recipient]", player.getName()));
                        lives.setLives(player, i);
                    }
                }
                else if(args[0].equalsIgnoreCase("get")){
                    if(!p.isOp()){
                        p.sendMessage(Message.INVALID_PERMISSIONS);
                        return true;
                    }
                    if(player.isOnline()){
                        p.sendMessage(Message.amount(Message.GET_LIVES, lives.getLives(player)).replace("[recipient]", player.getPlayer().getDisplayName()));
                    }
                    else{
                        p.sendMessage(Message.amount(Message.GET_LIVES, lives.getLives(player)).replace("[recipient]", player.getName()));
                    }
                }
                else if(args[0].equalsIgnoreCase("add")){
                    if(!p.isOp()){
                        p.sendMessage(Message.INVALID_PERMISSIONS);
                        return true;
                    }
                    int i = Integer.parseInt(args[2]);
                    if(player.isOnline()){
                        p.sendMessage(Message.amount(Message.ADD_LIVES_ADMIN, i).replace("[recipient]", player.getPlayer().getDisplayName()));
                        player.getPlayer().sendMessage(Message.amount(Message.ADD_LIVES_RECIPIENT, i).replace("[cmd-sender]", p.getDisplayName()));
                        lives.addLives(player, i);
                        lives.updateTab(player.getPlayer());
                    }else{
                        p.sendMessage(Message.amount(Message.ADD_LIVES_ADMIN, i).replace("[recipient]", player.getName()));
                        lives.addLives(player, i);
                    }
                }
                else if(args[0].equalsIgnoreCase("remove")){
                    if(!p.isOp()){
                        p.sendMessage(Message.INVALID_PERMISSIONS);
                        return true;
                    }
                    int i = Integer.parseInt(args[2]);
                    if(player.isOnline()){
                        p.sendMessage(Message.amount(Message.REMOVE_LIVES_ADMIN, i).replace("[recipient]", player.getPlayer().getDisplayName()));
                        player.getPlayer().sendMessage(Message.amount(Message.REMOVE_LIVES_RECIPIENT, i).replace("[cmd-sender]", p.getDisplayName()));
                        lives.removeLives(player, i);
                        lives.updateTab(player.getPlayer());
                    }else{
                        p.sendMessage(Message.amount(Message.REMOVE_LIVES_ADMIN, i).replace("[recipient]", player.getName()));
                        lives.removeLives(player, i);
                    }
                }
                else if(args[0].equalsIgnoreCase("withdraw")){
                    if(lives.getLives(p) == 1){
                        p.sendMessage(Message.NOT_ENOUGH_LIVES);
                        return true;
                    }
                    int i = Integer.parseInt(args[1]);
                    if(lives.getLives(p) <= i){
                        p.sendMessage(Message.NOT_ENOUGH_LIVES);
                        return true;
                    }
                    p.sendMessage(Message.amount(Message.WITHDRAW_LIVES, i));
                    lives.withdrawLives(p, i);
                }
            }
        }else{

        }
        return true;
    }

    List<String> arg0 = new ArrayList<>();
    List<String> arg1 = new ArrayList<>();
    List<String> arg2 = new ArrayList<>();
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player p = (Player) sender;
            if (args.length == 1) {
                if (p.isOp()) {
                    arg0.add("set");
                    arg0.add("get");
                    arg0.add("add");
                    arg0.add("remove");
                    arg0.add("withdraw");
                    return arg0;
                }
                else{
                    arg0.add("withdraw");
                    return arg0;
                }
            } else if (args.length == 2 && args[0].equalsIgnoreCase("withdraw")) {
                arg1.add("[amount]");
                return arg1;
            } else if (args.length == 3 && !args[0].equalsIgnoreCase("get") && !args[0].equalsIgnoreCase("withdraw")) {
                arg2.add("[amount]");
                return arg2;
            }
            return null;
        }
        return null;
    }
}
