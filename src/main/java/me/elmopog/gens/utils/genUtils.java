package me.elmopog.gens.utils;

import me.elmopog.gens.data;
import org.bukkit.*;
import org.bukkit.entity.Player;
import sun.jvm.hotspot.opto.Block;

import java.util.*;

public class genUtils {
    //The list with names for all generators
    private static final List<String> gensList = new ArrayList<>(Arrays.asList("elmo", "joe", "lean"));

    //Hashmap with all gens and their blocks
    private static final Map<String, Material> genMap = new HashMap<String, Material>(){{
        put("joe", Material.MELON);
        put("elmo", Material.REDSTONE_BLOCK);
        put("lean", Material.MAGENTA_CONCRETE);
    }};

    //Hashmap with all gens and their gen items
    private static final Map<String, Material> genItemMap = new HashMap<String, Material>(){{
        put("joe", Material.MELON_SLICE);
        put("elmo", Material.REDSTONE);
        put("lean", Material.MAGENTA_DYE);
    }};

    //Getter for list with generators
    public static List<String> getGensList(){
        return gensList;
    }

    //Getter for map with generators
    public static Map<String, Material> getGenMap(){
        return genMap;
    }

    //Getter for map with items
    public static Map<String, Material> getGenItemMap(){
        return genItemMap;
    }

    //Check if block at location is a generator
    public static boolean isGen(String location){

        data.get().options().copyDefaults(true);
        String check = data.get().getString(location + ".type");
        if(check != null) return true;
        return false;
    }

    //Convert text into location
    public static Location textToLocation(String input){
        String[] split = input.split(" ");

        double x = Double.parseDouble(split[0]);
        double y = Double.parseDouble(split[1]);
        double z = Double.parseDouble(split[2]);
        World world = Bukkit.getWorld(split[3]);

        Location loc = new Location(world, x, y, z);

        return loc;
    }

    //Convert location into text
    public static String locationToText(double x, double y, double z, String world){
        int x1 = (int) x;
        int y1 = (int) y;
        int z1 = (int) z;

        String locationText = x1 + " " + y1 + " " + z1 + " " + world;
        return locationText;
    }

    //Get gen slots of player
    public static int getGenSlots(Player player){
        data.get().options().copyDefaults(true);
        return (int) data.get().get(player.getUniqueId() + ".gensPlaced");
    }
    //Get gen slots of player uuid overload
    public static int getGenSlots(UUID UUID){
        data.get().options().copyDefaults(true);
        return (int) data.get().get(UUID + ".gensPlaced");
    }

    //Set gen slots of a player
    public static void setGenSlots(Player player, int newValue){
        data.get().options().copyDefaults(true);
        data.get().set(player.getUniqueId() + ".gensPlaced", newValue);
        data.save();
    }
    public static void setGenSlots(UUID UUID, int newValue){
        data.get().options().copyDefaults(true);
        data.get().set(UUID + ".gensPlaced", newValue);
        data.save();
    }

    //Add to gen slots of player
    public static void addGenSlots(Player player, int amount){
        data.get().options().copyDefaults(true);
        data.get().set(player.getUniqueId() + ".gensPlaced", getGenSlots(player) + amount);
        data.save();
    }
    public static void addGenSlots(UUID uuid, int amount){
        data.get().options().copyDefaults(true);
        data.get().set(uuid + ".gensPlaced", getGenSlots(uuid) + amount);
        data.save();
    }

    //Remove gen slots from players
    public static void removeGenSlots(Player player, int amount){
        data.get().options().copyDefaults(true);
        data.get().set(player.getUniqueId() + ".gensPlaced", getGenSlots(player) - amount);
        data.save();
    }
    public static void removeGenSlots(UUID uuid, int amount){
        data.get().options().copyDefaults(true);
        data.get().set(uuid + ".gensPlaced", getGenSlots(uuid) - amount);
        data.save();
    }

    //Get max gen slots of players
    public static int getMaxSlots(Player player){
        data.get().options().copyDefaults(true);
        return data.get().getInt(player.getUniqueId() + ".gensMax");
    }

    //Get nice format of genslots
    public static String getFormattedSlots(Player player){
        int slots = getGenSlots(player);
        int max = getMaxSlots(player);
        return ChatColor.translateAlternateColorCodes('&', "&8(&b" + slots + "&7/&3" + max + "&8)");
    }

    //Generate items
    public static void generate(Player player){
        for(String key : data.get().getConfigurationSection(player.getUniqueId().toString()).getKeys(false)){
            System.out.println(data.get().getString(player.getUniqueId() + ".gensPlaced" + key));
        }
    }
}
