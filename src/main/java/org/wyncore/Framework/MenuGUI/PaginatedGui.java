package org.wyncore.Framework.MenuGUI;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class PaginatedGui<T> extends Gui {

    private final List<T> entries = new ArrayList<>();
    private final List<Integer> contentSlots = new ArrayList<>();
    private int page = 0;

    private EntryRenderer<T> renderer;

    public PaginatedGui(String title, int rows) {
        super(title, rows);
    }

    public PaginatedGui<T> slots(int... slots) {
        contentSlots.clear();
        for (int slot : slots) contentSlots.add(slot);
        return this;
    }

    public PaginatedGui<T> entries(List<T> entries) {
        this.entries.clear();
        this.entries.addAll(entries);
        return this;
    }

    public PaginatedGui<T> renderer(EntryRenderer<T> renderer) {
        this.renderer = renderer;
        return this;
    }

    public int page() {
        return page;
    }

    public int maxPage() {
        if (contentSlots.isEmpty()) return 0;
        return Math.max(0, (int) Math.ceil(entries.size() / (double) contentSlots.size()) - 1);
    }

    public void nextPage(GuiSession session) {
        if (page < maxPage()) {
            page++;
            render(session);
        }
    }

    public void previousPage(GuiSession session) {
        if (page > 0) {
            page--;
            render(session);
        }
    }

    @Override
    public void render(GuiSession session) {
        super.render(session);

        int start = page * contentSlots.size();

        for (int i = 0; i < contentSlots.size(); i++) {
            int slot = contentSlots.get(i);
            int index = start + i;

            if (index >= entries.size()) {
                session.setItemDiff(slot, null);
                removeButton(slot);
                continue;
            }

            T entry = entries.get(index);
            GuiButton button = renderer.render(entry, index);
            button(slot, button);
            session.setItemDiff(slot, button.item(session));
        }

        button(size() - 9, GuiButton.dynamic(
                s -> ItemBuilder.of(Material.ARROW)
                        .name("§aPágina anterior")
                        .lore("§7Página atual: §f" + (page + 1) + "/" + (maxPage() + 1))
                        .build(),
                click -> previousPage(click.session())
        ));

        button(size() - 1, GuiButton.dynamic(
                s -> ItemBuilder.of(Material.ARROW)
                        .name("§aPróxima página")
                        .lore("§7Página atual: §f" + (page + 1) + "/" + (maxPage() + 1))
                        .build(),
                click -> nextPage(click.session())
        ));

        session.setItemDiff(size() - 9, buttonAt(size() - 9).item(session));
        session.setItemDiff(size() - 1, buttonAt(size() - 1).item(session));
    }

    public interface EntryRenderer<T> {
        GuiButton render(T entry, int index);
    }
}