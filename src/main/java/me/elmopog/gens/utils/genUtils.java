package me.elmopog.gens.utils;

import me.elmopog.gens.Gens;
import me.elmopog.gens.data;
import org.apache.commons.lang.StringUtils;
import org.bukkit.*;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import sun.jvm.hotspot.opto.Block;

import java.util.*;

public class genUtils {


    //The list with names for all generators
    private static List<String> gensList = new ArrayList<>();

    //Hashmap with all gens and their blocks
    private static Map<String, Material> genMap = new HashMap<>();

    //Hashmap with all gens and their gen items
    private static Map<String, Material> genItemMap = new HashMap<>();

    //Hashmap with all item names
    private static Map<Material, String> itemNameMap = new HashMap<>();



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

    //Getter for map with item names
    public static Map<Material, String> getItemNameMap() {
        return itemNameMap;
    }


    //Setter for gensList
    public static void setGensList(List<String> names){
        gensList = names;
    }

    //Setter for genMap
    public static void setGenMap(Map<String, Material> gens){
        genMap = gens;
    }

    //Setter for map genItemMap
    public static void setGenItemMap(Map<String, Material> items){
        genItemMap = items;
    }

    //Setter for itemNameMap
    public static void setItemNameMap(Map<Material, String> names){
        itemNameMap = names;
    }

    //Setter for all configs
    public static void setAll(List<String> nameList, Map<String, Material> genTypeBlock, Map<String, Material> genTypeItem, Map<Material, String> genItemName){
        setGensList(nameList);
        setGenMap(genTypeBlock);
        setGenItemMap(genTypeItem);
        setItemNameMap(genItemName);
    }

    public static void setAll(){

        Gens.getPlugin().reloadConfig();

        List<String> nameList = new ArrayList<>();

        Map<String, Material> genTypeBlock = new HashMap<String, Material>();

        Map<String, Material> genTypeItem = new HashMap<>();

        Map<Material, String> genItemName = new HashMap<>();

        for(String key : Gens.getPlugin().getConfig().getConfigurationSection("gens").getKeys(false)){

            //String gen = Gens.getPlugin().getConfig().getString("gens." + key + ".name");
            nameList.add(key);

            String block = Gens.getPlugin().getConfig().getString("gens." + key + ".block");
            genTypeBlock.put(key, Material.getMaterial(block.toUpperCase()));

            String item = Gens.getPlugin().getConfig().getString("gens." + key + ".generates");
            genTypeItem.put(key, Material.getMaterial(item.toUpperCase()));

            String name = Gens.getPlugin().getConfig().getString("gens." + key + ".itemName");
            genItemName.put(Material.getMaterial(Gens.getPlugin().getConfig().getString("gens." + key + ".generates").toUpperCase()), name);


        }


                gensList = nameList;
                genMap = genTypeBlock;
                genItemMap = genTypeItem;
                itemNameMap = genItemName;

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
    public static String locationToText(Location loc){
        int x1 = (int) loc.getX();
        int y1 = (int) loc.getY();
        int z1 = (int) loc.getZ();
        String world = loc.getWorld().getName();

        String locationText = x1 + " " + y1 + " " + z1 + " " + world;
        return locationText;
    }

    //Get gen slots of player
    public static int getGenSlots(Player player){
        data.get().options().copyDefaults(true);
        return (int) data.get().get(player.getUniqueId().toString() + ".gensPlaced");
    }


    //Get gen slots of player uuid overload
    public static int getGenSlots(UUID UUID){
        data.get().options().copyDefaults(true);
        return (int) data.get().get(UUID + ".gensPlaced");
    }

    //Set gen slots of a player
    public static void setGenSlots(Player player, int newValue){
        data.get().options().copyDefaults(true);
        data.get().set(player.getUniqueId().toString() + ".gensPlaced", newValue);
        data.save();
    }

    public static void setSlots(Player player, int newValue){
        data.get().options().copyDefaults(true);
        data.get().set(player.getUniqueId().toString() + ".gensMax", newValue);
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
        data.get().set(player.getUniqueId().toString() + ".gensPlaced", getGenSlots(player) + amount);
        data.save();
    }

    public static void addSlots(Player player, int amount){
        data.get().options().copyDefaults(true);
        data.get().set(player.getUniqueId().toString() + ".gensMax", getMaxSlots(player) + amount);
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
        data.get().set(player.getUniqueId().toString() + ".gensPlaced", getGenSlots(player) - amount);
        data.save();
    }
    public static void removeSlots(Player player, int amount){
        data.get().options().copyDefaults(true);
        data.get().set(player.getUniqueId().toString() + ".gensMax", getMaxSlots(player) - amount);
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
        return data.get().getInt(player.getUniqueId().toString() + ".gensMax");
    }

    //Get nice format of genslots
    public static String getFormattedSlots(Player player){
        int slots = getGenSlots(player);
        int max = getMaxSlots(player);
        return ChatColor.translateAlternateColorCodes('&', "&8(&b" + slots + "&7/&3" + max + "&8)");
    }

    //Give generator
    public static void giveGen(Player player, String type){

        String item = StringUtils.capitalize(genUtils.getGenItemMap().get(type).toString().toLowerCase().replace("_", " "));
        //Makes the gen given look noice

        ItemStack gen = new ItemStack(genUtils.getGenMap().get(type));
        ItemMeta meta = gen.getItemMeta();
        meta.setDisplayName(ChatColor.LIGHT_PURPLE + StringUtils.capitalize(type) + ChatColor.WHITE + " generator");
        meta.setLore(new ArrayList<>(Arrays.asList(ChatColor.WHITE + "-" + ChatColor.DARK_AQUA + " Type: " + ChatColor.LIGHT_PURPLE + type,
                ChatColor.WHITE + "-" + ChatColor.DARK_AQUA + " Generates: " + ChatColor.LIGHT_PURPLE + item)));
        meta.removeItemFlags();
        gen.setItemMeta(meta);
        player.getInventory().addItem(gen);
    }

    //Generate items
    public static void generate(Player player){
        for(String key : data.get().getConfigurationSection(player.getUniqueId().toString() + ".gens").getKeys(false)){

            String type = data.get().getString(player.getUniqueId().toString() + ".gens." + key);
            Material item1 = genUtils.getGenItemMap().get(type);
            Location loc = genUtils.textToLocation(key);

            World world = loc.getWorld();
            loc.setY(loc.getY() + 1.0);
            loc.add(0.5D, 0D, 0.5D);

            ItemStack itemStack = new ItemStack(item1);
            ItemMeta meta = itemStack.getItemMeta();
            meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', getItemNameMap().get(item1)));
            itemStack.setItemMeta(meta);

            Item item = world.dropItem(loc, itemStack);

            item.setVelocity(new Vector(0, 0, 0));

            item.teleport(loc);
        }
    }
}
