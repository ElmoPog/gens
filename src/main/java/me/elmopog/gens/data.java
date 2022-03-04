package me.elmopog.gens;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class data {
    private static File file;
    private static FileConfiguration customFile;

    public static void setup(){
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("Gens").getDataFolder(), "genData.yml");

        if(!(file.exists())){
            try {
                file.createNewFile();
            } catch (IOException e) {
                //
            }
        }

        customFile = YamlConfiguration.loadConfiguration(file);

    }

    public static FileConfiguration get(){
        return customFile;
    }

    public static void save(){
        try {
            customFile.save(file);
        } catch (IOException e) {
            //
        }
    }

    public static void reload(){
        customFile = YamlConfiguration.loadConfiguration(file);
    }
}
