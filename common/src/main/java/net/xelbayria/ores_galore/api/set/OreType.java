package net.xelbayria.ores_galore.api.set;

import com.google.common.base.Preconditions;
import net.mehvahdjukaar.moonlight.api.platform.PlatHelper;
import net.mehvahdjukaar.moonlight.api.set.BlockType;
import net.mehvahdjukaar.moonlight.api.util.Utils;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.xelbayria.ores_galore.OresGalore;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;


/**
 * Childkey Availability:
 * block,
 * cluster, glinted_cluster, budding
 * shard (ITEM)
**/
@SuppressWarnings("SameParameterValue")
public class OreType extends BlockType {

    public final Block blockOre;

    protected OreType(ResourceLocation id, Block blockOre) {
        super(id);
        this.blockOre = blockOre;
    }

    @Override
    public ItemLike mainChild() {
        return blockOre;
    }

    @Override
    public String getTranslationKey() {
        return "ore_type." + this.getNamespace() + "." + this.getTypeName();
    }

    @Override
    protected void initializeChildrenBlocks() {}

    @Override
    protected void initializeChildrenItems() {}

    protected Block findRelatedBlock(String prefixOrInfix, String suffix) {
        return findRelatedEntry(prefixOrInfix, suffix, BuiltInRegistries.BLOCK);
    }

     protected Item findRelatedItem(String prefixOrInfix, String suffix) {
        return findRelatedEntry(prefixOrInfix, suffix, BuiltInRegistries.ITEM);
    }

    @Override
    protected @Nullable <V> V findRelatedEntry(String prefixOrInfix, String suffix, Registry<V> reg) {
        String prefix_ = (prefixOrInfix.isEmpty()) ? "" : prefixOrInfix + "_";
        String _infix = (prefixOrInfix.isEmpty()) ? "" : "_" + prefixOrInfix;
        String _suffix = (suffix.isEmpty()) ? "" : "_" + suffix;

        ResourceLocation[] targets = {
                // DEFAULT
                new ResourceLocation(id.getNamespace(), id.getPath() + _infix + _suffix),
                new ResourceLocation(id.getNamespace(), prefix_ + id.getPath() + _suffix),
        };
        V found = null;
        for (var r : targets) {
            if (reg.containsKey(r)) {
                found = reg.get(r);
                break;
            }
        }
        return found;
    }

    protected static ResourceLocation[] makeKnownIDConventions(ResourceLocation id, String... affixKeyword) {
        List<ResourceLocation> resources = new ArrayList<>();
        for (String keyword : affixKeyword) {
            String path = id.getPath();
            String namespace = id.getNamespace();

            String suffixed = (keyword.isEmpty()) ? "" : "_" + keyword;
            String prefixed = (keyword.isEmpty()) ? "" : keyword + "_";

            resources.add(new ResourceLocation(namespace, path + suffixed));
            resources.add(new ResourceLocation(namespace, prefixed + path));
        }
        return resources.toArray(new ResourceLocation[0]);
    }

    public static Block findOreBlock(ResourceLocation id) {
        ResourceLocation[] tests = makeKnownIDConventions(id, "ore");
        return Utils.findFirstInRegistry(BuiltInRegistries.BLOCK, tests);
    }

    public static class Finder extends SetFinderBuilder<OreType> {

        private Supplier<Block> blockOreFinder;

        public Finder(ResourceLocation id) {
            super(id, OreTypeRegistry.INSTANCE);
            this.oreBlock(() -> findOreBlock(id));
        }

        public Finder oreBlock(Supplier<Block> oreFinder) {
            this.blockOreFinder = oreFinder;
            return this;
        }

        /// @param id Full Id of OreType as ResourceLocation
        public Finder oreBlock(ResourceLocation id) {
            return this.oreBlock(() -> BuiltInRegistries.BLOCK.getOptional(id)
                    .orElseThrow(() -> new IllegalStateException("Failed to find Ore Block: " + id))
            );
        }

        /// @param nameOreBlock name of Ore Block without modId or namespace
        public Finder oreBlock(String nameOreBlock) {
            return this.oreBlock(Utils.idWithOptionalNamespace(nameOreBlock, id.getNamespace()));
        }

        /**
         * @param prefix include the underscore, "_" if the blockId has one
         * @param suffix include the underscore, "_" if the blockId has one
         */
        public Finder oreBlockAffix(String prefix, String suffix) {
            return oreBlock(prefix + id.getPath() + suffix);
        }

        /**
         * @param suffix include the underscore, "_" if the blockId has one
         */
        public Finder oreBlockSuffix(String suffix) {
            return oreBlock(id.getPath() + suffix);
        }

        @Override
        @ApiStatus.Internal
        public Optional<OreType> get() {
            if (PlatHelper.isModLoaded(id.getNamespace())) {
                try {
                    Block ore = Preconditions.checkNotNull(blockOreFinder.get(), "Manual Finder - failed to find a Ore Block for {}", id);
                    var oreType = new OreType(id, ore);
                    childNames.forEach((key, value) -> {
                        try {
                            ItemLike obj = Preconditions.checkNotNull(value.get());
                            oreType.addChild(key, obj);
                        } catch (Exception e) {
                            OresGalore.LOGGER.warn("Failed to get children for OreType: {} - {}. Ignored! ERROR: {}", id, key, e.getMessage());
                        }
                    });
                    return Optional.of(oreType);
                } catch (Exception e) {
                    OresGalore.LOGGER.warn("Failed to find custom OreType: {} - ", id, e);
                }
            }
            return Optional.empty();
        }
    }

}
