package org.wyncore.Commands;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.wyncore.Framework.Command.Annotation.CommandInfo;
import org.wyncore.Framework.Command.Annotation.DefaultCommand;
import org.wyncore.Framework.Command.Annotation.SubCommandInfo;
import org.wyncore.Framework.Command.Core.CommandContext;
import org.wyncore.Framework.Command.Core.CommandSenderType;
import org.wyncore.Framework.JSONMessage.JsonMessage;
import org.wyncore.WynCore;

@CommandInfo(
        name = "gamemode",
        description = "Chance you gamemode.",
        permission = "wyncore.gamemode",
        aliases = {"gm"},
        sender = CommandSenderType.PLAYER
)
public class Gamemode {

    @SubCommandInfo(
            name = "creative",
            permission = "wyncore.gamemode.creative",
            aliases = {"criativo", "1"}
    )
    public void Creative(CommandContext context){
        Player getPlayer = context.getPlayer();
        if(context.args().length > 1){
            Player target = Bukkit.getPlayer(context.arg(1));
            if(target == null){
                context.getPlayer().sendMessage(WynCore.getPrefix() + "§cJogador está offline ou não foi encontrado.");
                return;
            }
            target.setGameMode(GameMode.CREATIVE);
            target.sendMessage(WynCore.getPrefix() + "§aSeu modo de jogo foi alterado para §eCriativo §apor §e" + getPlayer.getName().toUpperCase());
            getPlayer.sendMessage(WynCore.getPrefix() + "§aVocê alterou o modo de jogo do jogador §e" + target.getName() + "§a para §eCriativo");
            return;
        }
        getPlayer.setGameMode(GameMode.CREATIVE);
        getPlayer.sendMessage(WynCore.getPrefix() + "§aSeu modo de jogo foi alterado para §eCriativo");
    }

    @SubCommandInfo(
            name = "survival",
            permission = "wyncore.gamemode.survival",
            aliases = {"survival", "0"}
    )
    public void Survival(CommandContext context){
        Player getPlayer = context.getPlayer();
        if(context.args().length > 1){
            Player target = Bukkit.getPlayer(context.arg(1));
            if(target == null){
                context.getPlayer().sendMessage(WynCore.getPrefix() + "§cJogador está offline ou não foi encontrado.");
                return;
            }
            target.setGameMode(GameMode.SURVIVAL);
            target.sendMessage(WynCore.getPrefix() + "§aSeu modo de jogo foi alterado para §eSurvival §apor §e" + getPlayer.getName().toUpperCase());
            getPlayer.sendMessage(WynCore.getPrefix() + "§aVocê alterou o modo de jogo do jogador §e" + target.getName() + "§a para §eSurvival");
            return;
        }
        getPlayer.setGameMode(GameMode.SURVIVAL);
        getPlayer.sendMessage(WynCore.getPrefix() + "§aSeu modo de jogo foi alterado para §eSurvival");

    }

    @SubCommandInfo(
            name = "espectator",
            permission = "wyncore.gamemode.espectator",
            aliases = {"espectador", "2"}
    )
    public void Spectator(CommandContext context){
        Player getPlayer = context.getPlayer();
        if(context.args().length > 1){
            Player target = Bukkit.getPlayer(context.arg(1));
            if(target == null){
                context.getPlayer().sendMessage(WynCore.getPrefix() + "§cJogador está offline ou não foi encontrado.");
                return;
            }
            target.setGameMode(GameMode.SPECTATOR);
            target.sendMessage(WynCore.getPrefix() + "§aSeu modo de jogo foi alterado para §eEspectador §apor §e" + getPlayer.getName().toUpperCase());
            getPlayer.sendMessage(WynCore.getPrefix() + "§aVocê alterou o modo de jogo do jogador §e" + target.getName() + "§a para §eEspectador");
            return;
        }
        getPlayer.setGameMode(GameMode.SPECTATOR);
        getPlayer.sendMessage(WynCore.getPrefix() + "§aSeu modo de jogo foi alterado para §eEspectador");

    }

    @SubCommandInfo(
            name = "adventure",
            permission = "wyncore.gamemode.adventure",
            aliases = {"adventure", "3"}
    )
    public void Adventure(CommandContext context){
        Player getPlayer = context.getPlayer();
        if(context.args().length > 1){
            Player target = Bukkit.getPlayer(context.arg(1));
            if(target == null){
                context.getPlayer().sendMessage(WynCore.getPrefix() + "§cJogador está offline ou não foi encontrado.");
                return;
            }
            target.setGameMode(GameMode.ADVENTURE);
            target.sendMessage(WynCore.getPrefix() + "§aSeu modo de jogo foi alterado para §eAdventure §apor §e" + getPlayer.getName().toUpperCase());
            getPlayer.sendMessage(WynCore.getPrefix() + "§aVocê alterou o modo de jogo do jogador §e" + target.getName() + "§a para §eAdventure");
            return;
        }
        getPlayer.setGameMode(GameMode.ADVENTURE);
        getPlayer.sendMessage(WynCore.getPrefix() + "§aSeu modo de jogo foi alterado para §eAdventure");

    }


    @DefaultCommand
    public void exute(CommandContext context){
        Player getPlayer = context.getPlayer();
        getPlayer.sendMessage(WynCore.getPrefix() + "§fLista de todos modos de game.");
        JsonMessage.of(WynCore.getPrefix() + "§aCriativo").hoverText("§eCLICK PARA MUDAR").runCommand("/gamemode creative").send(getPlayer);
        JsonMessage.of(WynCore.getPrefix() + "§aSurvival").hoverText("§eCLICK PARA MUDAR").runCommand("/gamemode survival").send(getPlayer);
        JsonMessage.of(WynCore.getPrefix() + "§aEspectador").hoverText("§eCLICK PARA MUDAR").runCommand("/gamemode espectator").send(getPlayer);
        JsonMessage.of(WynCore.getPrefix() + "§aAdventure").hoverText("§eCLICK PARA MUDAR").runCommand("/gamemode adventure").send(getPlayer);
    }

}
