package org.wyncore.Commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.wyncore.API.SpawnPointAPI;
import org.wyncore.Framework.Command.Annotation.CommandInfo;
import org.wyncore.Framework.Command.Annotation.DefaultCommand;
import org.wyncore.Framework.Command.Annotation.SubCommandInfo;
import org.wyncore.Framework.Command.Core.CommandContext;
import org.wyncore.Framework.Command.Core.CommandSenderType;
import org.wyncore.Framework.ConfigAPI;
import org.wyncore.Framework.JSONMessage.JsonMessage;
import org.wyncore.WynCore;

import java.util.Random;

@CommandInfo(
        name = "spawn",
        description = "return spawn",
        permission = "wyncore.spawn",
        sender = CommandSenderType.PLAYER,
        aliases = {""}

)
public class Spawn {

        @SubCommandInfo(
                name = "set",
                permission = "wyncore.spawn.set",
                description = "Set new point spawn",
                usage = "/spawn set",
                sender = CommandSenderType.PLAYER
        )
        public void set(CommandContext context){
            Player getPlayer = (Player) context.sender();
            Random random = new Random();
            int id = random.nextInt(50000000);
            ConfigAPI config = ConfigAPI.load(WynCore.getGetInstance(), "SpawnPoint.yml");
            config.set("Locations.Point-" + id + ".World", getPlayer.getLocation().getWorld().getName());
            config.set("Locations.Point-" + id + ".X", getPlayer.getLocation().getBlockX());
            config.set("Locations.Point-" + id + ".Y", getPlayer.getLocation().getBlockY());
            config.set("Locations.Point-" + id + ".Z", getPlayer.getLocation().getBlockZ());
            config.set("Locations.Point-" + id + ".Pitch", getPlayer.getLocation().getPitch());
            config.set("Locations.Point-" + id + ".Yaw", getPlayer.getLocation().getYaw());
            config.save();
            context.sender().sendMessage(WynCore.getPrefix() + "§aNew point §f" + id);
            SpawnPointAPI.addLocation(getPlayer.getLocation());
        }

        @SubCommandInfo(
                name = "list",
                permission = "wyncore.spawn.list",
                description = "List point spawn",
                usage = "/spawn List",
                sender = CommandSenderType.PLAYER
        )
        public void list(CommandContext context){
            Player getPlayer = context.getPlayer();
            if(SpawnPointAPI.getLocationList().isEmpty()){
                getPlayer.sendMessage(WynCore.getPrefix() + "§cNenhuma localização foi encontrada.");
                return;
            }
            getPlayer.sendMessage(WynCore.getPrefix() + "§aLista de todos §fSpawnPoint: ");
            ConfigAPI config = ConfigAPI.create(WynCore.getGetInstance(), "SpawnPoint");
            for(String section : config.getConfig().getConfigurationSection("Locations").getKeys(false)){
                String path = "Locations." + section + ".";
                JsonMessage.of("§fSpawnPoint §7ID-" + section).hoverText("World: " + config.getString(path + "World") + "\n"
                + "X: " + config.getDouble(path + "X") + "\n"
                + "Y: " + config.getDouble(path + "Y") + "\n"
                + "Z: " + config.getDouble(path + "Z")
                ).send(getPlayer);
            }
        }

        @SubCommandInfo(
                name = "delete",
                permission = "wyncore.spawn.delete",
                description = "List point spawn",
                usage = "/spawn delete <point>",
                sender = CommandSenderType.PLAYER
        )
        public void delete(CommandContext context){
            String[] args = context.args();
            Player getPlayer = context.getPlayer();
            ConfigAPI config = ConfigAPI.create(WynCore.getGetInstance(), "SpawnPoint");
            if(SpawnPointAPI.getLocationList().isEmpty()){
                getPlayer.sendMessage(WynCore.getPrefix() + "§cNenhuma localização foi encontrada.");
                return;
            }
            if(args.length < 2){
                getPlayer.sendMessage(WynCore.getPrefix() + "§aLista de todos §fSpawnPoint: ");
                for(String section : config.getConfig().getConfigurationSection("Locations").getKeys(false)){
                    String path = "Locations." + section + ".";
                    JsonMessage.of("§fSpawnPoint §7ID-" + section).hoverText("World: " + config.getString(path + "World") + "\n"
                            + "X: " + config.getDouble(path + "X") + "\n"
                            + "Y: " + config.getDouble(path + "Y") + "\n"
                            + "Z: " + config.getDouble(path + "Z") + "\n§cClick para remover."
                    ).suggestCommand("/spawn delete " + section).send(getPlayer);
                }
                return;
            }
            if(config.getString("Locations." + args[1]) == null){
                getPlayer.sendMessage(WynCore.getPrefix() + "§cNenhuma localização foi encontrada.");
                return;
            }
            String patch = "Locations." + args[1] + ".";
            Location location = new Location(Bukkit.getWorld(config.getString(patch + "World")), config.getDouble(patch + "X"), config.getDouble(patch + "Y"), config.getDouble(patch + "Z"), (float) config.getDouble(patch + "Yaw"), (float) config.getDouble(patch + "Pitch"));
            if(!SpawnPointAPI.contains(location)){
                getPlayer.sendMessage(WynCore.getPrefix() + "§cNenhuma localização foi encontrada.");
                return;
            }
            config.set("Locations." + args[1], null);
            config.save();
            SpawnPointAPI.remove(location);
            getPlayer.sendMessage(WynCore.getPrefix() + "§aLocalização removida.");
        }

        @DefaultCommand
        public void execute(CommandContext sender){
            if(SpawnPointAPI.RandomLocation() == null){
                sender.getPlayer().sendMessage(WynCore.getPrefix() + "§cNenhuma localização foi encontrada.");
                return;
            }
            sender.getPlayer().teleport(SpawnPointAPI.RandomLocation());
            sender.sender().sendMessage(WynCore.getPrefix() + "§aTeleportado.");
        }


}
