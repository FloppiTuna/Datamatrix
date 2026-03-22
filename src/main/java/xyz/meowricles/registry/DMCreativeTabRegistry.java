package xyz.meowricles.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import xyz.meowricles.Datamatrix;

import java.util.function.Supplier;

public class DMCreativeTabRegistry {
    public static final DeferredRegister<CreativeModeTab> TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Datamatrix.MODID);

    public static final Supplier<CreativeModeTab> MAIN_TAB = TABS.register("main_tab", () ->
            CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup." + Datamatrix.MODID + ".main_tab"))
                    .icon(() -> new ItemStack(DMItemRegistry.UNIVERSAL_ADAPTER_ITEM.get()))
                    .displayItems((params, output) -> {
                        output.accept(DMItemRegistry.UNIVERSAL_ADAPTER_ITEM.get());
                    })
                    .build()
    );

    public static void register(IEventBus eventBus) {
        TABS.register(eventBus);
    }

}
