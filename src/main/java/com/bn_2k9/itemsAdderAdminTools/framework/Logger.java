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
        switch(type) {
            case INFO -> {
                String ansi = ANSIComponentSerializer.builder()
                        .flattener(ComponentFlattener.basic())
                        .colorLevel(ColorLevel.TRUE_COLOR)
                        .build()
                        .serialize(MiniMessage.miniMessage().deserialize("<green>INFO <dark_grey>>> <white>" + message));
                Bukkit.getServer().getConsoleSender().sendMessage(ansi);
            }
            case WARN -> {
                String ansi = ANSIComponentSerializer.builder()
                        .flattener(ComponentFlattener.basic())
                        .colorLevel(ColorLevel.TRUE_COLOR)
                        .build()
                        .serialize(MiniMessage.miniMessage().deserialize("<gold>WARN <dark_grey>>> <white>" + message));
                Bukkit.getServer().getConsoleSender().sendMessage(ansi);
            }
            case ERROR -> {
                String ansi = ANSIComponentSerializer.builder()
                        .flattener(ComponentFlattener.basic())
                        .colorLevel(ColorLevel.TRUE_COLOR)
                        .build()
                        .serialize(MiniMessage.miniMessage().deserialize("<red>ERROR <dark_grey>>> <white>" + message));
                Bukkit.getServer().getConsoleSender().sendMessage(ansi);
            }
            case DEBUG -> {
                if (ItemsAdderAdminTools.getInstance().getConfig().getBoolean("Debug")) {
                    String ansi = ANSIComponentSerializer.builder()
                            .flattener(ComponentFlattener.basic())
                            .colorLevel(ColorLevel.TRUE_COLOR)
                            .build()
                            .serialize(MiniMessage.miniMessage().deserialize("<yellow>DEBUG <dark_grey>>> <white>" + message));
                    Bukkit.getServer().getConsoleSender().sendMessage(ansi);
                }
            }
        }
    }
}
