package net.dinglezz.pickupvillagers.item;

import com.mojang.serialization.MapCodec;
import net.dinglezz.pickupvillagers.PickupVillagers;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.Spawner;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.TropicalFishEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.Registries;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.village.VillagerData;
import net.minecraft.village.VillagerType;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class VillagerItem extends Item {
    private static final MapCodec<EntityType<?>> ENTITY_TYPE_MAP_CODEC = Registries.ENTITY_TYPE.getCodec().fieldOf("id");
    private static final MapCodec<VillagerData> VILLAGER_DATA_MAP_CODEC = VillagerData.CODEC.fieldOf("VillagerType");
    private final EntityType<?> type;

    public VillagerItem(EntityType<? extends MobEntity > type, Item.Settings settings) {
        super(settings);
        this.type = type;
    }

    //Spawns Villager when used
    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        if (!(world instanceof ServerWorld)) {
            return ActionResult.SUCCESS;
        } else {
            ItemStack itemStack = context.getStack();
            BlockPos blockPos = context.getBlockPos();
            Direction direction = context.getSide();
            BlockState blockState = world.getBlockState(blockPos);
            if (world.getBlockEntity(blockPos) instanceof Spawner spawner) {
                EntityType<?> entityType = this.getEntityType(itemStack); // Change Later
                spawner.setEntityType(entityType, world.getRandom());
                world.updateListeners(blockPos, blockState, blockState, Block.NOTIFY_ALL);
                world.emitGameEvent(context.getPlayer(), GameEvent.BLOCK_CHANGE, blockPos);
                itemStack.decrement(1);
                return ActionResult.CONSUME;
            } else {
                BlockPos blockPos2;
                if (blockState.getCollisionShape(world, blockPos).isEmpty()) {
                    blockPos2 = blockPos;
                } else {
                    blockPos2 = blockPos.offset(direction);
                }

                EntityType<?> entityType = this.getEntityType(itemStack); // Change later
                if (entityType.spawnFromItemStack(
                        (ServerWorld) world,
                        itemStack,
                        context.getPlayer(),
                        blockPos2,
                        SpawnReason.SPAWN_EGG,
                        true,
                        !Objects.equals(blockPos, blockPos2) && direction == Direction.UP
                )
                        != null) {
                    itemStack.decrement(1);
                    world.emitGameEvent(context.getPlayer(), GameEvent.ENTITY_PLACE, blockPos);
                }

                return ActionResult.CONSUME;
            }
        }
    }

    public EntityType<?> getEntityType(ItemStack stack) {
       NbtComponent nbtComponent = stack.getOrDefault(DataComponentTypes.ENTITY_DATA, NbtComponent.DEFAULT);
       return !nbtComponent.isEmpty() ? (EntityType)nbtComponent.get(ENTITY_TYPE_MAP_CODEC).result().orElse(this.type) : this.type;
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        //Credits Tooltip
        tooltip.add(Text.translatable("tooltip_pickupvillagers_villager.tooltip"));
        super.appendTooltip(stack, context, tooltip, type);

        //Data manager
        NbtComponent nbtComponent = stack.getOrDefault(DataComponentTypes.ENTITY_DATA, NbtComponent.DEFAULT);
        if (nbtComponent.isEmpty()) {
            PickupVillagers.LOGGER.info("RETURNNNNNNNNNN");
            return;
        }

        Optional<VillagerData> optional = nbtComponent.get(VILLAGER_DATA_MAP_CODEC).result();

        if (optional.isPresent()) {
            System.out.println("AHHHHHHHH");
        }
    }
}
