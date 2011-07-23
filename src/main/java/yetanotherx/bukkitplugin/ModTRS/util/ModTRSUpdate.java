package yetanotherx.bukkitplugin.ModTRS.util;

import java.util.HashMap;
import yetanotherx.bukkitplugin.ModTRS.ModTRS;
import yetanotherx.bukkitplugin.ModTRS.update.IModTRSUpdate;
import yetanotherx.bukkitplugin.ModTRS.update.Update2_0;

public class ModTRSUpdate {

    private static HashMap<Double, IModTRSUpdate> updates = new HashMap<Double, IModTRSUpdate>();
    private static boolean initialized = false;

    public static void load( ModTRS parent ) {

        parent.log.debug("Checking for updates");

        if( !initialized ) {
            updates.put( 2.0, new Update2_0(parent) );

            initialized = true;
        }

        IModTRSUpdate update;
        for( Double version : updates.keySet() ) {
            update = updates.get(version);
            update.update();
        }

    }
}
