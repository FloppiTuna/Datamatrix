package xyz.meowricles.registry;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import xyz.meowricles.Datamatrix;
import xyz.meowricles.block.readers.DMUniversalAdapterBlock;

public class DMBlockRegistry {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Datamatrix.MODID);

    public static final DeferredBlock<DMUniversalAdapterBlock> UNIVERSAL_ADAPTER = BLOCKS.registerBlock(
            "universal_adapter",
            DMUniversalAdapterBlock::new,
            BlockBehaviour.Properties.of()
    );

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
