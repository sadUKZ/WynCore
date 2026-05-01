package org.wyncore.Commands;


import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.wyncore.Framework.Command.Annotation.CommandInfo;
import org.wyncore.Framework.Command.Annotation.DefaultCommand;
import org.wyncore.Framework.Command.Core.CommandContext;
import org.wyncore.Framework.MenuGUI.*;

import java.util.List;

@CommandInfo(
        name = "testing",
        aliases = {"teste"},
        permission = ""
)

public class CommandTeste {


    @DefaultCommand()
    public void execute(CommandContext context) {


        Gui menu = new Gui("§8Core Menu", 3)
                .updateEvery(20L);

        menu.button(11, GuiButton.dynamic(
                session -> ItemBuilder.of(Material.EMERALD)
                        .name("§aPerfil")
                        .lore(
                                "§7Jogador: §f" + session.player().getName()
                        )
                        .build(),
                click -> click.player().sendMessage("§aSeu perfil foi atualizado.")
        ));

        menu.button(13, GuiButton.of(
                ItemBuilder.of(Material.CHEST)
                        .name("§eLoja")
                        .lore("§7Clique para abrir a loja")
                        .build(),
                click -> openShop(click.player(), menu)
        ));

        menu.button(15, GuiButton.of(
                ItemBuilder.of(Material.BARRIER)
                        .name("§cFechar")
                        .build(),
                GuiClick::close
        ));

        GuiAPI.open(context.getPlayer(), menu);
    }

    private void openShop(Player player, Gui menu) {
        List<Material> itens = List.of(
                Material.DIAMOND,
                Material.EMERALD,
                Material.GOLD_INGOT,
                Material.IRON_INGOT,
                Material.REDSTONE,
                Material.REDSTONE,
                Material.REDSTONE,
                Material.REDSTONE,
                Material.REDSTONE,
                Material.REDSTONE,
                Material.REDSTONE,
                Material.REDSTONE,
                Material.REDSTONE,
                Material.REDSTONE,
                Material.REDSTONE,
                Material.REDSTONE,
                Material.REDSTONE,
                Material.REDSTONE,
                Material.REDSTONE,
                Material.REDSTONE,
                Material.REDSTONE,
                Material.REDSTONE,
                Material.REDSTONE,
                Material.REDSTONE,
                Material.REDSTONE,
                Material.REDSTONE,
                Material.REDSTONE,
                Material.REDSTONE,
                Material.REDSTONE,
                Material.REDSTONE,
                Material.REDSTONE,
                Material.REDSTONE,
                Material.REDSTONE,
                Material.REDSTONE,
                Material.REDSTONE,
                Material.REDSTONE,
                Material.REDSTONE,
                Material.REDSTONE,
                Material.REDSTONE,
                Material.REDSTONE,
                Material.REDSTONE,
                Material.REDSTONE,
                Material.REDSTONE,
                Material.REDSTONE,
                Material.REDSTONE,
                Material.REDSTONE,
                Material.REDSTONE,
                Material.REDSTONE,
                Material.REDSTONE,
                Material.REDSTONE,
                Material.REDSTONE,
                Material.REDSTONE,
                Material.LAPIS_LAZULI
        );

        PaginatedGui<Material> loja = new PaginatedGui<Material>("§8Loja", 6)
                .slots(
                        10, 11, 12, 13, 14, 15, 16,
                        19, 20, 21, 22, 23, 24, 25,
                        28, 29, 30, 31, 32, 33, 34
                )
                .entries(itens)
                .renderer((material, index) -> GuiButton.of(
                        ItemBuilder.of(material)
                                .name("§aComprar " + material.name())
                                .lore("§7Clique para comprar")
                                .build(),
                        click -> click.player().sendMessage("§aComprado: §f" + material.name())
                ));
                loja.button(49, GuiButton.of(
                        ItemBuilder.of(Material.ARROW)
                                .name("§aVoltar")
                                .lore("§7Retornar ao menu principal")
                                .build(),
                        click -> click.open(menu)
                ));


        GuiAPI.open(player, loja);
    }
}
