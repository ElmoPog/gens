package me.elmopog.gens.utils;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class format {
    public static void format(Player player, String input){
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', input));
    }
}
