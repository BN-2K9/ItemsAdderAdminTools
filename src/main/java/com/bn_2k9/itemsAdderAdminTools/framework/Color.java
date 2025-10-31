package com.bn_2k9.itemsAdderAdminTools.framework;

import com.bn_2k9.itemsAdderAdminTools.ItemsAdderAdminTools;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

public class Color {

    // Deserialize through miniMessages.
    public static Component color(String string) {
        return MiniMessage.miniMessage().deserialize(string);
    }

    // Deserialize through miniMessages. And add a prefix.
    public static Component colorPrefix(String string) {
        return MiniMessage.miniMessage().deserialize(ItemsAdderAdminTools.getInstance().getConfig().getString("Prefix") + string);
    }
}
