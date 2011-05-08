package yetanotherx.bukkitplugin.ModTRS.update;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import yetanotherx.bukkitplugin.ModTRS.ModTRS;
import yetanotherx.bukkitplugin.ModTRS.ModTRSSettings;
import yetanotherx.bukkitplugin.ModTRS.sql.ModTRSDatabase;

public class Update1_4 extends ModTRSUpdateBase implements IModTRSUpdate {

    private ModTRS parent;

    public Update1_4(ModTRS parent) {
        this.parent = parent;
    }

    @Override
    public void update() {

        if (ModTRSSettings.config.getProperty("modtrs.max_requests") == null) {

            ModTRS.log.debug("Updating ModTRS to v1.4");

            ModTRSSettings.config.setProperty("modtrs.max_requests", -1);

            ModTRSSettings.config.save();
            ModTRSSettings.load();

            try {
                parent.databaseHandler = new ModTRSDatabase(parent);
                parent.databaseHandler.getDatabase().stat().executeUpdate(parent.databaseHandler.getDatabase().addModCommentField());
                parent.databaseHandler.getDatabase().stat().executeUpdate(parent.databaseHandler.getDatabase().addServerField());

                if( !ModTRSSettings.database.get("type").equals("sqlite") ) {
                    parent.databaseHandler.getDatabase().stat().executeUpdate( parent.databaseHandler.getDatabase().updateLocationFieldsToMediumint());
                }
                
            } catch (Exception ex) {
                ex.printStackTrace();
                ModTRS.log.severe("Could not update!");
            }



            ModTRS.log.debug("Finished updating to v1.4");


        }

    }
}
