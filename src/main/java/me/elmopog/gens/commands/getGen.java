package me.elmopog.gens.commands;

import me.elmopog.gens.utils.center;
import me.elmopog.gens.utils.format;
import me.elmopog.gens.utils.genUtils;
import me.elmopog.gens.utils.prefix;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class getGen implements TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        //Checks if player is sender/exists to avoid NPE's
        Player p = null;
        if(sender instanceof Player){
            p = (Player) sender;
        }
        if(p == null){
            return true;
        }

        //Checks if minimum amount of arguments are provided
        if(args.length < 2){
            sendUsage((Player) sender);
            return true;
        }

        //Checks if arguments are more than needed
        if(args.length > 3){
            sendUsage(p);
            return true;
        }

        //Sets defaulting value for amount
        int amount = 1;

        //Checks if arg-3 (amount) is a number && sets amount to the amount defined by the player
        if(args.length == 3){
            if(StringUtils.isNumeric(args[2])){
                amount = Integer.parseInt(args[2]);
            }else{
                prefix.sendPrefix(p);
                p.sendMessage(ChatColor.RED + "Error: " + ChatColor.WHITE + args[2] + ChatColor.RED + " is not a number!");
            }
        }

        Player player = Bukkit.getPlayer(args[0]);

        //Checks if player exists
        if(player == null){
            prefix.sendPrefix(p);
            format.format(p, "&cError: &f" + args[0] + " &cis not a valid player!");
            return true;
        }

        //Checks if arg-2 is a valid type
        if(!genUtils.getGensList().contains(args[1])){
            prefix.sendPrefix(p);
            p.sendMessage(ChatColor.RED + "Error: " + ChatColor.WHITE + args[1] + ChatColor.RED + " is not a valid gen!");
            return true;
        }

        //Sets some variables for ease of args use
        String type = args[1];

        //
        prefix.sendPrefix(p);
        format.format(p, "&aSuccessfully gave &f" + player.getName() + " &aa &3" + type + " &fgenerator!");

        genUtils.giveGen(p, type);

        return true;
    }
    private static void sendUsage(Player p){
        prefix.sendPrefix(p);
        format.format(p ,"&cUsage: &f/givegen &b<player> <type> &3(amount)");
    }

    //AUTOCOMPLETE FOR GENS GIVE COMMAND
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        List<String> list = new ArrayList<>();
        if(args.length == 1){
            for(Player loop : Bukkit.getOnlinePlayers()){
                list.add(loop.getName());
            }
        }else if(args.length == 2){
            //Adds <type> to the autocomplete list
            List<String> newList = Stream.concat(list.stream(), genUtils.getGensList().stream())
                    .collect(Collectors.toList());
            newList.add("<type>");
            return newList;
        }else if(args.length == 3){
            list.add("<amount>");
            list.add("1");
            list.add("2");
            list.add("3");
            list.add("4");
            list.add("5");
            list.add("10");
            list.add("16");
            list.add("32");
            list.add("64");
        }
        return list;
    }
}
