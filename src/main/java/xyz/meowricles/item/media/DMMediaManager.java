package xyz.meowricles.item.media;

import net.minecraft.world.item.ItemStack;
import net.neoforged.fml.common.EventBusSubscriber;
import xyz.meowricles.data.DMStorageDataCodec;
import xyz.meowricles.registry.DMComponentsRegistry;

import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

public class DMMediaManager {
    private static final Map<String, DMAbstractStorageMedia> cache = new HashMap<>();

    public static DMAbstractStorageMedia getMedia(ItemStack stack) {
        DMStorageDataCodec stored = stack.get(DMComponentsRegistry.STORAGE_DATA.get());
        if (stored == null) return null;

        return cache.computeIfAbsent(stored.id(), id -> new DMAbstractStorageMedia(stack) {});
    }

    public static void tickAll() {
        for (DMAbstractStorageMedia media : cache.values()) {
            media.tick();
        }
    }
}