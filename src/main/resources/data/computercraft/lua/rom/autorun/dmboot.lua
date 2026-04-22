print("DMATRIX Compatibility Driver Version ${mod_version}")

local readers = dmio.getAttachedReaders()
local count = 0
for _ in pairs(readers) do count = count + 1 end
print("DMATRIX loaded. " .. count .. " attached storage peripherals.")

-- check filesystem defs
local filesystems = dmfs.getFilesystems()

-- on startup check to make sure that the headers in each filesystem are defined correctly
for fs, def in pairs(filesystems) do

end


-- open /fstab.dmx to see if there are any devices to auto mount
