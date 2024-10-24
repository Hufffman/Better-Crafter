package com.huffman.mixin;

import net.minecraft.block.entity.CrafterBlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.util.collection.DefaultedList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(CrafterBlockEntity.class)
public interface CrafterBlockEntityAccessor {
    @Accessor
    PropertyDelegate getPropertyDelegate();

    @Accessor
    void setInputStacks(DefaultedList<ItemStack> inputStacks);
}