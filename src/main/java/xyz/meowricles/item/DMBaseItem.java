package xyz.meowricles.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import xyz.meowricles.utils.FileSizePrettier;

import java.util.List;

public class DMBaseItem extends Item {
    public DMBaseItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> components, TooltipFlag flag) {
        super.appendHoverText(stack, context, components, flag);

        components.add(Component.translatable(getDescriptionId() + ".lore").withStyle(ChatFormatting.DARK_AQUA));
    }
}
