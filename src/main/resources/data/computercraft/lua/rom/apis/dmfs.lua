--[[
    Datamatrix Filesystems API for CC: Tweaked
    FloppiTuna, April 1st, 2026
]]

local filesystems = {
    ["DMCD"] = {
        friendlyName = "Datamatrix Audio CD Filesystem",
        header = {
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

    -- create blank buffer
    local buffer = string.rep("\0", fs.header.clamp)


    for _, component in ipairs(fs.header.components) do

    end

    return header;
end

print(
        assembleHeader("DMCD")
)