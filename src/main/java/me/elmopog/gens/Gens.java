package me.elmopog.gens;

import me.elmopog.gens.commands.getGen;
import me.elmopog.gens.listeners.BlockPlace;
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
        //

        //Registers commands
        getCommand("givegen").setExecutor(new getGen());
        //
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
