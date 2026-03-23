--[[
    Datamatrix tool for testing API
]]--

local basaltLoadStatus, basalt = pcall(function()
    return require("/basalt")
end)

print(basaltLoadStatus)
print(basalt)

if (not basaltLoadStatus) then
    term.setTextColor(color.red)
    print("ERROR: The Basalt UI framework is required to run dmtool, but it is not available.")
    print("Please visit basalt.madefor.cc to install it.")
    os.exit()
end

if (not dmio) then
    term.setTextColor(color.red)
    print("ERROR: The Datamatrix I/O Interactions API is not available on this system.")
    print("This server may be configured incorrectly. Please contact the server host.")
    os.exit()
end

-- at this point we are ready to begin initializing the tool's components
local main = basalt.getMainFrame()
local screenX, screenY = term.getSize()


local header = main:addLabel()
                   :setText("DMtool for CraftOS")
                   :setPosition(1, 1)
                   :setSize(screenX, 1)

local root = main:addContainer()
                 :setPosition(1, 2)
                 :setSize(screenX, screenY - 1)

local readerSide = root:addTable()
                        :setPosition(2, 2)
                        :setSize((screenX / 2) - 2, screenY - 3)
                        :setColumns({
                            {name = "Reader"},
                        })

local manageSide = root:addContainer()
                       :setPosition((screenX / 2) + 2, 2)
                       :setSize((screenX / 2) - 1, screenY - 3)



parallel.waitForAll(
        function() basalt.run() end,
        function()
            while true do
                header:setText("DMtool for CraftOS - " .. os.date("%H:%M:%S") .. " - " .. screenX .. " " .. screenY)
                os.sleep(1)
            end
        end,
        function()
            local readers = dmio.getAllReaders()
            for periph, type in pairs(readers) do
                readerSide:addRow(periph)
            end
        end
)


