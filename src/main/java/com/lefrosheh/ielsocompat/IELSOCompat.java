package com.lefrosheh.ielsocompat;

import com.mojang.logging.LogUtils;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;

@Mod(IELSOCompat.MODID)
public class IELSOCompat {
    public static final String MODID = "ie_lso_compat";
    public static final Logger LOGGER = LogUtils.getLogger();

    public IELSOCompat() {
        LOGGER.info("IE x LSO compat loaded");
    }
}
