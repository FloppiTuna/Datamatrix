package xyz.meowricles.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import xyz.meowricles.Datamatrix;
import xyz.meowricles.entity.projectile.DMCompactDiscProjectile;

import java.util.function.Supplier;

public class DMEntityRegistry {
    public static final DeferredRegister<EntityType<?>> ENTITIES =
            DeferredRegister.create(Registries.ENTITY_TYPE, Datamatrix.MODID);

    public static final Supplier<EntityType<DMCompactDiscProjectile>> DISC =
            ENTITIES.register("disc", () ->
                    EntityType.Builder.<DMCompactDiscProjectile>of(DMCompactDiscProjectile::new, MobCategory.MISC)
                            .sized(0.25F, 0.25F)
                            .clientTrackingRange(4)
                            .updateInterval(10)
                            .build("disc"));

    public static void register(IEventBus eventBus) {
        ENTITIES.register(eventBus);
    }
}
