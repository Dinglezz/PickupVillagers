package net.dinglezz.pickupvillagers.item;

import net.dinglezz.pickupvillagers.PickupVillagers;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {

    // Creates villager item
    public static final Item VILLAGER = registerItem("villager", new VillagerItem(EntityType.VILLAGER, new Item.Settings().maxCount(1)));

    // Helper Function
    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(PickupVillagers.MOD_ID, name), item);
    }

    // This function is required, but I don't use it so to not make it empty I make it print something
    public static void registerModItems() {
        PickupVillagers.LOGGER.info("Registering Mod Items for " + PickupVillagers.MOD_ID);
    }
}
