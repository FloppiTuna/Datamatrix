package xyz.meowricles.registry;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import xyz.meowricles.Datamatrix;
import xyz.meowricles.item.media.DMCompactDiscItem;
import xyz.meowricles.item.media.DMShoddyMemoryItem;
import xyz.meowricles.item.media.DMUSBStickItem;

public class DMItemRegistry {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Datamatrix.MODID);

    public static final DeferredItem<Item> COMPACT_DISC = ITEMS.registerItem(
            "compact_disc",
            DMCompactDiscItem::new,
            new Item.Properties().stacksTo(1)
    );

    public static final DeferredItem<Item> USB_4M = ITEMS.registerItem(
            "usb_4m",
            props -> new DMUSBStickItem(props, 4),
            new Item.Properties().stacksTo(1)
    );

    public static final DeferredItem<Item> USB_16M = ITEMS.registerItem(
            "usb_16m",
            props -> new DMUSBStickItem(props, 16),
            new Item.Properties().stacksTo(1)
    );

    public static final DeferredItem<Item> USB_32M = ITEMS.registerItem(
            "usb_32m",
            props -> new DMUSBStickItem(props, 32),
            new Item.Properties().stacksTo(1)
    );

    public static final DeferredItem<Item> SHODDY_MEMORY = ITEMS.registerItem(
            "shoddy_memory",
            DMShoddyMemoryItem::new,
            new Item.Properties().stacksTo(1)
    );

    public static final DeferredItem<BlockItem> UNIVERSAL_ADAPTER_ITEM = ITEMS.registerSimpleBlockItem(
            "universal_adapter",
            DMBlockRegistry.UNIVERSAL_ADAPTER
    );


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
