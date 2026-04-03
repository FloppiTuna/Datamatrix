package xyz.meowricles.peripheral;

import dan200.computercraft.api.peripheral.GenericPeripheral;
import dan200.computercraft.api.peripheral.PeripheralType;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import xyz.meowricles.Datamatrix;

public class DMBaseCCPeripheral implements GenericPeripheral {
    private final String name;

    public DMBaseCCPeripheral(String name) {
        super();
        this.name = name;
    }

    @Override
    public @NotNull PeripheralType getType() {
        return PeripheralType.ofType(name);
    }

    @Override
    public @NotNull String id() {
        return ResourceLocation.fromNamespaceAndPath(Datamatrix.MODID, name).toString();
    }
}
