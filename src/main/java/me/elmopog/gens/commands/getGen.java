package me.elmopog.gens.commands;

import me.elmopog.gens.utils.center;
import me.elmopog.gens.utils.format;
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
    //The list with names for all generators
    private static final List<String> gensList = new ArrayList<>(Arrays.asList("elmo", "joe"));

    //Hashmap with all gens and their blocks
    private static final Map<String, Material> genMap = new HashMap<String, Material>(){{
        put("joe", Material.MELON);
        put("elmo", Material.REDSTONE_BLOCK);
    }};

    //Getter for list with generators
    public static List<String> getGensList(){
        return gensList;
    }

    //Getter for map with generators
    public static Map<String, Material> getGenMap(){
        return genMap;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Player p = null;
        if(sender instanceof Player){
            p = (Player) sender;
        }
        if(p == null){
            return true;
        }
        if(args.length < 2){
            p.sendMessage("e");
            sendUsage((Player) sender);
            return true;
        }
        int amount = 1;
        if(args.length > 3){
            sendUsage(p);
            return true;
        }
        //Checks if arg-3 (amount) is a number
        if(args.length == 3){
            if(StringUtils.isNumeric(args[2])){
                amount = Integer.parseInt(args[2]);
            }else{
                prefix.sendPrefix(p);
                p.sendMessage(ChatColor.RED + "Error: " + ChatColor.WHITE + args[2] + ChatColor.RED + " is not a number!");
            }
        }
        //Sets some variables for ease of args use
        Player player = Bukkit.getPlayer(args[0]);
        String type = args[1].toLowerCase();
        String[] itemAsList = StringUtils.capitalize(getGenMap().get(type).toString().toLowerCase()).split("_");
        String item = itemAsList[0];

        //Checks if player exists
        if(player == null){
            prefix.sendPrefix(p);
            format.format(p, "&cError: &f" + args[0] + " &cis not a valid player!");
            return true;
        }

        //Checks if arg-2 is a valid type
        if(!getGensList().contains(args[1].toLowerCase())){
            prefix.sendPrefix(p);
            p.sendMessage(ChatColor.RED + "Error: " + ChatColor.WHITE + args[1] + ChatColor.RED + " is not a valid gen!");
            return true;
        }
        prefix.sendPrefix(p);
        format.format(p, "&aSuccessfully gave &f" + player.getName() + " &aa &3" + type + " &fgenerator!");

        ItemStack gen = new ItemStack(getGenMap().get(type));
        ItemMeta meta = gen.getItemMeta();
        meta.setDisplayName(ChatColor.RED + StringUtils.capitalize(type) + ChatColor.WHITE + " generator");
        meta.setLore(new ArrayList<>(Arrays.asList(ChatColor.WHITE + "-" + ChatColor.DARK_AQUA + " Type: " + ChatColor.RED + StringUtils.capitalize(type),
                ChatColor.WHITE + "-" + ChatColor.DARK_AQUA + " Generates: " + ChatColor.RED + item)));
        gen.setItemMeta(meta);
        player.getInventory().addItem(gen);
        return true;
    }

    private static void sendUsage(Player p){
        prefix.sendPrefix(p);
        format.format(p ,"&cUsage: &f/givegen &b<player> <type> &3(amount)");
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        List<String> list = new ArrayList<>();
        if(args.length == 1){
            for(Player loop : Bukkit.getOnlinePlayers()){
                list.add(loop.getName());
            }
        }else if(args.length == 2){
            List<String> newList = Stream.concat(list.stream(), getGensList().stream())
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
