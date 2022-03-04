package me.elmopog.gens;

import me.elmopog.gens.commands.getGen;
import me.elmopog.gens.listeners.BlockBreak;
import me.elmopog.gens.listeners.BlockPlace;
import me.elmopog.gens.listeners.JoinListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

/*TODO
    - Add config.yml to easily add/remove/change generators
    - Make gens do something when placed
    - * MORE COMING *
 */

public final class Gens extends JavaPlugin {

    @Override
    public void onEnable() {
        //Registers listeners
        Bukkit.getServer().getPluginManager().registerEvents(new BlockPlace(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new BlockBreak(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new JoinListener(), this);
        //

        //Registers commands
        getCommand("givegen").setExecutor(new getGen());
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
}
