package com.lefrosheh.ielsocompat.registry;

import com.lefrosheh.ielsocompat.IELSOCompat;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, IELSOCompat.MODID);

    public static final RegistryObject<Item> ELECTRIC_HEATER = ITEMS.register("electric_heater",
            () -> new BlockItem(ModBlocks.ELECTRIC_HEATER.get(), new Item.Properties()));

    public static final RegistryObject<Item> ELECTRIC_COOLER = ITEMS.register("electric_cooler",
            () -> new BlockItem(ModBlocks.ELECTRIC_COOLER.get(), new Item.Properties()));
}
