package xyz.meowricles.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import xyz.meowricles.item.media.DMStorageMediaInterface;
import xyz.meowricles.item.media.DMUSBStickItem;

public class DMTestCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("dmtest")
                .requires(source -> source.hasPermission(0))
                .executes(ctx -> {
                    Player player = ctx.getSource().getPlayerOrException();
                    ItemStack stack = player.getMainHandItem();

                    if (!(stack.getItem() instanceof DMUSBStickItem usb)) {
                        ctx.getSource().sendFailure(Component.literal("Hold a USB stick."));
                        return 0;
                    }

                    DMStorageMediaInterface media = usb.getMediaInterface(stack);

                    // WRITE test
                    byte[] testData = "hello".getBytes();
                    media.write(0, testData, stack);

                    // READ test
                    byte[] read = media.read(0, 5);

                    ctx.getSource().sendSuccess(() ->
                            Component.literal("Read back: " + new String(read)), false);

                    ctx.getSource().sendSuccess(() ->
                            Component.literal("Used: " + media.getUsed() + "/" + media.getCapacity()), false);

                    return 1;
                })
        );
    }
}