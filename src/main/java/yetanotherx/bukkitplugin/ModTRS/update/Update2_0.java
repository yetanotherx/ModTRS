package yetanotherx.bukkitplugin.ModTRS.update;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import yetanotherx.bukkitplugin.ModTRS.ModTRS;
import yetanotherx.bukkitplugin.ModTRS.util.ModTRSSettings;
import yetanotherx.bukkitplugin.ModTRS.util.config.Configuration;

public class Update2_0 extends ModTRSUpdateBase {

    private ModTRS parent;

    public Update2_0(ModTRS parent) {
        this.parent = parent;
    }

    @Override
    public void update() {
        if (ModTRSSettings.config.getProperty("modtrs.database") != null) {

            ModTRS.log.info("Updating configuration and database to version 2.0");
            
            Configuration oldConfig = ModTRSSettings.config;
            boolean oldDebug = oldConfig.getBoolean("modtrs.debug", false);
            boolean oldCommands = oldConfig.getBoolean("modtrs.log_commands", false);
            boolean oldNotifyMods = oldConfig.getBoolean("modtrs.notify_mods", false);
            List<String> oldBlacklist = oldConfig.getStringList("modtrs.blacklist", new ArrayList<String>());
            int oldRequests = oldConfig.getInt("modtrs.max_requests", -1);
            int oldRPP = oldConfig.getInt("modtrs.requests_per_page", 5);
            HashMap<String, String> oldDatabase = new HashMap<String, String>();
            
            List<String> dbKeys = oldConfig.getKeys("modtrs.database");

            if (dbKeys != null) {
                for (String key : dbKeys) {
                    oldDatabase.put(key, oldConfig.getString("modtrs.database." + key));
                }

            }

            File file = new File("plugins" + File.separator + "ModTRS", "config.yml");
            file.delete();
            ModTRSSettings.saveConfigFromResource(file);

            ModTRSSettings.config.load();
            ModTRSSettings.config.setProperty("modtrs.debug", oldDebug);
            ModTRSSettings.config.setProperty("modtrs.log_commands", oldCommands);
            ModTRSSettings.config.setProperty("modtrs.notify_mods", oldNotifyMods);
            ModTRSSettings.config.setProperty("modtrs.blacklist", oldBlacklist);
            ModTRSSettings.config.setProperty("modtrs.max_requests", oldRequests);
            ModTRSSettings.config.setProperty("modtrs.requests_per_page", oldRPP);

            ModTRSSettings.config.save();

            ModTRSSettings.load(parent);

        }
    }
}