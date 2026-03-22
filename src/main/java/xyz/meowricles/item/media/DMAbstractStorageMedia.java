package xyz.meowricles.item.media;

import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
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

    private final ItemStack stack;
    private final Path savePath;

    public DMAbstractStorageMedia(ItemStack stack) {
        this.stack = stack;
        loadData();
        this.data = new byte[getCapacity()];
        this.savePath = Paths.get("datamatrix/storage/");
        try {
            Files.createDirectories(savePath);
        } catch (IOException e) {
            throw new RuntimeException("Failed to create storage folder", e);
        }

        // now load the file
        File file = getFile();
        if (file.exists() && file.length() > 0) {
            try (RandomAccessFile raf = new RandomAccessFile(file, "r")) {
                int bytesRead = raf.read(this.data);
                if (bytesRead != this.data.length) {
                    // If file is smaller than capacity, remaining bytes stay zero
                    System.out.println("Partial read: " + bytesRead + " bytes");
                }
            } catch (IOException e) {
                throw new RuntimeException("Failed to read storage file", e);
            }
        }
    }

    private File getFile() {
        File file = savePath.resolve(id + ".bin").toFile();
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException("Could not create storage file at " + file, e);
            }
        }
        return file;
    }

    private void loadData() {
        DMStorageDataCodec stored = stack.get(DMComponentsRegistry.STORAGE_DATA.get());

        if (stored != null) {
            // this feels like one of those things that gets u crucified and put into software dev purgatory
            this.id = stored.id();
            this.used =  stored.used();
            // okay eventually we ened to actually save and load the data to disk sighhhh i hate filesystems
//            this.data =
        } else {
            // fresh storage media, initialize it
            this.id = UUID.randomUUID().toString();
            int capacity = generateCapacity(); // <- IMPORTANT
            this.used = 0;

            stack.set(DMComponentsRegistry.STORAGE_DATA.get(), new DMStorageDataCodec(id, capacity, used));
        }
    }

    private void saveData() {
        stack.set(DMComponentsRegistry.STORAGE_DATA.get(), new DMStorageDataCodec(id, getCapacity(), used));

        // Write actual bytes to file
        File file = getFile();
        try (RandomAccessFile raf = new RandomAccessFile(file, "rw")) {
            raf.setLength(0); // truncate before writing
            raf.write(this.data, 0, used); // only write used bytes
        } catch (IOException e) {
            throw new RuntimeException("Failed to save storage file", e);
        }
    }

    @Override
    public int getCapacity() {
        DMStorageDataCodec stored = stack.get(DMComponentsRegistry.STORAGE_DATA.get());
        return stored != null ? stored.capacity() : 0;
    }

    @Override
    public int getUsed() {
        return used;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public byte[] read(int offset, int size) {
        int end = Math.min(offset + size, data.length);
        return Arrays.copyOfRange(data, offset, end);
    }

    @Override
    public void write(int offset, byte @NotNull [] input) {
        int writable = Math.min(input.length, data.length - offset);
        System.arraycopy(input, 0, this.data, offset, writable);

        used = Math.max(used, offset + writable);

        // todo: writing to disk on EVERY write operation is stupid because we dont live in the year 1986 anymore
        // implement a better way to do this while also not absolutely incinerating the server
        saveData();
    }

    protected int generateCapacity() {
        return 4 * 1024; // default fallback
    }
}
