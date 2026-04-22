import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.luaj.vm2.*;

import org.luaj.vm2.lib.jse.*;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DMFSTests {
    @Test
    @DisplayName("should return valid filesystems")
    void testDMFSFileSystems() {
        Globals globals = JsePlatform.standardGlobals();
        LuaValue chunk = globals.loadfile("src/main/resources/data/computercraft/lua/rom/apis/dmfs.lua");
        chunk.call();

        LuaValue getFilesystems = globals.get("getFilesystems");
        assertNotNull(getFilesystems);
        LuaValue fs = getFilesystems.call();
        assertNotNull(fs);
        assertNotNull(fs.get("DMCD"));
    }

    @Test
    @DisplayName("should assemble filesystem headers")
    void testDMFSHeader() {
        Globals globals = JsePlatform.standardGlobals();

        LuaValue chunk = globals.loadfile("src/main/resources/data/computercraft/lua/rom/apis/dmfs.lua");
        chunk.call();

        LuaValue assembleHeader = globals.get("assembleHeader");
        assertNotNull(assembleHeader);

        LuaValue params = LuaTable.tableOf();
        params.set("label", LuaString.valueOf("TestMix1"));

        LuaValue header = assembleHeader.call(LuaString.valueOf("DMCD"), params);
        assertNotNull(header);

        String headerStr = header.tojstring();
        org.junit.jupiter.api.Assertions.assertTrue(headerStr.startsWith("DMCDFS00"));
    }
}
