package me.elmopog.gens.listeners;

import net.kyori.adventure.text.Component;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.StringJoiner;

public class BlockPlace implements Listener {
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e){
        Player p = e.getPlayer();
        Block block = e.getBlockPlaced();
        ItemStack hand = p.getInventory().getItemInMainHand();

        StringJoiner sbl = new StringJoiner(" ");
        if(hand.lore() != null && (!(hand.lore().size()==0))){
            for(String line : hand.getItemMeta().getLore()){
                sbl.add(line);
            }
        }
        String lore = (!(sbl.toString().equals(""))) ? sbl.toString() : "null";
        p.sendMessage("hand: " + hand.getType() + ", block: " + block.getType() + ", lore: \"" + lore + "\"");
    }
}
