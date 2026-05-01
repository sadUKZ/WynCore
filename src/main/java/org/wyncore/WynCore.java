package org.wyncore;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.wyncore.API.SpawnPointAPI;
import org.wyncore.Framework.Command.Core.CommandFramework;
import org.wyncore.Framework.ConfigAPI;
import org.wyncore.Framework.ItemBuilder.InventoryAPI;
import org.wyncore.Framework.ItemBuilder.ItemAPI;
import org.wyncore.Framework.ItemBuilder.ItemNBT;
import org.wyncore.Framework.MenuGUI.GuiAPI;
import org.wyncore.Listeners.PlayerJoinEvent;

public final class WynCore extends JavaPlugin {

    public static WynCore getInstance;
    private CommandFramework commandFramework;
    ConfigAPI SpawnPoint = ConfigAPI.create(this,"SpawnPoint.yml");
    private ItemAPI itemAPI;
    private InventoryAPI inventoryAPI;
    private ItemNBT itemNBT;

    public static int temp = 0;

    public static int getTemp() {
        return temp;
    }

    @Override
    public void onEnable() {
        GuiAPI.init(this);
        getInstance = this;
        inventoryAPI = new InventoryAPI(this);
        itemAPI = new ItemAPI(this);
        itemNBT = new ItemNBT(this);
        commandFramework = new CommandFramework(this);
        commandFramework.scan("org.wyncore.Commands");
        SpawnPointAPI.LoadLocations();
        RegisterListeners();
        Bukkit.getScheduler().runTaskTimer(this, new Runnable() {
            @Override
            public void run() {
                temp = temp + 1;
                getLogger().info(temp + "");
            }
        },10*20L, 20L);

    }

    @Override
    public void onDisable() {
    }

    public CommandFramework getCommandFramework() {
        return commandFramework;
    }

    public void RegisterListeners(){
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerJoinEvent(),this);
    }

    public static String getPrefix(){return "§7[§3Wyn§6Empire§7] ";}

    public static WynCore getGetInstance() {
        return getInstance;
    }

    public ItemAPI getItemAPI() {
        return itemAPI;
    }

    public ItemNBT getItemNBT() {
        return itemNBT;
    }

    public InventoryAPI getInventoryAPI() {
        return inventoryAPI;
    }
}
