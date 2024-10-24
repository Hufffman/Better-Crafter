package com.huffman;

import com.huffman.registry.AttackCrafterRegistry;
import com.huffman.registry.ItemRegistry;
import net.fabricmc.api.ModInitializer;

public class BetterCrafter implements ModInitializer {
	@Override
	public void onInitialize() {
		ItemRegistry.initialize();

		AttackCrafterRegistry.initialize();
	}
}