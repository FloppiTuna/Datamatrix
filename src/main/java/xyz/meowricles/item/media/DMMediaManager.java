package xyz.meowricles.item.media;

import net.minecraft.world.item.ItemStack;

import java.util.WeakHashMap;

public class DMMediaManager {
    private static final WeakHashMap<ItemStack, DMAbstractStorageMedia> cache = new WeakHashMap<>();

    public static DMAbstractStorageMedia getMedia(ItemStack stack) {
        return cache.computeIfAbsent(stack, s -> new DMAbstractStorageMedia(s) {
        });
    }
}