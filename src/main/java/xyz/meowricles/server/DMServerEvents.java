package xyz.meowricles.server;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import xyz.meowricles.item.media.DMMediaManager;

@EventBusSubscriber
public class DMServerEvents {

    @SubscribeEvent
    public static void onServerTick(ServerTickEvent.Post event) {
        DMMediaManager.tickAll();
    }
}
