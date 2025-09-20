package net.xelbayria.ores_galore;

import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class OresGalore {
    public static final String MOD_ID = "oresgalore";
    public static final Logger LOGGER = LogManager.getLogger("Ores Galore");

    public static void init() {

    }

    public static ResourceLocation res(String name) {
        return new ResourceLocation(MOD_ID, name);
    }
}
