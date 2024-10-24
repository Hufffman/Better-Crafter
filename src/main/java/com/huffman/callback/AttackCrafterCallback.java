package com.huffman.callback;

import com.huffman.item.ConfigStickItem;
import com.huffman.mixin.CrafterBlockEntityAccessor;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.CrafterBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import static com.huffman.constant.ItemInfo.PASTED_TEXT;

public class AttackCrafterCallback implements AttackBlockCallback {

    @Override
    public ActionResult interact(PlayerEntity player, World world, Hand hand, BlockPos pos, Direction direction) {
        if (world.isClient()) {
            return ActionResult.PASS;
        }

        Item item = player.getMainHandStack().getItem();
        if(!(item instanceof ConfigStickItem configStickItem)) {
            return ActionResult.PASS;
        }

        if (!configStickItem.isPasted()) {
            return ActionResult.PASS;
        }

        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof CrafterBlockEntity) {
            CrafterBlockEntityAccessor crafter = (CrafterBlockEntityAccessor) blockEntity;

            configStickItem.pasteInputStacks(crafter);
            configStickItem.pastePropertyDelegate(crafter);

            player.sendMessage(Text.of(PASTED_TEXT), false);

            return ActionResult.SUCCESS;
        }

        return ActionResult.PASS;
    }
}
