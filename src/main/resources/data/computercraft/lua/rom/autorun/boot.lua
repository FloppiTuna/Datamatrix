print("DMATRIX Compatibility Driver Version 0.01")

local readers = dmio.getAllReaders()
local count = 0
for _ in pairs(readers) do count = count + 1 end
print("DMATRIX loaded. " .. count .. " attached storage peripherals.")

-- open /fstab.dmx to see if there are any devices to auto mount
