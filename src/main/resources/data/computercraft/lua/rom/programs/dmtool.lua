--[[
    Datamatrix Management Tool for CC: Tweaked
    FloppiTuna, March 23rd, 2026
]]

if (not dmio) then
    term.setTextColor(colors.red)
    print("ERROR: The Datamatrix I/O Interactions API is not available on this system.")
    print("This server may be configured incorrectly. Please contact the server host.")
    os.exit()
end

function resetScreen()
    term.clear()
    term.setCursorPos(1, 1)
end

resetScreen()

print("Scanning for attached reader peripherals...")
local readers = dmio.getAttachedReaders()
local definitions = dmio.getReaderDefinitions()

local readerNames = {}
for peripheral, v in pairs(readers) do
    table.insert(readerNames, peripheral)
end

local readerSession
if (#readerNames > 1) then
    resetScreen()
    print("There are " .. #readerNames .. " readers attached to this computer:")
    for pos, peripheral in pairs(readerNames) do
        print("[" .. pos .. "] " .. peripheral)
        if (readers[peripheral].hasMedia) then
            term.setTextColor(colors.cyan)
            write("     " .. readers[peripheral].media.type .. " / ")
            if (readers[peripheral].media.initialized) then
                term.setTextColor(colors.orange)
                if (readers[peripheral].media.label ~= "") then
                    print(readers[peripheral].media.label)
                else
                    print(readers[peripheral].media.type .. " Media")
                end
            else
                term.setTextColor(colors.red)
                print("Uninitialized")
            end
        else
            term.setTextColor(colors.gray)
            print("     no media")
        end
        term.setTextColor(colors.white)
    end
    print("")
    print("Please select the reader you would like to use.")

    write("> ")
    local choice = tonumber(read())
    if not choice or not readerNames[choice] then
        print("Invalid choice.")
        os.exit()
    end

    readerSession = dmio.open(readerNames[choice])
elseif (#readerNames == 1) then
    readerSession = dmio.open(readerNames[1])
else
    term.setTextColor(colors.red)
    print("ERROR: No Datamatrix readers attached.")
    os.exit()
end

if (not readerSession.peripheral.hasMedia()) then
    resetScreen()
    print("Please insert one of the following media types into " .. readerSession.name .. ".")
    term.setTextColor(colors.orange)
    for _, media in pairs(definitions[readerSession.type].mediaTypes) do
        print("* " .. media)
    end
    term.setTextColor(colors.white)

    repeat
        write(".")
        os.sleep(1)
    until readerSession.peripheral.hasMedia()
end

local initialized, mediaId = pcall(readerSession.peripheral.getId) -- awesome swagalicious workaround because im lazy
if (initialized == false) then
    resetScreen()
    term.setTextColor(colors.orange)
    print("Uninitialized")
    term.setTextColor(colors.white)
    print("The media you have inserted into " .. readerSession.name .. ", an " .. readerSession.peripheral.getMediaType() .. ", is not initialized.")
    print("In order to perform operations on this media, you must first initialize it.")
    print("Would you like to initialize it now?")
    print()
    write("[y/N] > ")
    local choice = read()

    if (choice:lower() == "y") then
        resetScreen()
        print("Initializing " .. readerSession.peripheral.getMediaType() .. "...")
        readerSession.peripheral.initialize()
        print("Done.")
    else
        resetScreen()
        print("You chose not to initialize this media.")
        shell.exit()
    end
end

-- main menu time
local screens = {}

screens.MAIN_MENU = function()
    resetScreen()
    term.setTextColor(colors.orange)
    print("Main Menu")
    term.setTextColor(colors.white)

    print("Label: " .. readerSession.peripheral.getLabel())
    print("ID: " .. readerSession.peripheral.getId())
    print("Usage: " .. readerSession.peripheral.getUsed() .. "/" .. readerSession.peripheral.getCapacity())

    print()

    print("[1] Format...")
    print("[0] Exit")

    print()
    write("> ")
    local choice = tonumber(read())

    if choice == 0 then
        resetScreen()
        print("Bye!")
        return
    elseif choice == 1 then
        screens.FORMAT_MENU()
    end
end

screens.FORMAT_MENU = function()
    resetScreen()
end

screens.MAIN_MENU()




