package xyz.meowricles.item.media;

import net.minecraft.world.item.ItemStack;
import xyz.meowricles.registry.DMComponentsRegistry;
import xyz.meowricles.data.DMStorageDataCodec;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.UUID;

public abstract class DMAbstractStorageMedia implements DMStorageMediaInterface {
    protected String id;
    protected byte[] data;
    protected int used;
    protected int capacity;

    private final ItemStack stack;
    private final Path savePath;

    private boolean dirty = false;

    public DMAbstractStorageMedia(ItemStack stack) {
        this.stack = stack;
        loadData(); // only id, used, capacity
        this.savePath = Paths.get("datamatrix/storage/");

        try {
            Files.createDirectories(savePath);
        } catch (IOException e) {
            throw new RuntimeException("Failed to create storage folder!!!", e);
        }
    }

    private void ensureLoaded() {
        if (data != null) return;

        data = new byte[getCapacity()];
        File file = getFile();

        if (file.exists() && file.length() > 0) {
            try (RandomAccessFile raf = new RandomAccessFile(file, "r")) {
                raf.read(data);
            } catch (IOException e) {
                throw new RuntimeException("Failed to read storage file!", e);
            }
        }
    }

    private File getFile() {
        File file = savePath.resolve(id + ".bin").toFile();
        if (!file.exists()) {
            try (RandomAccessFile raf = new RandomAccessFile(file, "rw")) {
                raf.setLength(capacity);
            } catch (IOException e) {
                throw new RuntimeException("Could not create storage file at " + file, e);
            }
        }
        return file;
    }

    private void loadData() {
        DMStorageDataCodec stored = stack.get(DMComponentsRegistry.STORAGE_DATA.get());

        if (stored != null) {
            this.id = stored.id();
            this.used = stored.used();
            this.capacity = stored.capacity();
        } else {
            this.id = UUID.randomUUID().toString();
            this.capacity = generateCapacity();
            this.used = 0;

            stack.set(DMComponentsRegistry.STORAGE_DATA.get(),
                    new DMStorageDataCodec(id, capacity, used));
        }
    }

    public void saveData(ItemStack stack) {
        stack.set(DMComponentsRegistry.STORAGE_DATA.get(),
                new DMStorageDataCodec(id, capacity, used));

        File file = getFile();
        try (RandomAccessFile raf = new RandomAccessFile(file, "rw")) {
            raf.seek(0);
            raf.write(this.data);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save storage file", e);
        }
    }

    @Override
    public int getCapacity() {
        return capacity;
    }

    @Override
    public int getUsed() {
        return used;
    }

    @Override
    public String getId() {
        return id;
    }

    public void initialize() {
        ensureLoaded();
        saveData(stack);
    }

    @Override
    public byte[] read(int offset, int size) {
        ensureLoaded();

        if (offset >= data.length) return new byte[0];
        int end = Math.min(offset + size, data.length);
        return Arrays.copyOfRange(data, offset, end);
    }

    @Override
    public int write(int offset, byte[] input, ItemStack stack) {
        ensureLoaded();

        int writable = Math.min(input.length, data.length - offset);
        System.arraycopy(input, 0, this.data, offset, writable);

        used = Math.max(used, offset + writable);

        stack.set(DMComponentsRegistry.STORAGE_DATA.get(),
                new DMStorageDataCodec(id, capacity, used));

        dirty = true;
        return writable;
    }

    public void flush() {
        if (!dirty) return;
        saveData(this.stack);
    }

    public void tick() {
        flush();
    }

    protected int generateCapacity() {
        return 4 * 1024; // default fallback
    }
}
