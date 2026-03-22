package xyz.meowricles.block.readers;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.meowricles.block.entity.readers.DMUniversalAdapterBlockEntity;

public class DMUniversalAdapterBlock extends Block implements EntityBlock {
    public static final BooleanProperty HAS_MEDIA = BooleanProperty.create("has_media");

    public DMUniversalAdapterBlock(BlockBehaviour.Properties props) {
        super(props);
        this.registerDefaultState(this.stateDefinition.any().setValue(HAS_MEDIA, false));
    }

    @Override
    protected @NotNull ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        // super.useItemOn(stack, state, level, pos, player, hand, hitResult);

        if (level.isClientSide) {
            return ItemInteractionResult.SUCCESS;
        }


        ItemStack heldStack = player.getItemInHand(hand);
        DMUniversalAdapterBlockEntity entity = (DMUniversalAdapterBlockEntity) level.getBlockEntity(pos);

        if (entity != null) {
            if (entity.hasMedia()) {
                // EJECT
                if (heldStack.isEmpty()) {
                    ItemStack media = entity.getMedia();
                    entity.setMedia(ItemStack.EMPTY);

                    if (!player.addItem(media)) {
                        player.drop(media, false);
                    }

                    player.sendSystemMessage(Component.literal("Ejected."));
                    level.setBlock(pos, state.setValue(HAS_MEDIA, false), 3);

                    return ItemInteractionResult.SUCCESS;
                } else {
                    player.sendSystemMessage(Component.literal("Already populated! Empty hand to eject."));
                    return ItemInteractionResult.FAIL;
                }
            } else {
                // INSERT
                if (!heldStack.isEmpty()) {
                    entity.setMedia(heldStack.split(1));
                    level.setBlock(pos, state.setValue(HAS_MEDIA, true), 3);
                    return ItemInteractionResult.CONSUME;
                }
            }
        }
        return ItemInteractionResult.SUCCESS;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new DMUniversalAdapterBlockEntity(pos, state);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(HAS_MEDIA);
    }
}
