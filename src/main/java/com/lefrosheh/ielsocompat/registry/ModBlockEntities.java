package com.lefrosheh.ielsocompat.registry;

import com.lefrosheh.ielsocompat.IELSOCompat;
import com.lefrosheh.ielsocompat.blockentity.ElectricHeaterBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, IELSOCompat.MODID);

    public static final RegistryObject<BlockEntityType<ElectricHeaterBlockEntity>> ELECTRIC_HEATER =
            BLOCK_ENTITIES.register("electric_heater",
                    () -> BlockEntityType.Builder.of(ElectricHeaterBlockEntity::new,
                            ModBlocks.ELECTRIC_HEATER.get(),
                            ModBlocks.ELECTRIC_COOLER.get()).build(null));
}
