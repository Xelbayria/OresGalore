package net.xelbayria.forge;

import dev.architectury.platform.forge.EventBuses;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import net.xelbayria.OresGalore;

@Mod(OresGalore.MOD_ID)
public final class OresGaloreForge {
    public OresGaloreForge() {
        // Submit our event bus to let Architectury API register our content on the right time.
        EventBuses.registerModEventBus(OresGalore.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());

        // Run our common setup.
        OresGalore.init();
    }
}
