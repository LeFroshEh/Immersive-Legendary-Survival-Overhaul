
package com.lefrosheh.ielsocompat;

import blusunrize.immersiveengineering.common.blocks.multiblocks.logic.arcfurnace.ArcFurnaceLogic;
import blusunrize.immersiveengineering.api.multiblocks.blocks.registry.MultiblockBlockEntityMaster;
import blusunrize.immersiveengineering.common.blocks.metal.BlastFurnacePreheaterBlockEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec3;

import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import sfiomn.legendarysurvivaloverhaul.api.temperature.TemperatureUtil;

import java.util.UUID;

@Mod.EventBusSubscriber(
        modid = IELSOCompat.MODID,
        bus = Mod.EventBusSubscriber.Bus.FORGE
)
public class ArcFurnaceHeatHandler {

    private static final UUID ARC_FURNACE_HEAT_ID =
            UUID.fromString("8a72a520-51a4-4d76-9b3b-4e5e5a8a1f31");

    private static final double HEAT_STRENGTH = 8.0;

    private static final int HEAT_RADIUS = 8;
    private static final double HEAT_RADIUS_SQUARED =
            HEAT_RADIUS * HEAT_RADIUS;

    private static final int CHECK_INTERVAL = 20;

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) {
            return;
        }

        Player player = event.player;
        Level level = player.level();

        if (level.isClientSide) {
            return;
        }

        if (player.tickCount % CHECK_INTERVAL != 0) {
            return;
        }

        boolean shouldHeat =
                isNearActiveArcFurnace(player)
                        || isNearChargedPreheater(player);

        TemperatureUtil.addTemperatureModifier(
                player,
                shouldHeat ? HEAT_STRENGTH : 0.0,
                ARC_FURNACE_HEAT_ID
        );
    }

    private static boolean isNearActiveArcFurnace(Player player) {
        Level level = player.level();
        BlockPos origin = player.blockPosition();

        BlockPos.MutableBlockPos cursor =
                new BlockPos.MutableBlockPos();

        for (int x = -HEAT_RADIUS; x <= HEAT_RADIUS; x++) {
            for (int y = -HEAT_RADIUS; y <= HEAT_RADIUS; y++) {
                for (int z = -HEAT_RADIUS; z <= HEAT_RADIUS; z++) {

                    cursor.set(
                            origin.getX() + x,
                            origin.getY() + y,
                            origin.getZ() + z
                    );

                    if (!level.hasChunkAt(cursor)) {
                        continue;
                    }

                    BlockEntity blockEntity =
                            level.getBlockEntity(cursor);

                    if (!(blockEntity instanceof
                            MultiblockBlockEntityMaster<?> master)) {
                        continue;
                    }

                    Object state = master.getHelper().getState();

                    if (!(state instanceof ArcFurnaceLogic.State arcState)) {
                        continue;
                    }

                    if (!arcState.isClientActive()) {
                        continue;
                    }

                    Vec3 furnacePosition =
                            Vec3.atCenterOf(blockEntity.getBlockPos());

                    if (player.distanceToSqr(furnacePosition)
                            <= HEAT_RADIUS_SQUARED) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    private static boolean isNearChargedPreheater(Player player) {
        Level level = player.level();
        BlockPos origin = player.blockPosition();

        BlockPos.MutableBlockPos cursor =
                new BlockPos.MutableBlockPos();

        for (int x = -HEAT_RADIUS; x <= HEAT_RADIUS; x++) {
            for (int y = -HEAT_RADIUS; y <= HEAT_RADIUS; y++) {
                for (int z = -HEAT_RADIUS; z <= HEAT_RADIUS; z++) {

                    cursor.set(
                            origin.getX() + x,
                            origin.getY() + y,
                            origin.getZ() + z
                    );

                    if (!level.hasChunkAt(cursor)) {
                        continue;
                    }

                    BlockEntity blockEntity =
                            level.getBlockEntity(cursor);

                    if (!(blockEntity instanceof
                            BlastFurnacePreheaterBlockEntity preheater)) {
                        continue;
                    }

                    BlastFurnacePreheaterBlockEntity master =
                            preheater.master();

                    if (master == null) {
                        continue;
                    }

                    if (master.energyStorage.getEnergyStored() <= 0) {
                        continue;
                    }

                    Vec3 heaterPosition =
                            Vec3.atCenterOf(master.getBlockPos());

                    if (player.distanceToSqr(heaterPosition)
                            <= HEAT_RADIUS_SQUARED) {
                        return true;
                    }
                }
            }
        }

        return false;
    }
}
