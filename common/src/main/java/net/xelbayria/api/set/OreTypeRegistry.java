package net.xelbayria.api.set;

import net.mehvahdjukaar.moonlight.api.set.BlockTypeRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

import java.util.Optional;

import static net.xelbayria.gems_realm.misc.HardcodedBlockType.BLACKLISTED_CRYSTALTYPES;
import static net.xelbayria.gems_realm.misc.HardcodedBlockType.BLACKLISTED_MODS;

//@SuppressWarnings("unused")
public class OreTypeRegistry extends BlockTypeRegistry<OreType> {

    public static final OreTypeRegistry INSTANCE = new OreTypeRegistry();

    public OreTypeRegistry() {
        super(OreType.class, "crystal_type");
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
        if (blockPath.matches("\\w+_block")) {
            String crystalName = blockPath.replace("_block", ""); // get gemName from namespace:gemName_block
            ResourceLocation idBlockType = baseRes.withPath(crystalName);

            /// Ensure the detected block is actually CrystalType
            boolean hasShard = BuiltInRegistries.ITEM.containsKey(
                    new ResourceLocation(baseRes.getNamespace(), blockPath.replace("block", "shard"))
            );
            boolean hasCluster = BuiltInRegistries.ITEM.containsKey(
                    new ResourceLocation(baseRes.getNamespace(), blockPath.replace("block", "cluster"))
            );
            boolean noWoodType = !BuiltInRegistries.BLOCK.containsKey(
                    new ResourceLocation(baseRes.getNamespace(), blockPath.replace("block", "log"))
            );
            boolean noMetalType = !BuiltInRegistries.ITEM.containsKey(
                    new ResourceLocation(baseRes.getNamespace(), blockPath.replace("block", "ingot"))
            );
            boolean noGemType = !BuiltInRegistries.ITEM.containsKey(
                    new ResourceLocation(baseRes.getNamespace(), blockPath.replace("_block", ""))
            );

            // Ensure there is no duplicated CrystalType in the list
            if (!valuesReg.containsKey(idBlockType)
                    && (hasCluster || hasShard)
                    && noWoodType
                    && noMetalType
                    && noGemType
                    && !BLACKLISTED_CRYSTALTYPES.contains(idBlockType.toString())
                    && !BLACKLISTED_MODS.contains(baseRes.getNamespace())
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
