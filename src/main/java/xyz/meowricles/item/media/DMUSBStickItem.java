package xyz.meowricles.item.media;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import xyz.meowricles.utils.FileSizePrettier;

public class DMUSBStickItem extends DMBaseStorageMedia {
    public final int MEGS;

    public DMUSBStickItem(Item.Properties props, int megs) {
        super(props);
        MEGS = megs;
    }

    @Override
    public DMStorageMediaInterface getMediaInterface(ItemStack stack) {
        return new DMAbstractStorageMedia(stack) {
            @Override
            protected int generateCapacity() {
                return MEGS * 1024 * 1024;
            }
        };
    }


    @Override
    public Component getName(ItemStack stack) {
        return Component.translatable("item.datamatrix.usb_drive", FileSizePrettier.humanReadableByteCountBin((long) MEGS * 1024 * 1024));
    }
}