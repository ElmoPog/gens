package me.elmopog.gens.listeners;

import me.elmopog.gens.commands.getGen;
import me.elmopog.gens.data;
import me.elmopog.gens.utils.format;
import me.elmopog.gens.utils.genUtils;
import me.elmopog.gens.utils.prefix;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
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

        data.get().options().copyDefaults(true);

        Player p = e.getPlayer();
        Block block = e.getBlockPlaced();
        ItemStack hand = p.getInventory().getItemInMainHand();


        //Makes lore into one string separated by spaces
        StringJoiner sbl = new StringJoiner(" ");
        if(hand.lore() != null && (!(hand.lore().size()==0))){
            for(String line : hand.getItemMeta().getLore()){
                sbl.add(line);
            }
        }

        //Checks if the lore is empty, if so it'll put "null" instead of ""
        String lore = (!(sbl.toString().equals(""))) ? sbl.toString() : "null";
        //p.sendMessage("hand: " + hand.getType() + ", block: " + block.getType() + ", lore: \"" + lore + "\"");
        if(lore.equalsIgnoreCase("null")){
            return;
        }

        String splitlore[] = lore.split(" ");

        //Checks if lore is the gen length of a lore
        if(splitlore.length < 6 ){
            return;
        }if(splitlore.length > 7){
            return;
        }


        //Sets some variables for ease of use
        String type = ChatColor.stripColor(splitlore[2]);
        Material item = genUtils.getGenItemMap().get(type.toLowerCase());
        String locationText = genUtils.locationToText(e.getBlockPlaced().getLocation());
        //Checks if block at location is somehow already a gen
        if(genUtils.isGen(locationText)){
            e.setCancelled(true);
            prefix.sendPrefix(p);
            format.format(p, "&cError: Block at location &7\"&f" + locationText + "&7\" &cis already a generator!");
            return;
        }

        //Checks if player has enough gen slots

        if(genUtils.getGenSlots(p) == genUtils.getMaxSlots(p)){
            prefix.sendPrefix(p);
            format.format(p, "&cError: Max gens placed! " + genUtils.getFormattedSlots(p));
            e.setCancelled(true);
            return;
        }

        data.get().options().copyDefaults(true);
        //Gen data
        data.get().set(locationText + ".type", type);
        data.get().set(locationText + ".item", item.name());
        data.get().set(locationText + ".owner", e.getPlayer().getUniqueId().toString());
        //Player data
        genUtils.addGenSlots(p, 1);
        data.get().set(p.getUniqueId().toString() + ".gens." + locationText, type);
        //
        p.sendTitle(ChatColor.translateAlternateColorCodes('&', "&aPlaced generator"), genUtils.getFormattedSlots(p), 1, 20, 1);
        data.save();

        p.playSound(p.getLocation(), Sound.BLOCK_GLASS_BREAK, 1, 20);
    }
}
