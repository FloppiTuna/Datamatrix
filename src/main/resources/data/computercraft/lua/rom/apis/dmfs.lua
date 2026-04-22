--[[
    Datamatrix Filesystems API for CC: Tweaked
    FloppiTuna, April 1st, 2026
]]

local filesystems = {
    ["DMCD"] = {
        friendlyName = "Datamatrix Audio CD Filesystem",
        header = {
            signature = "DMCDFS00",
            components = {
                { name = "labelLength", format = "I4", derive = function(ctx) return #(ctx.label or "") end },
                { name = "label", format = "z" },
                { name = "trackCount", format = "I4" },
            }
        }
    }
}

function getFilesystems()
    return filesystems;
end

function assembleHeader(name, params)
    params = params or {}

    local fs = filesystems[name]
    if not fs then
        error("Cannot assemble header: filesystem " .. name .. " doesn't exist")
    end

    local format = "" -- the first 4 bytes after the header will contain the total size of the header
    local values = {}

    -- build format string + provided values
    for _, comp in ipairs(fs.header.components) do
        format = format .. comp.format

        local value = "";
        -- check if this component derives from another component (not provided)
        if (comp.derive) then
            value = comp.derive(params)
            table.insert(values, value)
        else
            value = params[comp.name]
            table.insert(values, value)
        end

--         -- if the value is still empty
--         if (value == "") then
--             print("Cannot assemble header: component" .. comp.name .. " never provided or processed")
--         end
    end

    -- calculate total header size
    local headerSize = #(string.pack(format, table.unpack(values)))

    -- add header size total
    table.insert(values, 1, headerSize)
    format = "<I4" .. format

    -- prepend signature (raw string, not packed)
    local header = fs.header.signature .. string.pack(format, table.unpack(values))

    return header;
end

function processHeader(name, header)

end

print(
    string.sub(
        assembleHeader("DMCD", { label = "totallyawesomesexymix", trackCount = 14 } ),
        1,
        -2
    ) -- string cleanup for testing because lua makes me want to tear my brain out and eat it
)