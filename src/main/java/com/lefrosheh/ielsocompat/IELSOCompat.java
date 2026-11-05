package com.lefrosheh.ielsocompat;

import com.lefrosheh.ielsocompat.registry.ModBlockEntities;
import com.lefrosheh.ielsocompat.registry.ModBlocks;
import com.lefrosheh.ielsocompat.registry.ModCreativeTabs;
import com.lefrosheh.ielsocompat.registry.ModItems;
import com.mojang.logging.LogUtils;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(IELSOCompat.MODID)
public class IELSOCompat {
    public static final String MODID = "ie_lso_compat";
    public static final Logger LOGGER = LogUtils.getLogger();

    public IELSOCompat() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModBlocks.BLOCKS.register(modBus);
        ModItems.ITEMS.register(modBus);
        ModBlockEntities.BLOCK_ENTITIES.register(modBus);
        ModCreativeTabs.TABS.register(modBus);
        LOGGER.info("IE x LSO compat loaded");
    }
}
