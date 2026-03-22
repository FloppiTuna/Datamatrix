package xyz.meowricles.block.entity.readers;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import xyz.meowricles.registry.DMBlockEntityRegistry;

public class DMUniversalAdapterBlockEntity extends BlockEntity {
    private ItemStack media = ItemStack.EMPTY;

    public DMUniversalAdapterBlockEntity(BlockPos pos, BlockState state) {
        super(DMBlockEntityRegistry.UNIVERSAL_ADAPTER_ENTITY.get(), pos, state);
    }

    public ItemStack getMedia() {
        return media;
    }

    public void setMedia(ItemStack media) {
        this.media = media;
        setChanged();
    }

    public boolean hasMedia() {
        return media != null && !media.isEmpty();
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        super.saveAdditional(tag, provider);
        if (media != ItemStack.EMPTY) {
            tag.put("Media", media.save(provider));
        }
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        super.loadAdditional(tag, provider);
        if (tag.contains("Media")) {
            media = ItemStack.parse(provider, tag.getCompound("Media")).orElse(null);
        }
    }
}
