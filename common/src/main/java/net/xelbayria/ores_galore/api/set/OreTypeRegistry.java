package net.xelbayria.ores_galore.api.set;

import net.mehvahdjukaar.moonlight.api.set.BlockTypeRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

import java.util.Optional;

import static net.xelbayria.ores_galore.api.intergration.HardcodedOreType.BLACKLISTED_ORETYPES;

@SuppressWarnings("unused")
public class OreTypeRegistry extends BlockTypeRegistry<OreType> {

    public static final OreTypeRegistry INSTANCE = new OreTypeRegistry();

    public OreTypeRegistry() {
        super(OreType.class, "ore_type");
    }

    @Override
    public OreType register(OreType vanillaType) {
        return super.register(vanillaType);
    }

    @Override
    public OreType getDefaultType() {
        return VanillaOreTypes.AMETHYST;
    }

    @Override
    public Optional<OreType> detectTypeFromBlock(Block baseBlock, ResourceLocation baseRes) {
        String blockPath = baseRes.getPath();

        /// Default
        if (blockPath.matches("\\w+_ore")) {
            String oreName = blockPath.replace("_ore", ""); // get oreName from namespace:oreName_ore
            ResourceLocation idBlockType = baseRes.withPath(oreName);

            // Ensure there is no duplicated CrystalType in the list
            if (!valuesReg.containsKey(idBlockType)
                    && !BLACKLISTED_ORETYPES.contains(idBlockType.toString())
            ) {
                Optional<Block> opt = BuiltInRegistries.BLOCK.getOptional(baseRes);

                if (opt.isPresent()) return Optional.of(new OreType(idBlockType, opt.get()));
            }

        }
        return Optional.empty();
    }

    //shorthand for add finder. Gives a builder-like object that's meant to be configured inline
    public OreType.Finder addSimpleFinder(ResourceLocation crystalTypeId) {
        OreType.Finder finder = new OreType.Finder(crystalTypeId);
        this.addFinder(finder);
        return finder;
    }

    public OreType.Finder addSimpleFinder(String typeId) {
        return addSimpleFinder(new ResourceLocation(typeId));
    }

    public OreType.Finder addSimpleFinder(String namespace, String nameCrystalType) {
        return addSimpleFinder(new ResourceLocation(namespace, nameCrystalType));
    }

    @Override
    public int priority() {
        return 110;
    }
}
