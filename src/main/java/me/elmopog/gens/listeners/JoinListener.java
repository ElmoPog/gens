package me.elmopog.gens.listeners;

import me.elmopog.gens.data;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent e){

        //Sets default values for YML
        data.get().options().copyDefaults(true);
        if(data.get().get(e.getPlayer().getUniqueId() + ".gensPlaced") == null){
            data.get().set(e.getPlayer().getUniqueId() + ".gensPlaced", 0);
            data.get().set(e.getPlayer().getUniqueId() + ".gensMax", 5);
            data.save();
        }
    }
}
