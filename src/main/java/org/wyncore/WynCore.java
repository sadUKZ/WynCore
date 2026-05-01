package org.wyncore;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.wyncore.API.SpawnPointAPI;
import org.wyncore.Framework.Command.CommandFramework;
import org.wyncore.Framework.ConfigAPI;
import org.wyncore.Framework.ItemBuilder.InventoryAPI;
import org.wyncore.Framework.ItemBuilder.ItemAPI;
import org.wyncore.Framework.ItemBuilder.ItemNBT;
import org.wyncore.Framework.MenuGUI.GuiAPI;
import org.wyncore.Listeners.PlayerJoinEvent;

public final class WynCore extends JavaPlugin {

    public static WynCore getInstance;
    ConfigAPI SpawnPoint = ConfigAPI.create(this,"SpawnPoint.yml");
    private ItemAPI itemAPI;
    private InventoryAPI inventoryAPI;
    private ItemNBT itemNBT;
    private CommandFramework commandFramework;

    @Override
    public void onEnable() {
        GuiAPI.init(this);
        getInstance = this;
        commandFramework = new CommandFramework(this);
        inventoryAPI = new InventoryAPI(this);
        itemAPI = new ItemAPI(this);
        itemNBT = new ItemNBT(this);
        SpawnPointAPI.LoadLocations();
        RegisterListeners();
    }

    @Override
    public void onDisable() {
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
