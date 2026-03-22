package xyz.meowricles.item.media;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import xyz.meowricles.entity.projectile.DMCompactDiscProjectile;

public class DMCompactDiscItem extends DMBaseStorageMedia {
    public DMCompactDiscItem(Properties props) {
        super(props);
    }

    @Override
    public DMStorageMediaInterface getMediaInterface(ItemStack stack) {
        return new DMAbstractStorageMedia(stack) {
            @Override
            protected int generateCapacity() {
                return 16 * 1024 * 1024;
            }
        };
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (!level.isClientSide) {
            DMCompactDiscProjectile projectile = new DMCompactDiscProjectile(level, player);
            projectile.setItem(stack);
            projectile.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.5F, 1.0F);
            level.addFreshEntity(projectile);
        }
        player.playSound(SoundEvents.WITCH_THROW, 1.0F, 1.0F);
        
        player.getCooldowns().addCooldown(this, 10);

        if (!player.getAbilities().instabuild) {
            stack.shrink(1);
        }

        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }
}