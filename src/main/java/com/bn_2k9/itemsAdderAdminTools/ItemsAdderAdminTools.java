package com.bn_2k9.itemsAdderAdminTools;

import com.bn_2k9.itemsAdderAdminTools.commands.RefreshFurniture;
import com.bn_2k9.itemsAdderAdminTools.commands.ReplaceFurniture;
import com.bn_2k9.itemsAdderAdminTools.framework.Logger;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public final class ItemsAdderAdminTools extends JavaPlugin {

    private static ItemsAdderAdminTools instance;

    @Override
    public void onEnable() {
        // Registering main instance.
        instance = this;
        // Saving the config.
        saveDefaultConfig();
        // Checking if itemsAdder is installed.
        if (!isItemsadderInstalled()) {
            Logger.log(Logger.LogType.ERROR, "ItemsAdder isn't installed. Please install Itemsadder!");
            Bukkit.getPluginManager().disablePlugin(instance);
        }
        // Registering all commands.
        registercommands();
        // Show the admin that the plugin has started ;).
        Logger.log(Logger.LogType.INFO, "ItemsAdderAdminTools Started!");
    }

    @Override
    public void onDisable() {
        // Bye ;)
        Logger.log(Logger.LogType.INFO, "Bye!");
    }

    // Method to register all commands
    private void registercommands() {
        // Registering the refresh furniture command.
        if (getConfig().getBoolean("EnabledCommands.RefreshFurniture")) {
            registerCommand("refreshfurniture", "refresh your furniture!", List.of(), new RefreshFurniture());
        }
        // Registering the replace furniture command.
        if (getConfig().getBoolean("EnabledCommands.ReplaceFurniture")) {
            registerCommand("replacefurniture", "replace your furniture!", List.of(), new ReplaceFurniture());
        }
    }

    // The check if ItemsAdder is enabled.
    private boolean isItemsadderInstalled() {
        return Bukkit.getPluginManager().isPluginEnabled("ItemsAdder");
    }

    // Static method for getting the instance.
    public static ItemsAdderAdminTools getInstance() {
        return instance;
    }
}
