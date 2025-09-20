package net.xelbayria.ores_galore;

import net.mehvahdjukaar.moonlight.api.misc.RegSupplier;
import net.mehvahdjukaar.moonlight.api.platform.RegHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.xelbayria.ores_galore.configs.OGConfigs;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

import static net.xelbayria.ores_galore.configs.OGConfigs.TAB_ITEM_SEARCH_ENABLED;

public class OresGaloreRegistry {

    public static void init() {

    }

    public static final Supplier<AllGemsItem> ALL_GEMS = RegHelper.registerItem(OresGalore.res("all_gems"), AllGemsItem::new);

    @Nullable
    public static final RegSupplier<CreativeModeTab> MOD_TAB = OGConfigs.TAB_ENABLED.get() ?
            RegHelper.registerCreativeModeTab(OresGalore.res("gems_realm"),
                    TAB_ITEM_SEARCH_ENABLED.get(), // searchBar
                    builder -> builder.icon(() -> ALL_GEMS.get().getDefaultInstance())
                            .backgroundSuffix((TAB_ITEM_SEARCH_ENABLED.get()) ? "item_search.png" : "items.png")
                            .title(Component.translatable("itemGroup.gemsrealm.gems_realm"))
                            .build())
            : null;
}
