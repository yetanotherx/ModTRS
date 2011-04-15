package yetanotherx.bukkitplugin.ModTRS;

import java.util.HashMap;
import yetanotherx.bukkitplugin.ModTRS.update.IModTRSUpdate;
import yetanotherx.bukkitplugin.ModTRS.update.Update1_1;

public class ModTRSUpdate {

    private static HashMap<Double, IModTRSUpdate> updates = new HashMap<Double, IModTRSUpdate>();
    private static boolean initialized = false;

    public static void load( ModTRS parent ) {

        if( !initialized ) {
            updates.put( 1.1, new Update1_1(parent) );

            initialized = true;
        }

        IModTRSUpdate update;
        for( Double version : updates.keySet() ) {
            update = updates.get(version);
            update.update();
        }

    }
}
