--[[
    Datamatrix I/O Interactions API for CC: Tweaked
    FloppiTuna, March 22nd, 2026
]]

local readerPeripherals = {
    ["universal_adapter"] = {
        mediaTypes = {
            "COMPACT_DISC"
        },
    },
};

function getReaderDefinitions()
    return readerPeripherals
end

function getAttachedReaders()
    local peripherals = peripheral.getNames()
    local result = {}
    for _, name in pairs(peripherals) do
        local type = peripheral.getType(name)

        if readerPeripherals[type] then
            local hasMedia = peripheral.call(name, "hasMedia")
            local initialized, mediaId = pcall(function() return peripheral.call(name, "getId") end) -- awesome swagalicious workaround because im lazy
            result[name] = {
                type = type,
                hasMedia = hasMedia,
                media = {
                    type = (hasMedia) and peripheral.call(name, "getMediaType") or "",
                    label = (hasMedia and initialized) and peripheral.call(name, "getLabel") or "",
                    initialized = initialized,
                    mediaId = (hasMedia and initialized) and mediaId or "",
                }
            };
        end
    end
    return result
end

function open(name)
        if not peripheral.isPresent(name) then
            error("Failed to open reader: peripheral does not exist")
        end

        local type = peripheral.getType(name)
        if not readerPeripherals[type] then
            error("Failed to open reader: dmio is unfamiliar with this device; is it a reader?")
        end

        local wrapped = peripheral.wrap(name)

        return {
            name = name,
            type = type,
            peripheral = wrapped
        }
end


--function open(name)
--    if not peripheral.isPresent(name) then
--        return nil, "Target peripheral is missing!"
--    end
--
--    local type = peripheral.getType(name)
--    if not readerPeripherals[type] then
--        return nil, "Target peripheral is not a Datamatrix device!"
--    end
--
--    local p = peripheral.wrap(name)
--    local session = {
--        name = name,
--        type = type,
--        peripheral = p,
--        isOpen = true,
--    }
--
--    function session.hasMedia()
--        if not session.isOpen then return nil, "Session closed" end
--        return session.peripheral.hasMedia()
--    end
--
--    function session.initialize()
--        if not session.isOpen then return nil, "Session closed" end
--        return session.peripheral.initialize()
--    end
--
--    function session.read(offset, length, _length)
--        if offset == session then offset, length = length, _length end
--        if not session.isOpen then return nil, "Session closed" end
--        return session.peripheral.read(offset, length)
--    end
--
--    function session.write(offset, data, _data)
--        if offset == session then offset, data = data, _data end
--        if not session.isOpen then return nil, "Session closed" end
--        return session.peripheral.write(offset, data)
--    end
--
--    function session.close()
--        session.isOpen = false
--        session.peripheral = nil
--    end
--
--    return session
--end

