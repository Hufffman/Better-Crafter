package com.huffman.item;

import com.huffman.mixin.CrafterBlockEntityAccessor;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.CrafterBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import static com.huffman.constant.ItemInfo.COPIED_TEXT;

public class ConfigStickItem extends Item {

    private DefaultedList<ItemStack> inputStacks;

    private PropertyDelegate propertyDelegate;

    private boolean pasted = false;

    public ConfigStickItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();

        if (world.isClient()) {
            return super.useOnBlock(context);
        }

        BlockPos pos = context.getBlockPos();
        BlockEntity blockEntity = world.getBlockEntity(pos);

		if (blockEntity instanceof CrafterBlockEntity) {
            copyInputStacks((CrafterBlockEntity) blockEntity);
            copyPropertyDelegate((CrafterBlockEntityAccessor) blockEntity);

            this.pasted = true;

            PlayerEntity player = context.getPlayer();
            if (player != null) {
                player.sendMessage(Text.of(COPIED_TEXT), false);
            }
		}

        return super.useOnBlock(context);
    }

    public void pasteInputStacks(CrafterBlockEntityAccessor blockEntity) {
        blockEntity.setInputStacks(this.inputStacks);
    }

    public void pastePropertyDelegate(CrafterBlockEntityAccessor blockEntity) {
        PropertyDelegate delegate = blockEntity.getPropertyDelegate();

        for (int i = 0; i <= 9; i++) {
            delegate.set(i, this.propertyDelegate.get(i));
        }
    }

    public boolean isPasted() {
        return pasted;
    }

    private void copyInputStacks(CrafterBlockEntity blockEntity) {
        DefaultedList<ItemStack> inputStacks = DefaultedList.of();
        DefaultedList<ItemStack> itemStacks = blockEntity.getHeldStacks();

        for (ItemStack itemStack : itemStacks) {
            inputStacks.add(itemStack.copy());
        }

        this.inputStacks = inputStacks;
    }

    private void copyPropertyDelegate(CrafterBlockEntityAccessor blockEntity) {
        PropertyDelegate propertyDelegate = new PropertyDelegate() {
            private final int[] disabledSlots = new int[9];

            private int triggered = 0;

            @Override
            public int get(int index) {
                return index == 9 ? this.triggered : this.disabledSlots[index];
            }

            @Override
            public void set(int index, int value) {
                if (index == 9) {
                    this.triggered = value;
                } else {
                    this.disabledSlots[index] = value;
                }
            }

            @Override
            public int size() {
                return 10;
            }
        };

        PropertyDelegate delegate = blockEntity.getPropertyDelegate();

        for (int i = 0; i <= 9; i++) {
            propertyDelegate.set(i, delegate.get(i));
        }

        this.propertyDelegate = propertyDelegate;
    }
}
