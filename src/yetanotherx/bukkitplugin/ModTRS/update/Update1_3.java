package yetanotherx.bukkitplugin.ModTRS.update;

import yetanotherx.bukkitplugin.ModTRS.ModTRS;
import yetanotherx.bukkitplugin.ModTRS.ModTRSSettings;

public class Update1_3 extends ModTRSUpdateBase implements IModTRSUpdate {

    private ModTRS parent;
    public Update1_3( ModTRS parent ) {
        this.parent = parent;
    }


    @Override
    public void update() {
        
        if( !ModTRSSettings.config.getBoolean("modtrs.log_commands", false) ) {

            ModTRS.log.debug("Updating config file to v1.3");

            ModTRSSettings.config.setProperty("modtrs.log_commands", false);

            ModTRSSettings.config.save();
            ModTRSSettings.load();

            ModTRS.log.debug("Finished updating config file to v1.3");


        }

    }

}
