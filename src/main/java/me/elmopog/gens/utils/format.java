package me.elmopog.gens.utils;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class format {
    //Used to send formatted messages easier
    public static void format(Player player, String input){
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', input));
    }
    public static void format(CommandSender sender, String input){
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', input));
    }
}
