package xyz.meowricles.item.media;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class DMUSBStickItem extends DMBaseStorageMedia {
    public DMUSBStickItem(Item.Properties props) {
        super(props);
    }

    @Override
    public DMStorageMediaInterface getMediaInterface(ItemStack stack) {
        return new DMAbstractStorageMedia(stack) {
            @Override
            protected int generateCapacity() {
                return 4 * 1024 * 1024;
            }
        };
    }
}