package net.dinglezz.pickupvillagers;

import net.dinglezz.pickupvillagers.item.ModItems;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.util.ActionResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PickupVillagers implements ModInitializer {
	//Useful variables
	public static final String MOD_ID = "pickupvillagers";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItems.registerModItems();

		// Get villager item when player has shift dawn while right-clicking on villager
		 UseEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
			if (entity instanceof VillagerEntity villagerEntity && Screen.hasShiftDown()) {
				player.giveItemStack(ModItems.VILLAGER.getDefaultStack());
				villagerEntity.remove(Entity.RemovalReason.DISCARDED);
				return ActionResult.SUCCESS;
			}
			return ActionResult.PASS;
		});
	}
}