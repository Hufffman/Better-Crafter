package com.huffman.registry;

import com.huffman.item.ConfigStickItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import static com.huffman.constant.ItemInfo.CONFIG_STICK;
import static com.huffman.constant.ModInfo.MOD_ID;

public final class ItemRegistry {

    private ItemRegistry() {}
 
    public static void initialize() {
        Registry.register(Registries.ITEM, Identifier.of(MOD_ID, CONFIG_STICK), new ConfigStickItem(new Item.Settings().maxCount(1)));
    }
}