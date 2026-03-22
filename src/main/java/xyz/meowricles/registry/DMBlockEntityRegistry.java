package xyz.meowricles.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import xyz.meowricles.Datamatrix;
import xyz.meowricles.block.entity.readers.DMUniversalAdapterBlockEntity;

import java.util.function.Supplier;

public class DMBlockEntityRegistry {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES =
            DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, Datamatrix.MODID);

    public static final Supplier<BlockEntityType<DMUniversalAdapterBlockEntity>> UNIVERSAL_ADAPTER_ENTITY = BLOCK_ENTITY_TYPES.register(
            "universal_adapter_entity",
            // The block entity type, created using a builder.
            () -> BlockEntityType.Builder.of(
                            // The supplier to use for constructing the block entity instances.
                            DMUniversalAdapterBlockEntity::new,
                            // A vararg of blocks that can have this block entity.
                            // This assumes the existence of the referenced blocks as DeferredBlock<Block>s.
                            DMBlockRegistry.UNIVERSAL_ADAPTER.get()
                    )
                    // Build using null; vanilla does some datafixer shenanigans with the parameter that we don't need.
                    .build(null)
    );

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITY_TYPES.register(eventBus);
    }
}
