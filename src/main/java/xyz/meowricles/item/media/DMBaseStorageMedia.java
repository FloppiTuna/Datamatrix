package xyz.meowricles.item.media;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import xyz.meowricles.item.DMBaseItem;
import xyz.meowricles.utils.FileSizePrettier;

import java.util.List;

public class DMBaseStorageMedia extends DMBaseItem {
    public DMBaseStorageMedia(Item.Properties properties) {
        super(properties);
    }

    public DMStorageMediaInterface getMediaInterface(ItemStack stack) {
        return new DMAbstractStorageMedia(stack) {
        };
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> components, TooltipFlag flag) {
        super.appendHoverText(stack, context, components, flag);

        int used = getMediaInterface(stack).getUsed();
        components.add(Component.literal(String.format("Used: %s / %s", FileSizePrettier.humanReadableByteCountBin(used), FileSizePrettier.humanReadableByteCountBin(getMediaInterface(stack).getCapacity()))).withStyle(ChatFormatting.DARK_AQUA)
                .withStyle(ChatFormatting.DARK_GRAY));
        components.add(Component.literal(getMediaInterface(stack).getId()).withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
    }
}
