package xyz.meowricles.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record DMStorageDataCodec(String id, int capacity, int used) {

    public static final Codec<DMStorageDataCodec> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
//                    Codec.BYTE_BUFFER.xmap(
//                            buf -> {
//                                byte[] arr = new byte[buf.remaining()];
//                                buf.get(arr);
//                                return arr;
//                            },
//                            ByteBuffer::wrap
//                    ).fieldOf("data").forGetter(StorageData::data),
                    Codec.STRING.fieldOf("id").forGetter(DMStorageDataCodec::id),
                    Codec.INT.fieldOf("capacity").forGetter(DMStorageDataCodec::capacity),
                    Codec.INT.fieldOf("used").forGetter(DMStorageDataCodec::used)
            ).apply(instance, DMStorageDataCodec::new)
    );
}