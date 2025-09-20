package net.xelbayria.ores_galore.misc;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.mehvahdjukaar.moonlight.api.client.ICustomItemRendererProvider;
import net.mehvahdjukaar.moonlight.api.client.ItemStackRenderer;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

public class AllOresItem extends Item implements ICustomItemRendererProvider {

    public AllOresItem() {
        super(new Properties());
    }

    @Override
    @Environment(EnvType.CLIENT)
    public Supplier<ItemStackRenderer> getRendererFactory() {
        return OreTypeCycleItemRenderer::new;
    }
}
