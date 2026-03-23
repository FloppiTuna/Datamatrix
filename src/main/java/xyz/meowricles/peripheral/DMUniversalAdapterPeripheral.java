package xyz.meowricles.peripheral;

import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.peripheral.GenericPeripheral;
import dan200.computercraft.api.peripheral.PeripheralType;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import xyz.meowricles.Datamatrix;
import xyz.meowricles.block.entity.readers.DMUniversalAdapterBlockEntity;
import xyz.meowricles.item.media.DMAbstractStorageMedia;
import xyz.meowricles.item.media.DMMediaManager;
import xyz.meowricles.item.media.DMStorageMediaInterface;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class DMUniversalAdapterPeripheral implements GenericPeripheral {
    @Override
    public @NotNull String id() {
        return ResourceLocation.fromNamespaceAndPath(Datamatrix.MODID, "universal_adapter").toString();
    }

    @Override
    public @NotNull PeripheralType getType() {
        return PeripheralType.ofType("universal_adapter");
    }

    @LuaFunction(mainThread = true)
    public boolean hasMedia(DMUniversalAdapterBlockEntity adapter) {
        return adapter.hasMedia();
    }

    @LuaFunction(mainThread = true)
    public void initialize(DMUniversalAdapterBlockEntity adapter) throws LuaException {
        if (!adapter.hasMedia()) {
            throw new LuaException("No media present.");
        }
        DMAbstractStorageMedia media = DMMediaManager.ensureMedia(adapter.getMedia());
        if (media == null) {
            throw new LuaException("Invalid or unsupported media.");
        }
        media.initialize();
    }

    @LuaFunction(mainThread = true)
    public byte[] read(DMUniversalAdapterBlockEntity adapter, int offset, int length) throws LuaException {
        if (!adapter.hasMedia()) {
            throw new LuaException("No media present.");
        }
        DMAbstractStorageMedia media = DMMediaManager.getMedia(adapter.getMedia());
        if (media == null) {
            throw new LuaException("Invalid or uninitialized media.");
        }
        return media.read(offset, length);
    }

    @LuaFunction(mainThread = true)
    public void write(DMUniversalAdapterBlockEntity adapter, int offset, String data) throws LuaException {
        if (!adapter.hasMedia()) {
            throw new LuaException("No media present.");
        }
        DMAbstractStorageMedia media = DMMediaManager.getMedia(adapter.getMedia());
        if (media == null) {
            throw new LuaException("Invalid or uninitialized media.");
        }
        media.write(offset, data.getBytes(StandardCharsets.ISO_8859_1), adapter.getMedia());
    }
}
