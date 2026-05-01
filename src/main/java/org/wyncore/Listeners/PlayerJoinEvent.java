package org.wyncore.Listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.wyncore.API.SpawnPointAPI;
import org.wyncore.WynCore;

import java.util.Objects;

public class PlayerJoinEvent implements Listener {

    @EventHandler
    public void PlayerJoin(org.bukkit.event.player.PlayerJoinEvent event){
        Player getPlayer = event.getPlayer();
        getPlayer.setHealth(0.1);
        getPlayer.setAllowFlight(false);
        getPlayer.setSaturation(20);
        if(SpawnPointAPI.RandomLocation() != null){
            getPlayer.teleport(Objects.requireNonNull(SpawnPointAPI.RandomLocation()));
        }
        if(getPlayer.hasPermission("wyncore.fly.bypass")){
            getPlayer.sendMessage(WynCore.getPrefix() + "§aSeu modo de vou foi ativado sozinho.");
            getPlayer.setAllowFlight(true);
        }



    }



}
