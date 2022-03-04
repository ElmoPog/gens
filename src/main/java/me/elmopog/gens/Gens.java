package me.elmopog.gens;

import me.elmopog.gens.commands.getGen;
import me.elmopog.gens.listeners.BlockPlace;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Gens extends JavaPlugin {

    @Override
    public void onEnable() {
        Bukkit.getServer().getPluginManager().registerEvents(new BlockPlace(), this);
        getCommand("givegen").setExecutor(new getGen());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
