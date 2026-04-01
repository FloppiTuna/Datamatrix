print("DMATRIX Compatibility Driver Version 0.01")

local readers = dmio.getAttachedReaders()
local count = 0
for _ in pairs(readers) do count = count + 1 end
print("DMATRIX loaded. " .. count .. " attached storage peripherals.")

-- check filesystem defs
local filesystems = dmfs.getFilesystems()

-- on startup check to make sure that the headers in each filesystem are defined correctly
for fs, def in pairs(filesystems) do
    local headerAt0x0 = false
    for _, component in pairs(def.header) do
        if (component.pos == 0x0) then headerAt0x0 = true end;
    end

    if (not headerAt0x0) then
        term.setTextColor(colors.yellow)
        print("dmfs warning: filesystem " .. fs .. "'s header doesnt begin at 0x0. this might be unintended!")
        term.setTextColor(colors.white)
    end
end


-- open /fstab.dmx to see if there are any devices to auto mount
