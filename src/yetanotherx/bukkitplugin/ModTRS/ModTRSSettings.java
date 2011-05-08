package yetanotherx.bukkitplugin.ModTRS;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.io.File;

import org.bukkit.util.config.Configuration;

public class ModTRSSettings {

    /**
     * Settings
     */
    public static boolean debugMode = false;
    public static boolean logCommands = false;
    public static boolean notifyMods = true;
    public static boolean autoupdate = true;
    public static HashMap<String, String> databases = new HashMap<String, String>();
    public static List<String> blacklist = new ArrayList<String>();
    public static HashMap<String, String> database = new HashMap<String, String>();
    public static int maxRequests = -1;
    /**
     * Bukkit config class
     */
    public static Configuration config = null;

    /**
     * Load and parse the YAML config file
     */
    public static void load() {

        File dataDirectory = new File("plugins" + File.separator + "ModTRS" + File.separator);

        dataDirectory.mkdirs();

        File file = new File("plugins" + File.separator + "ModTRS", "config.yml");

        ModTRS.log.debug("Loading config file: " + file.getPath());

        config = new Configuration(file);
        config.load();

        if (!file.exists()) {
            ModTRS.log.debug("Config file not found, saving bare-bones file");
            config.setProperty("modtrs.debug", debugMode);
            config.setProperty("modtrs.log_commands", logCommands);
            config.setProperty("modtrs.database", getDefaultDatabase());
            config.setProperty("modtrs.notify_mods", notifyMods);
            config.setProperty("modtrs.autoupdate", autoupdate);
            config.setProperty("modtrs.databases", databases);
            config.setProperty("modtrs.blacklist", blacklist);
            config.setProperty("modtrs.max_requests", maxRequests);
            config.save();
        }

        setSettings();

        ModTRS.log.debug("Settings loaded");


    }

    /**
     * Sets the internal variables
     */
    private static void setSettings() {

        debugMode = config.getBoolean("modtrs.debug", false);
        logCommands = config.getBoolean("modtrs.log_commands", false);
        notifyMods = config.getBoolean("modtrs.notify_mods", true);
        autoupdate = config.getBoolean("modtrs.autoupdate", true);

        List<String> keys = config.getKeys("modtrs.databases");

        if (keys != null) {
            for (String key : keys) {
                databases.put(key, config.getString("modtrs.databases." + key));
            }

        }

        List<String> dbKeys = config.getKeys("modtrs.database");

        if (dbKeys != null) {
            for (String key : dbKeys) {
                database.put(key, config.getString("modtrs.database." + key));
            }

        }

        blacklist = config.getStringList("modtrs.blacklist", new ArrayList<String>());


        //Why do all this? Because Bukkit is weird and sets the property if it isn't found.
        Integer o = castInt(config.getProperty("modtrs.max_requests"));
        if (o == null) {
            maxRequests = -1;
        } else {
            maxRequests = o;
        }

    }

    private static HashMap<String, String> getDefaultDatabase() {

        HashMap<String, String> newDb = new HashMap<String, String>();

        newDb.put("type", "sqlite");
        newDb.put("user", "");
        newDb.put("port", "");
        newDb.put("pass", "");
        newDb.put("database", "plugins" + File.separator + "ModTRS" + File.separator + "modtrs.db");
        newDb.put("server", "");

        return newDb;

    }

    private static Integer castInt(Object o) {
        if (o == null) {
            return null;
        } else if (o instanceof Byte) {
            return (int) (Byte) o;
        } else if (o instanceof Integer) {
            return (Integer) o;
        } else if (o instanceof Double) {
            return (int) (double) (Double) o;
        } else if (o instanceof Float) {
            return (int) (float) (Float) o;
        } else if (o instanceof Long) {
            return (int) (long) (Long) o;
        } else {
            return null;
        }
    }
}
