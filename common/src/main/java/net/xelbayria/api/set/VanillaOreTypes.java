package net.xelbayria.api.set;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;

public class VanillaOreTypes {

    public static final OreType AMETHYST = OreTypeRegistry.INSTANCE.register(
            new OreType(new ResourceLocation("amethyst"), Blocks.AMETHYST_BLOCK)
    );
}
