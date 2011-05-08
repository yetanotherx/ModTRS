package yetanotherx.bukkitplugin.ModTRS.update;

import yetanotherx.bukkitplugin.ModTRS.ModTRS;
import yetanotherx.bukkitplugin.ModTRS.ModTRSSettings;

public class Update1_4 extends ModTRSUpdateBase implements IModTRSUpdate {

    private ModTRS parent;
    public Update1_4( ModTRS parent ) {
        this.parent = parent;
    }


    @Override
    public void update() {

        if( ModTRSSettings.config.getProperty("modtrs.max_requests") == null ) {

            ModTRS.log.debug("Updating ModTRS to v1.4");

            ModTRSSettings.config.setProperty("modtrs.max_requests", -1);

            ModTRSSettings.config.save();
            ModTRSSettings.load();

            ModTRS.log.debug("Finished updating to v1.4");


        }

    }

}
