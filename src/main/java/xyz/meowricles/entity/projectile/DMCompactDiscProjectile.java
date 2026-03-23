package xyz.meowricles.entity.projectile;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
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
    // Remove the redundant level field

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

        // 95% chance to drop the disc item
        if (random.nextFloat() < 0.95f) {
            spawnAtLocation(getItem());
        }
        this.discard();
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        
        Level level = level();
        if (!level.isClientSide()) {
            // Server-side: use sendParticles to broadcast to all nearby clients
            if (level instanceof net.minecraft.server.level.ServerLevel serverLevel) {
                serverLevel.sendParticles(
                    ParticleTypes.CLOUD,
                    result.getLocation().x(),
                    result.getLocation().y(),
                    result.getLocation().z(),
                    10,      // particle count
                    0.1,     // x spread
                    0.1,     // y spread  
                    0.1,     // z spread
                    0.05     // speed
                );
                
                // 95% chance to drop the disc item
                if (random.nextFloat() < 0.95f) {
                    spawnAtLocation(getItem());
                }
            }
        }
    
        playSound(SoundEvents.GLASS_BREAK, 1.0F, 0.8F);
        this.discard();
    }
}