package xyz.meowricles.registry;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import xyz.meowricles.Datamatrix;
import xyz.meowricles.data.DMStorageDataCodec;

public class DMComponentsRegistry {
    public static final DeferredRegister.DataComponents COMPONENTS =
            DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, Datamatrix.MODID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<DMStorageDataCodec>> STORAGE_DATA = COMPONENTS.registerComponentType(
            "storage_data",
            builder -> builder.persistent(DMStorageDataCodec.CODEC)
    );

    public static void register(IEventBus eventBus) {
        COMPONENTS.register(eventBus);
    }
}
