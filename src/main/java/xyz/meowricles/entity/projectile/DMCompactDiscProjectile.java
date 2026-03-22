package xyz.meowricles.entity.projectile;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import xyz.meowricles.registry.DMEntityRegistry;
import xyz.meowricles.registry.DMItemRegistry;

public class DMCompactDiscProjectile extends ThrowableItemProjectile {

    public DMCompactDiscProjectile(EntityType<? extends DMCompactDiscProjectile> type, Level level) {
        super(type, level);
    }

    public DMCompactDiscProjectile(Level level, LivingEntity shooter) {
        super(DMEntityRegistry.DISC.get(), shooter, level);
    }

    @Override
    protected Item getDefaultItem() {
        return DMItemRegistry.COMPACT_DISC.get();
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        result.getEntity().hurt(damageSources().thrown(this, this.getOwner()), 4.0F);
        this.discard();
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        this.discard();
    }
}