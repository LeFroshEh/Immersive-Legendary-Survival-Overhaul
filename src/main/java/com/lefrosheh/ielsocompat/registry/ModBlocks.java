package com.lefrosheh.ielsocompat.registry;

import com.lefrosheh.ielsocompat.IELSOCompat;
import com.lefrosheh.ielsocompat.block.ElectricHeaterBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, IELSOCompat.MODID);

    public static final RegistryObject<Block> ELECTRIC_HEATER = BLOCKS.register("electric_heater",
            () -> new ElectricHeaterBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.METAL)
                    .strength(3.5F)
                    .sound(SoundType.METAL)
                    .requiresCorrectToolForDrops()
                    .lightLevel(state -> state.getValue(ElectricHeaterBlock.LIT) ? 10 : 0)));

    public static final RegistryObject<Block> ELECTRIC_COOLER = BLOCKS.register("electric_cooler",
            () -> new ElectricHeaterBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.METAL)
                    .strength(3.5F)
                    .sound(SoundType.METAL)
                    .requiresCorrectToolForDrops()));
}