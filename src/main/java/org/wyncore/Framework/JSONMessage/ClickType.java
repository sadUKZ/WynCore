package org.wyncore.Framework.JSONMessage;

import net.md_5.bungee.api.chat.ClickEvent;

public enum ClickType {

    RUN_COMMAND(ClickEvent.Action.RUN_COMMAND),
    SUGGEST_COMMAND(ClickEvent.Action.SUGGEST_COMMAND),
    OPEN_URL(ClickEvent.Action.OPEN_URL),
    COPY_TO_CLIPBOARD(ClickEvent.Action.COPY_TO_CLIPBOARD);

    private final ClickEvent.Action action;

    ClickType(ClickEvent.Action action) {
        this.action = action;
    }

    public ClickEvent.Action getAction() {
        return action;
    }
}