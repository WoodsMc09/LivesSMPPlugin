package me.woodsmc.livessmpplugin;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;

public class LivesYML {


    private FileConfiguration config = null;
    private final LivesSMPPlugin plugin;
    private File configFile = null;


    public LivesYML(LivesSMPPlugin plugin){
        this.plugin = plugin;
        saveDefaultConfig();
    }

    public void reloadConfig(){
        if(this.configFile == null){
            this.configFile = new File(this.plugin.getDataFolder(), "lives.yml");
        }
        this.config = YamlConfiguration.loadConfiguration(this.configFile);

        InputStream defaultStream = this.plugin.getResource("lives.yml");
        if(defaultStream != null){
            YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultStream));
            this.config.setDefaults(defaultConfig);
        }
    }

    public FileConfiguration getConfig(){
        if(this.config == null){
            reloadConfig();
        }
        return this.config;
    }
    public void saveConfig(){
        if(this.config == null || this.configFile == null){
            return;
        }
        try {
            getConfig().save(this.configFile);
        } catch (IOException e) {
            plugin.getLogger().log(Level.WARNING, "§cCould not save config to " + this.configFile, e);
        }
    }

    public void saveDefaultConfig() {
        if (this.configFile == null) {
            this.configFile = new File(plugin.getDataFolder(), "lives.yml");
        }
        if(!this.configFile.exists()){
            plugin.saveResource("lives.yml", false);
        }
    }
}
