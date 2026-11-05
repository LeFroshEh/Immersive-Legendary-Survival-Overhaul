package com.lefrosheh.ielsocompat.registry;

import com.lefrosheh.ielsocompat.IELSOCompat;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, IELSOCompat.MODID);

    public static final RegistryObject<CreativeModeTab> IMMERSIVE_LSO = TABS.register("immersive_lso",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup." + IELSOCompat.MODID + ".immersive_lso"))
                    .icon(() -> new ItemStack(ModItems.ELECTRIC_HEATER.get()))
                    .displayItems((parameters, output) -> {
                        output.accept(ModItems.ELECTRIC_HEATER.get());
                        output.accept(ModItems.ELECTRIC_COOLER.get());
                        // add future items here
                    })
                    .build());
}