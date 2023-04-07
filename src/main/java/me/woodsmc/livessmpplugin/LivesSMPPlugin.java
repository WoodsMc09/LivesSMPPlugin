package me.woodsmc.livessmpplugin;

import me.woodsmc.livessmpplugin.commands.CommandExec;
import me.woodsmc.livessmpplugin.commands.LivesCommand;
import me.woodsmc.livessmpplugin.crafts.LifeCraft;
import me.woodsmc.livessmpplugin.crafts.ReviveBeaconCraft;
import me.woodsmc.livessmpplugin.items.Life;
import me.woodsmc.livessmpplugin.items.ReviveBeacon;
import me.woodsmc.livessmpplugin.listeners.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.util.HashMap;

public final class LivesSMPPlugin extends JavaPlugin {

    private LivesYML livesYML;
    private Message message;
    private final LivesManager lives = new LivesManager(this);
    public static HashMap<String, Long> banned = new HashMap<String, Long>();
    public static String Path = "plugins/TempBan" + File.separator + "BanList.dat";
    public TBListener Listener = new TBListener(this);

    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getConsoleSender().sendMessage("§fLoading custom crafts..");
        getServer().getConsoleSender().sendMessage("§fLoading lives manager..");
        getServer().getConsoleSender().sendMessage("§fLoading files..");
        getServer().getConsoleSender().sendMessage("§fLivesSMP Optimized");


        //files
        livesYML = new LivesYML(this);
        getConfig().options().copyDefaults();
        saveDefaultConfig();

        //commands
        getCommand("lives").setExecutor(new LivesCommand());
        getCommand("lives").setTabCompleter(new LivesCommand());
        getCommand("unban").setExecutor(new CommandExec(this));
        getCommand("tempban").setExecutor(new CommandExec(this));

        //listeners
        getServer().getPluginManager().registerEvents(new onJoin(), this);
        getServer().getPluginManager().registerEvents(new onInteract(), this);
        getServer().getPluginManager().registerEvents(new onDeath(), this);
        getServer().getPluginManager().registerEvents(new onInventoryClick(), this);
        getServer().getPluginManager().registerEvents(Listener, this);

        //items
        Life.init();
        ReviveBeacon.init();
        //crafts
        NamespacedKey life = new NamespacedKey(this, "life");
        NamespacedKey revive_beacon = new NamespacedKey(this, "revive_beacon");
        Bukkit.addRecipe(new LifeCraft(life, Life.life));
        Bukkit.addRecipe(new ReviveBeaconCraft(revive_beacon, ReviveBeacon.reviveBeacon));

        //temp ban
        File file = new File(Path);
        new File("plugins/TempBan").mkdir();

        if(file.exists()){
            banned = load();
        }

        if(banned == null){
            banned = new HashMap<String, Long>();
        }
    }

    @Override
    public void onDisable() {
        save();
    }

    public static void save(){
        File file = new File("plugins/TempBan" + File.separator + "BanList.dat");
        new File("plugins/TempBan").mkdir();
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        try{
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(Path));
            oos.writeObject(banned);
            oos.flush();
            oos.close();
            //Handle I/O exceptions
        }catch(Exception e){
            e.printStackTrace();
        }
    }


    @SuppressWarnings("unchecked")
    public static HashMap<String, Long> load(){
        try{
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(Path));
            Object result = ois.readObject();
            ois.close();
            return (HashMap<String,Long>)result;
        }catch(Exception e){
            return null;
        }
    }

    public LivesYML getLivesYML(){
        return livesYML;
    }
}
