package com.bn_2k9.itemsAdderAdminTools;

import com.bn_2k9.itemsAdderAdminTools.commands.ExtraInfo.ExtraInfoCommand;
import com.bn_2k9.itemsAdderAdminTools.commands.Furniture.RefreshFurniture;
import com.bn_2k9.itemsAdderAdminTools.commands.Furniture.ReplaceFurniture;
import com.bn_2k9.itemsAdderAdminTools.framework.ItemsAdder.ItemsAdderCache;
import com.bn_2k9.itemsAdderAdminTools.framework.Logger;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public final class ItemsAdderAdminTools extends JavaPlugin {

    // Static method for getting the instance.
    @Getter
    private static ItemsAdderAdminTools instance;

    @Getter
    private static ItemsAdderCache itemsAdderCache;

    @Override
    public void onEnable() {
        // Registering main instance.
        instance = this;
        // Registering the itemsAdderCache.
        itemsAdderCache = new ItemsAdderCache();
        // Saving the config.
        saveDefaultConfig();
        // Checking if itemsAdder is installed.
        if (!isItemsadderInstalled()) {
            Logger.log(Logger.LogType.ERROR, "ItemsAdder isn't installed. Please install Itemsadder!");
            Bukkit.getPluginManager().disablePlugin(instance);
        }
        // Registering all commands.
        registerCommands();
        // Registering all Listeners.
        registerListeners();
        // Show the admin that the plugin has started ;).
        Logger.log(Logger.LogType.INFO, "ItemsAdderAdminTools Started!");
    }

    @Override
    public void onDisable() {
        // Bye ;)
        Logger.log(Logger.LogType.INFO, "Bye!");
    }

    // Method to register all commands
    private void registerCommands() {
        // Registering the refresh furniture command.
        if (getConfig().getBoolean("EnabledCommands.RefreshFurniture")) {
            registerCommand("refreshfurniture", "refresh your furniture!", List.of(), new RefreshFurniture());
        }
        // Registering the replace furniture command.
        if (getConfig().getBoolean("EnabledCommands.ReplaceFurniture")) {
            registerCommand("replacefurniture", "replace your furniture!", List.of(), new ReplaceFurniture());
        }
        // Registering the info command.
        if (getConfig().getBoolean("EnabledCommands.ExtraInfoCommand")) {
            registerCommand("iaextrainfo", "get more info!", List.of(""), new ExtraInfoCommand());
        }
    }

    // Register All listeners.
    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new ItemsAdderCache(), this);
    }
    // The check if ItemsAdder is enabled.
    private boolean isItemsadderInstalled() {
        return Bukkit.getPluginManager().isPluginEnabled("ItemsAdder");
    }

}
