package com.bn_2k9.itemsAdderAdminTools.framework.ItemsAdder;

import com.bn_2k9.itemsAdderAdminTools.ItemsAdderAdminTools;
import dev.lone.itemsadder.api.CustomBlock;
import dev.lone.itemsadder.api.CustomFurniture;
import dev.lone.itemsadder.api.CustomStack;
import dev.lone.itemsadder.api.Events.ItemsAdderLoadDataEvent;
import lombok.Getter;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.List;

public class ItemsAdderCache implements Listener {

    @Getter
    List<String> nameSpacedItems = new ArrayList<>();

    @Getter
    List<String> nameSpacedFurniture = new ArrayList<>();

    @Getter
    List<String> nameSpacedBlocks = new ArrayList<>();

    @EventHandler
    public void itemsAdderLoadEvent(ItemsAdderLoadDataEvent event) {
        // Cache all the custom items, blocks, furniture.
        nameSpacedBlocks = CustomBlock.getNamespacedIdsInRegistry().stream().toList();
        nameSpacedItems = CustomStack.getNamespacedIdsInRegistry().stream().toList();
        nameSpacedFurniture = CustomFurniture.getNamespacedIdsInRegistry().stream().toList();
    }

    public static ItemsAdderCache getInstance() {
        return ItemsAdderAdminTools.getItemsAdderCache();
    }

}
