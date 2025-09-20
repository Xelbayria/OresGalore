package net.xelbayria.ores_galore.modmenu;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.mehvahdjukaar.moonlight.api.platform.configs.fabric.FabricConfigListScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.xelbayria.ores_galore.OresGalore;
import net.xelbayria.ores_galore.configs.OGConfigs;

public class ModMenuCompat implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return p -> new FabricConfigListScreen(OresGalore.MOD_ID, Items.IRON_ORE.getDefaultInstance(),
                Component.literal("§6Ores Galore Configs"), new ResourceLocation("textures/block/iron_ore.png"),
                p, OGConfigs.SPEC);
    }
}