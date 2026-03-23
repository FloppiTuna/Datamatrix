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
        if (stack == null || stack.isEmpty()) return null; // sanity guard

        DMStorageDataCodec stored = stack.get(DMComponentsRegistry.STORAGE_DATA.get());
        if (stored == null) {
            // No stored data yet -> treat as uninitialized; do not create here.
            return null;
        }

        return cache.computeIfAbsent(stored.id(), id -> createMediaFor(stack));
    }

    public static DMAbstractStorageMedia ensureMedia(ItemStack stack) {
        if (stack == null || stack.isEmpty()) return null;

        DMStorageDataCodec stored = stack.get(DMComponentsRegistry.STORAGE_DATA.get());
        if (stored == null) {
            // First-time use: construct using the item-provided media implementation
            DMAbstractStorageMedia media = createMediaFor(stack);
            if (media != null) {
                cache.putIfAbsent(media.getId(), media);
            }
            return media;
        }
        return cache.computeIfAbsent(stored.id(), id -> createMediaFor(stack));
    }

    private static DMAbstractStorageMedia createMediaFor(ItemStack stack) {
        if (stack.getItem() instanceof DMBaseStorageMedia base) {
            DMStorageMediaInterface iface = base.getMediaInterface(stack);
            return (iface instanceof DMAbstractStorageMedia) ? (DMAbstractStorageMedia) iface : null;
        }
        return null;
    }

    public static void tickAll() {
        for (DMAbstractStorageMedia media : cache.values()) {
            media.tick();
        }
    }
}