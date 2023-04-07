package me.woodsmc.livessmpplugin.commands;

import me.woodsmc.livessmpplugin.LivesManager;
import me.woodsmc.livessmpplugin.LivesSMPPlugin;
import me.woodsmc.livessmpplugin.TempBanUnit;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class CommandExec implements CommandExecutor {


    public LivesSMPPlugin plugin;
    private LivesManager lives;

    public CommandExec(LivesSMPPlugin p){
        plugin = p;
        lives = new LivesManager(p);
    }


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String desc, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            ////// Player commands....

            if(cmd.getName().equalsIgnoreCase("tempban")){
                if(args.length!=4){
                    player.sendMessage("§cUsage: /tempban <player> <amount> <unit> <reason>");
                    return true;
                }

                if(!player.isOp()){
                    player.sendMessage("§cYou do not have permission to execute this command!");
                    return true;
                }

                Player target = plugin.getServer().getPlayer(args[0]);
                //assert RankManager.config != null;

                if (target == null || !target.isOnline()) {
                    player.sendMessage("§cThat player could not be found!");
                    return true;
                }
                if (target.isOp()) {
                    player.sendMessage("§3You may not ban that player!");
                    return true;
                }

                long endOfBan = System.currentTimeMillis() + TempBanUnit.getTicks(args[2], Integer.parseInt(args[1]));
                String reason = ChatColor.translateAlternateColorCodes('&', args[3]);
                ChatColor.stripColor(reason);

                long now = System.currentTimeMillis();
                long diff = endOfBan - now;

                if (diff > 0) {
                    setBanned(target.getName().toLowerCase(), endOfBan);

                    String message = getMSG(endOfBan);

                    Bukkit.getScheduler().runTaskLater(plugin, () -> {
                        target.kickPlayer("§3§lWoodenBot\n§b§lYou are temp-banned for\n§3" + message + "\n§b§lREASON:\n§3" + reason + "\n3Appeal: §bhttps://bit.ly/3JPjthI");
                        }, 10);
                    return true;
                } else {
                    player.sendMessage("§cUnit or time not valid.");
                    return true;
                }
            }

            /*if(cmd.getName().equalsIgnoreCase("tempbanexact")){
                if(args.length!=3){
                    player.sendMessage(ChatColor.RED + "Error: /tempban <player> <amount> <unit>");
                    return true;
                }

                if(!player.isOp()){
                    player.sendMessage(ChatColor.RED + "[TempBan] You don't have permission to do this.");
                    return true;
                }

                long endOfBan = System.currentTimeMillis() + TempBanUnit.getTicks(args[2], Integer.parseInt(args[1]));

                long now = System.currentTimeMillis();
                long diff = endOfBan - now;

                if(diff > 0){
                    setBanned(args[0].toLowerCase(), endOfBan);

                    String message = getMSG(endOfBan);

                    plugin.server.broadcastMessage("[TempBan] " + "The player " + args[0].toLowerCase() + " is now banned for " + message);
                    return true;
                }else{
                    player.sendMessage(ChatColor.RED + "Error: Unit or time not valid.");
                    return true;
                }
            }
             */

            if(cmd.getName().equalsIgnoreCase("unban")){
                if(args.length!=1){
                    player.sendMessage(ChatColor.RED + "§cUsage: /unban <player>");
                    return true;
                }

                if(!player.isOp()){
                    player.sendMessage("§cYou do not have permission to execute this command!");
                    return true;
                }

                String target = args[0];
                //assert RankManager.config != null;

                if (getBanned().containsKey(target.toLowerCase())) {
                    getBanned().remove(target.toLowerCase());
                    player.sendMessage("§b" + args[0] + "§3 Is now un-banned!");
                    lives.setLives(Bukkit.getOfflinePlayer(target), 1);
                    return true;
                } else {
                    player.sendMessage("§b" + args[0] + "§3 Is not currently banned!");
                    return true;
                }
            }

            if(cmd.getName().equalsIgnoreCase("check")){
                if(args.length!=1){
                    player.sendMessage(ChatColor.RED + "§cUsage: /check <player>");
                    return true;
                }

                if(!player.isOp()){
                    player.sendMessage("§cYou do not have permission to execute this command!");
                    return true;
                }

                String target = args[0];
                //assert RankManager.config != null;

                if (getBanned().containsKey(target.toLowerCase())) {
                    player.sendMessage("§b" + args[0] + " §3is banned for §b" + getMSG(getBanned().get(target.toLowerCase())));
                    return true;
                } else {
                    player.sendMessage("§b" + args[0] + " §3is not banned.");
                    return true;
                }
            }
        }else{
            //////// Console Commands....



            if(cmd.getName().equalsIgnoreCase("tempban")){
                if(args.length!=4){
                    System.out.println(ChatColor.RED + "§cUsage: /tempban <player> <amount> <unit> <reason>");
                    return true;
                }

                Player target = plugin.getServer().getPlayer(args[0]);

                if(target==null || !target.isOnline()){
                    plugin.getServer().getConsoleSender().sendMessage("§cPlayer could not be found!");
                    return true;
                }

                long endOfBan = System.currentTimeMillis() + TempBanUnit.getTicks(args[2], Integer.parseInt(args[1]));
                String reason = ChatColor.translateAlternateColorCodes('&', args[3]);
                ChatColor.stripColor(reason);

                long now = System.currentTimeMillis();
                long diff = endOfBan - now;

                if(diff > 0){
                    setBanned(target.getName().toLowerCase(), endOfBan);

                    String message = getMSG(endOfBan);

                    Bukkit.getScheduler().runTaskLater(plugin, () -> {
                        target.kickPlayer("§3§lWoodenBot\n§b§lYou are temp-banned for\n§3" + message + "\n§b§lREASON:\n§3" + reason + "\n3Appeal: §bhttps://bit.ly/3JPjthI");
                    }, 10);
                    return true;
                }else{
                    System.out.println("§cUnit or time not valid.");
                    return true;
                }
            }

            /*
            if(cmd.getName().equalsIgnoreCase("tempbanexact")){
                if(args.length!=3){
                    System.out.println("Error: /tempbanexact <player> <amount> <unit>");
                    return true;
                }

                long endOfBan = System.currentTimeMillis() + TempBanUnit.getTicks(args[2], Integer.parseInt(args[1]));

                long now = System.currentTimeMillis();
                long diff = endOfBan - now;

                if(diff > 0){
                    setBanned(args[0].toLowerCase(), endOfBan);

                    String message = getMSG(endOfBan);

                    plugin.server.broadcastMessage("[TempBan] The player " + args[0].toLowerCase() + " is now banned for " + message);
                    return true;
                }else{
                    System.out.println("Error: Unit or time not valid.");
                    return true;
                }
            }
             */

            if(cmd.getName().equalsIgnoreCase("unban")){
                if(args.length!=1){
                    System.out.println("§cUsage: /unban <player>");
                    return true;
                }

                String target = args[0];

                if(getBanned().containsKey(target.toLowerCase())){
                    getBanned().remove(target.toLowerCase());
                    plugin.getServer().getPlayerExact(target);
                    Bukkit.getBanList(BanList.Type.NAME).pardon(target);
                    lives.setLives(Bukkit.getOfflinePlayer(target), 1);
                    return true;
                }else{
                    return true;
                }
            }

            if(cmd.getName().equalsIgnoreCase("check")){
                if(args.length!=1){
                    return true;
                }

                String target = args[0];

                if(getBanned().containsKey(target.toLowerCase())){
                    return true;
                }else{
                    return true;
                }
            }
        }

        return false;
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
