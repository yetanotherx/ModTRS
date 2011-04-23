package yetanotherx.bukkitplugin.ModTRS.update;

import java.io.File;
import java.util.HashMap;
import yetanotherx.bukkitplugin.ModTRS.ModTRS;
import yetanotherx.bukkitplugin.ModTRS.ModTRSSettings;

public class Update1_1 extends ModTRSUpdateBase implements IModTRSUpdate {

    private ModTRS parent;
    public Update1_1( ModTRS parent ) {
        this.parent = parent;
    }

    
    @Override
    public void update() {

        if( ModTRSSettings.config.getString("modtrs.database_file") != null || ModTRSSettings.config.getString("modtrs.database") == null ) {

            ModTRS.log.debug("Updating config file to v1.1");

            String dbFile = ModTRSSettings.config.getString("modtrs.database_file");
            
            ModTRSSettings.config.removeProperty("modtrs.database_file");

            HashMap<String, String> newDb = new HashMap<String, String>();

            newDb.put("type", "sqlite");
            newDb.put("user", "");
            newDb.put("port", "");
            newDb.put("pass", "");
            newDb.put("database", "plugins" + File.separator + "ModTRS" + File.separator + "modtrs.db");
            newDb.put("server", "");

            ModTRSSettings.config.setProperty("modtrs.database", newDb);

            ModTRSSettings.config.save();
            ModTRSSettings.load();

            ModTRS.log.debug("Finished updating config file to v1.1");


        }
        
    }

}
