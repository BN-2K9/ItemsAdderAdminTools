package com.bn_2k9.itemsAdderAdminTools.commands.ExtraInfo;

import com.bn_2k9.itemsAdderAdminTools.framework.Color;
import com.bn_2k9.itemsAdderAdminTools.framework.ItemsAdder.ItemsAdderCache;
import com.bn_2k9.itemsAdderAdminTools.framework.Logger;
import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.regions.Region;
import dev.lone.itemsadder.api.CustomFurniture;
import dev.lone.itemsadder.api.CustomStack;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.datacomponent.DataComponentTypes;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jspecify.annotations.Nullable;

import java.util.Collection;
import java.util.List;

public class ExtraInfoCommand implements BasicCommand {

    @Override
    public void execute(CommandSourceStack commandSourceStack, String[] args) {
        // The custom ItemStack.
        CustomStack customStack = null;
        Player player = (Player) commandSourceStack.getSender();

        if (args.length >= 1) {
            // Check if the second argument is an existing namespaced item in itemsadder.
            if (ItemsAdderCache.getInstance().getNameSpacedItems().contains(args[1])) {
                customStack = CustomStack.getInstance(args[1]);
            }
        } else {
            // Check if there is a item in the players main hand.
            if (player.getInventory().getItemInMainHand().getType() != Material.AIR) {
                player.sendMessage(Color.colorPrefix("<red>You don't have a item in your hand!"));
                return;
            }
            customStack = CustomStack.byItemStack(player.getInventory().getItemInMainHand());
        }

        // Check if the items is a ItemsAdder item.
        if (customStack == null) {
            player.sendMessage(Color.colorPrefix("<red>The item in your hand isn't a ItemsAdder item!"));
            return;
        }

        // Return the desired value.
        switch (args[0].toLowerCase()) {
            case "item_model" -> {
                player.sendMessage(Color.colorPrefix("<green>The Item_Model value of: <gold>" + customStack.getDisplayName() + " <green>is: <gold>" + customStack.getModelPath()));
            }
            case "durability" -> {
                player.sendMessage(Color.colorPrefix("<green>The Durability value of: <gold>" + customStack.getDisplayName() + " <green>is: <gold>" + customStack.getDurability() + " <green>/ <gold>" + customStack.getMaxDurability()));
            }
            case "custommodeldata" -> {
                player.sendMessage(Color.colorPrefix("<green>The CustomModelData value of: <gold>" + customStack.getDisplayName() + " <green>is: <gold>" + customStack.getItemStack().getData(DataComponentTypes.CUSTOM_MODEL_DATA)));
            }
            case "material" -> {
                player.sendMessage(Color.colorPrefix("<green>The Material value of: <gold>" + customStack.getDisplayName() + " <green>is: <gold>" + customStack.getItemStack().getType().toString()));
            }
        }
    }

    @Override
    public Collection<String> suggest(CommandSourceStack commandSourceStack, String[] args) {
        if (args.length == 0) {
            return List.of("Item_Model", "Durability", "CustomModelData", "Material");
        }
        if (args.length == 1) {
            return ItemsAdderCache.getInstance().getNameSpacedItems();
        }
        return BasicCommand.super.suggest(commandSourceStack, args);
    }

    @Override
    public boolean canUse(CommandSender sender) {
        return sender instanceof Player;
    }

    @Override
    public @Nullable String permission() {
        return "ItemsAdderAdminTools.ExtraInfoCommand";
    }
}
