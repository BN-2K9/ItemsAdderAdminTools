package com.bn_2k9.itemsAdderAdminTools.framework;

import com.bn_2k9.itemsAdderAdminTools.ItemsAdderAdminTools;
import net.kyori.adventure.text.flattener.ComponentFlattener;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.ansi.ANSIComponentSerializer;
import net.kyori.ansi.ColorLevel;
import org.bukkit.Bukkit;

public class Logger {

    // Possible LogTypes.
    public enum LogType {
        INFO,
        WARN,
        ERROR,
        DEBUG
    }

    // Method for getting colored log messages.
    public static void log(LogType type, String message) {
        ANSIComponentSerializer ansi = ANSIComponentSerializer.builder()
                .flattener(ComponentFlattener.basic())
                .colorLevel(ColorLevel.TRUE_COLOR)
                .build();

        switch(type) {
            case INFO -> Bukkit.getServer().getConsoleSender().sendMessage(ansi.serialize(MiniMessage.miniMessage().deserialize(ItemsAdderAdminTools.getInstance().getConfig().getString("Prefix", "ItemsAdderAdminTools") + "<green>INFO <dark_grey>>> <white>" + message)));
            case WARN -> Bukkit.getServer().getConsoleSender().sendMessage(ansi.serialize(MiniMessage.miniMessage().deserialize(ItemsAdderAdminTools.getInstance().getConfig().getString("Prefix", "ItemsAdderAdminTools") + "<gold>WARN <dark_grey>>> <white>" + message)));
            case ERROR -> Bukkit.getServer().getConsoleSender().sendMessage(ansi.serialize(MiniMessage.miniMessage().deserialize(ItemsAdderAdminTools.getInstance().getConfig().getString("Prefix", "ItemsAdderAdminTools") + "<red>ERROR <dark_grey>>> <white>" + message)));
            case DEBUG -> {
                if (ItemsAdderAdminTools.getInstance().getConfig().getBoolean("Debug")) {
                    Bukkit.getServer().getConsoleSender().sendMessage(ansi.serialize(MiniMessage.miniMessage().deserialize(ItemsAdderAdminTools.getInstance().getConfig().getString("Prefix", "ItemsAdderAdminTools") + "<yellow>DEBUG <dark_grey>>> <white>" + message)));
                }
            }
        }
    }
}
