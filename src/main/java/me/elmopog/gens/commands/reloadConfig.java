package me.elmopog.gens.commands;

import me.elmopog.gens.Gens;
import me.elmopog.gens.utils.genUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class reloadConfig implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        Gens.getPlugin().reloadConfig();

        genUtils.setAll();

        Gens.getPlugin().saveDefaultConfig();
        return true;
    }
}
