package me.elmopog.gens;

import me.elmopog.gens.commands.getGen;
import me.elmopog.gens.commands.reloadConfig;
import me.elmopog.gens.listeners.BlockBreak;
import me.elmopog.gens.listeners.BlockPlace;
import me.elmopog.gens.listeners.JoinListener;
import me.elmopog.gens.utils.genUtils;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

/*TODO
    - Complete generate method in genUtils
    - ln 85 BlockPlace
    - ln 38 BlockBreak
    - Add config.yml to easily add/remove/change generators
    - Make gens do something when placed
    - * MORE COMING *
 */

public final class Gens extends JavaPlugin {

    public static Plugin plugin;

    @Override
    public void onEnable() {

        plugin = this;

        //Config init
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();

        genUtils.setAll();
        //

        //Registers listeners
        Bukkit.getServer().getPluginManager().registerEvents(new BlockPlace(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new BlockBreak(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new JoinListener(), this);
        //

        //Registers commands
        getCommand("givegen").setExecutor(new getGen());
        getCommand("reloadconfig").setExecutor(new reloadConfig());
        //

        //Registers config
        data.setup();
        data.get().options().copyDefaults(true);
        data.save();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static Plugin getPlugin(){
        return plugin;
    }
}
