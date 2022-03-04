package me.elmopog.gens.listeners;

import me.elmopog.gens.data;
import me.elmopog.gens.utils.format;
import me.elmopog.gens.utils.genUtils;
import me.elmopog.gens.utils.prefix;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.UUID;

public class BlockBreak implements Listener {
    @EventHandler
    public void onBlockBreak(BlockBreakEvent e){

        Player p = e.getPlayer();

        String loc = genUtils.locationToText(e.getBlock().getX(), e.getBlock().getY(), e.getBlock().getZ(), e.getBlock().getWorld().getName());
        if(!genUtils.isGen(loc)){
            return;
        }

        Player owner = Bukkit.getPlayer((UUID) data.get().get(loc + ".owner"));
        if(owner != e.getPlayer()){
            e.setCancelled(true);
            prefix.sendPrefix(p);
            format.format(p, "&cError: That is not your generator!");
            return;
        }
        data.get().options().copyDefaults(true);
        data.get().set(loc, null);
        data.save();



    }
}