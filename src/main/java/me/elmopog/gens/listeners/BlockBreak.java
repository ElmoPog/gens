package me.elmopog.gens.listeners;

import me.elmopog.gens.data;
import me.elmopog.gens.utils.format;
import me.elmopog.gens.utils.genUtils;
import me.elmopog.gens.utils.prefix;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.UUID;

public class BlockBreak implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent e){

        Player p = e.getPlayer();

        String loc = genUtils.locationToText(e.getBlock().getLocation());
        if(!genUtils.isGen(loc)){
            return;
        }

        Player owner = Bukkit.getPlayer(UUID.fromString(data.get().getString(loc + ".owner")));
        if(owner != e.getPlayer()){
            e.setCancelled(true);
            prefix.sendPrefix(p);
            format.format(p, "&cError: That is not your generator!");
            return;
        }

        if(p.getInventory().firstEmpty() == -1){
            prefix.sendPrefix(p);
            e.setCancelled(true);
            format.format(p, "&cError: Your inventory is full!");
            return;
        }

        data.get().options().copyDefaults(true);
        genUtils.removeGenSlots(p, 1);
        data.get().set(loc, null);

        genUtils.giveGen(p, data.get().getString(e.getPlayer().getUniqueId() + ".gens." + loc));

        data.get().set(e.getPlayer().getUniqueId() + ".gens." + loc, null);

        p.sendTitle(ChatColor.translateAlternateColorCodes('&', "&cRemoved generator"), genUtils.getFormattedSlots(p), 1, 20, 1);

        data.save();

        p.playSound(p.getLocation(), Sound.BLOCK_RESPAWN_ANCHOR_DEPLETE, 1, 10);

        e.setCancelled(true);

        e.getBlock().setType(Material.AIR);

    }
}