package net.dinglezz.pickupvillagers.datagen;

import net.dinglezz.pickupvillagers.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        // There aren't any blocks
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        // Gets villager item texture
        itemModelGenerator.register(ModItems.VILLAGER, Models.GENERATED);
    }
}
