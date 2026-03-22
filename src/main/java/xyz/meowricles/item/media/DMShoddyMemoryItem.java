package xyz.meowricles.item.media;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class DMShoddyMemoryItem extends DMBaseStorageMedia {
    public DMShoddyMemoryItem(Item.Properties props) {
        super(props);
    }

    @Override
    public void onCraftedBy(ItemStack stack, Level level, Player player) {
        super.onCraftedBy(stack, level, player);
    }

    @Override
    public DMStorageMediaInterface getMediaInterface(ItemStack stack) {
        return new DMAbstractStorageMedia(stack) {
            @Override
            protected int generateCapacity() {
                return (int)(Math.random() * 64 + 4) * 1024;
            }
        };
    }
}
