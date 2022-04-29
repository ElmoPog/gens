package me.elmopog.gens.commands;

import me.elmopog.gens.data;
import me.elmopog.gens.utils.format;
import me.elmopog.gens.utils.genUtils;
import me.elmopog.gens.utils.prefix;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class genSlots implements TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(args.length < 2) {
            if(sender instanceof Player) {
                prefix.sendPrefix((Player) sender);
            }
            format.format(sender, "&cUsage: /genslots <player> <set|add|remove> <amount>");
        }

        Player target = Bukkit.getPlayer(args[0]);

        if(!StringUtils.isNumeric(args[2])){
            sender.sendMessage(ChatColor.RED + "Error: " + ChatColor.WHITE + args[2] + ChatColor.RED + " is not a number!");
            return true;
        }

        if(target == null) {
            if(sender instanceof Player) {
                prefix.sendPrefix((Player) sender);
            }
            format.format(sender, "&cPlayer not found.");
            return true;
        }

        if(args[1].equalsIgnoreCase("set")) {
            if(Integer.parseInt(args[2]) <= 0) {
                if(sender instanceof Player) {
                    prefix.sendPrefix((Player) sender);
                }
                format.format(sender, "&cGen slots can't be less than 0.");
                return true;
            }
            genUtils.setSlots(target, Integer.parseInt(args[2]));
        }
        if(args[1].equalsIgnoreCase("add")) {
            genUtils.addSlots(target, Integer.parseInt(args[2]));
        }
        if(args[1].equalsIgnoreCase("remove")) {
            if(genUtils.getMaxSlots(target) - Integer.parseInt(args[2]) < 0) {
                if(sender instanceof Player) {
                    prefix.sendPrefix((Player) sender);
                }
                format.format(sender, "&cGen slots can't be less than 0.");
                return true;
            }
            genUtils.removeSlots(target, Integer.parseInt(args[2]));
        }

        if(sender instanceof Player) {
            prefix.sendPrefix((Player) sender);
        }
        sender.sendMessage(ChatColor.WHITE + target.getName() + ChatColor.GRAY + " now has " + ChatColor.WHITE + genUtils.getMaxSlots(target) + ChatColor.GRAY + " gen slots.");
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        List<String> list = new ArrayList<>();
        if(args.length == 2) {
            list.add("set");
            list.add("add");
            list.add("remove");
            return list;
        }
        if(args.length == 3) {
            list.add("1");
            list.add("2");
            list.add("3");
            list.add("4");
            list.add("5");
            list.add("10");
            list.add("32");
            list.add("64");
            return list;
        }
        return null;
    }
}
