package com.huffman.registry;

import com.huffman.callback.AttackCrafterCallback;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;

public class AttackCrafterRegistry {
    private AttackCrafterRegistry() {}

    public static void initialize() {
        AttackBlockCallback.EVENT.register(new AttackCrafterCallback());
    }
}
