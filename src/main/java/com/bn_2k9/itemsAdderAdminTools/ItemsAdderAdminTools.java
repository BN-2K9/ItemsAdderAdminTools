package com.bn_2k9.itemsAdderAdminTools;

import com.bn_2k9.itemsAdderAdminTools.commands.ExtraInfo.ExtraInfoCommand;
import com.bn_2k9.itemsAdderAdminTools.commands.Furniture.RefreshFurniture;
import com.bn_2k9.itemsAdderAdminTools.commands.Furniture.ReplaceFurniture;
import com.bn_2k9.itemsAdderAdminTools.commands.RemoveResourcePack.RemoveResourcePackCommand;
import com.bn_2k9.itemsAdderAdminTools.commands.ScanSelection.ScanSelectionCommand;
import com.bn_2k9.itemsAdderAdminTools.framework.ItemsAdder.ItemsAdderCache;
import com.bn_2k9.itemsAdderAdminTools.framework.Logger;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class ItemsAdderAdminTools extends JavaPlugin {

    // Static method for getting the instance.
    @Getter
    private static ItemsAdderAdminTools instance;

    @Getter
    private static ItemsAdderCache itemsAdderCache;

    @Override
    public void onLoad() {
        // Registering main instance.
        instance = this;
        // Saving the config.
        saveDefaultConfig();
    }

    @Override
    public void onEnable() {
        // Registering the itemsAdderCache.
        itemsAdderCache = new ItemsAdderCache();
        // Checking if itemsAdder is installed.
        if (!isItemsAdderInstalled()) {
            Logger.log(Logger.LogType.ERROR, "ItemsAdder isn't installed. Please install ItemsAdder!");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        if (!isWorldEditInstalled()) {
            Logger.log(Logger.LogType.ERROR, "WorldEdit or Fawe, isn't installed. Please install WorldEdit or Fawe.");
            Bukkit.getPluginManager().disablePlugin(this);
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
        if (getConfig().getBoolean("EnabledCommands.RefreshFurniture", false)) {
            registerCommand("refreshfurniture", "refresh your furniture!", new RefreshFurniture());
        }
        // Registering the replace furniture command.
        if (getConfig().getBoolean("EnabledCommands.ReplaceFurniture", false)) {
            registerCommand("replacefurniture", "replace your furniture!", new ReplaceFurniture());
        }
        // Registering the info command.
        if (getConfig().getBoolean("EnabledCommands.ExtraInfoCommand", false)) {
            registerCommand("iaextrainfo", "get more info!",  new ExtraInfoCommand());
        }
        if (getConfig().getBoolean("EnabledCommands.RemoveResourcePackCommand", false)) {
            registerCommand("removeresourcepack", "remove all resourcepacks.", new RemoveResourcePackCommand());
        }
        if (getConfig().getBoolean("EnabledCommands.ScanSelectionCommand", false)) {
            registerCommand("scanselection", "scan a selection for items.", new ScanSelectionCommand());
        }
    }

    // Register All listeners.
    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new ItemsAdderCache(), this);
    }
    // The check if ItemsAdder is enabled.
    private boolean isItemsAdderInstalled() {
        return Bukkit.getPluginManager().isPluginEnabled("ItemsAdder");
    }
    // Check if world edit is enabled.
    private boolean isWorldEditInstalled() {return Bukkit.getPluginManager().isPluginEnabled("WorldEdit") || Bukkit.getPluginManager().isPluginEnabled("FastAsyncWorldEdit");}
}
