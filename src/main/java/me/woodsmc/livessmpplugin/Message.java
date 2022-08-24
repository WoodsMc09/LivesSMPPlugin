package me.woodsmc.livessmpplugin;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

public class Message {

    private static LivesSMPPlugin plugin = LivesSMPPlugin.getPlugin(LivesSMPPlugin.class);

    //PREFIX MESSAGE
    public static final String PREFIX = plugin.getConfig().getString("Message-Prefix");
    //INVALID PERMISSIONS MESSAGE
    public static final String INVALID_PERMISSIONS = formatWithPrefix(plugin.getConfig().getString("Command-Invalid-Permissions"));
    //COMMAND SET LIVES (admin)
    public static final String SET_LIVES_ADMIN = formatWithPrefix(plugin.getConfig().getString("Command-Set-Lives(admin)"));
    //COMMAND SET LIVES (recipient)
    public static final String SET_LIVES_RECIPIENT = formatWithPrefix(plugin.getConfig().getString("Command-Set-Lives(recipient)"));
    //COMMAND GET LIVES
    public static final String GET_LIVES = formatWithPrefix(plugin.getConfig().getString("Command-Get-Lives"));
    //COMMAND ADD LIVES (admin)
    public static final String ADD_LIVES_ADMIN = formatWithPrefix(plugin.getConfig().getString("Command-Add-Lives(admin)"));
    //COMMAND ADD LIVES (recipient)
    public static final String ADD_LIVES_RECIPIENT = formatWithPrefix(plugin.getConfig().getString("Command-Add-Lives(recipient)"));
    //COMMAND REMOVE LIVES (admin)
    public static final String REMOVE_LIVES_ADMIN = formatWithPrefix(plugin.getConfig().getString("Command-Remove-Lives(admin)"));
    //COMMAND REMOVE LIVES (recipient)
    public static final String REMOVE_LIVES_RECIPIENT = formatWithPrefix(plugin.getConfig().getString("Command-Remove-Lives(recipient)"));
    //COMMAND WITHDRAW LIVES
    public static final String WITHDRAW_LIVES = formatWithPrefix(plugin.getConfig().getString("Command-Withdraw-Lives"));
    //COMMAND WITHDRAW FULL INVENTORY
    public static final String WITHDRAW_FULL_INVENTORY = formatWithPrefix(plugin.getConfig().getString("Command-Withdraw-Full-Inventory"));
    //LIVES EARN LIFE
    public static final String EARN_LIFE = formatWithPrefix(plugin.getConfig().getString("Lives-Earn-Life"));
    //LIVES LOSE LIFE
    public static final String LOSE_LIFE = formatWithPrefix(plugin.getConfig().getString("Lives-Lose-Life"));
    //PLAYER BANNED
    public static final String BAN_BROADCAST = formatWithPrefix(plugin.getConfig().getString("Lives-Ban-Broadcast.message"));
    //REVIVED PLAYER
    public static final String REVIVED_PLAYER = formatWithPrefix(plugin.getConfig().getString("Lives-Revive-Player"));
    //REVIVE BROADCAST
    public static final String REVIVE_BROADCAST = formatWithPrefix(plugin.getConfig().getString("Lives-Revive-Player-Broadcast.message"));
    //MAXIMUM LIVES
    public static final String MAXIMUM_LIVES = formatWithPrefix(plugin.getConfig().getString("Lives-Max-Lives"));
    //NOT ENOUGH LIVES
    public static final String NOT_ENOUGH_LIVES = formatWithPrefix(plugin.getConfig().getString("Command-Withdraw-Not-Enough-Lives"));
    private static String formatWithPrefix(String s){
        return ChatColor.translateAlternateColorCodes('&', s.replace("[prefix]", PREFIX));
    }

    public static String amount(String s, int i){
        return s.replace("[count]", String.valueOf(i));
    }

}
