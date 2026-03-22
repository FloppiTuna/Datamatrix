package xyz.meowricles.client;

import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import xyz.meowricles.Datamatrix;
import xyz.meowricles.registry.DMEntityRegistry;

@EventBusSubscriber(modid = Datamatrix.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class DMClientEvents {

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(
                DMEntityRegistry.DISC.get(),
                ThrownItemRenderer::new
        );
    }
}