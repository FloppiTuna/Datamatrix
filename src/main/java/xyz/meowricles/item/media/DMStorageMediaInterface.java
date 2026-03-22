package xyz.meowricles.item.media;

import net.minecraft.world.item.ItemStack;

public interface DMStorageMediaInterface {
    // usage statistics
    int getCapacity();
    int getUsed();
    String getId();

    // i/o ops
    byte[] read(int offset, int length);
    void write(int offset, byte[] data);
}