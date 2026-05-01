package org.wyncore.API;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.wyncore.Framework.ConfigAPI;
import org.wyncore.WynCore;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class SpawnPointAPI {

    static ConfigAPI config = ConfigAPI.create(WynCore.getGetInstance(), "SpawnPoint");
    static List<Location> locationList = new ArrayList<>();
    public static void LoadLocations(){
        if(config.getConfig().getString("Locations") == null) return;
        for(String point : Objects.requireNonNull(config.getConfig().getConfigurationSection("Locations.")).getKeys(false)){
            String path = "Locations." + point + ".";
            World world = Bukkit.getWorld(config.getString(path + ".World"));
            Location loc = new Location(world, config.getDouble(path + ".X"),config.getDouble(path + ".Y"),config.getDouble(path + ".Z"), (float) config.getDouble(path + ".Yaw"), (float) config.getDouble(path + ".Pitch"));
            locationList.add(loc);
        }
        WynCore.getGetInstance().getLogger().info(WynCore.getPrefix() + "§fForam carregados §e" + locationList.size() + "§f Locations.");
    }

    public static Location RandomLocation(){
        Random random = new Random();
        if(locationList.isEmpty()){return null;};
        return locationList.get(random.nextInt(locationList.size()));
    }

    public static boolean contains(Location location){
        return locationList.contains(location);
    }

    public static void remove(Location location){
        if(locationList.contains(location)){
            locationList.remove(location);
        }
        return;
    }

    public static void addLocation(Location location){
        locationList.add(location);
    }

    public static List<Location> getLocationList() {
        return locationList;
    }
}
