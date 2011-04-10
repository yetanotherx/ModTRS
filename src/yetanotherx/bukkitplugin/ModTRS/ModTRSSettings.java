package yetanotherx.bukkitplugin.ModTRS;

import java.sql.Connection;
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
    public static boolean notifyMods = true;
    public static boolean autoupdate = true;
    public static HashMap<String, String> databases = new HashMap<String, String>();
    public static List<String> blacklist = new ArrayList<String>();
    public static String databaseFile = "plugins" + File.separator + "ModTRS" + File.separator + "modtrs.db";

    /**
     * Bukkit config class
     */
    public static Configuration config = null;
    
    /**
     * SQLite configuration
     */
    public static Connection sqlite;

    /**
     * Load and parse the YAML config file
     */
    public static void load() {

	File dataDirectory = new File("plugins" + File.separator + "ModTRS" + File.separator);

	dataDirectory.mkdirs();

	File file = new File("plugins" + File.separator + "ModTRS", "config.yml");

	ModTRS.log.debug("Loading config file: " + file.getPath() );

	config  = new Configuration(file);
	config.load();

	if( !file.exists() ) {
	    ModTRS.log.debug("Config file not found, saving bare-bones file");
	    config.setProperty("modtrs.debug", debugMode );
	    config.setProperty("modtrs.database_file", databaseFile );
	    config.setProperty("modtrs.notify_mods", notifyMods );
	    config.setProperty("modtrs.autoupdate", autoupdate );
	    config.setProperty("modtrs.databases", databases );
	    config.setProperty("modtrs.blacklist", blacklist );
	    config.save();
	}

	setSettings();

	ModTRS.log.debug("Settings loaded");


    }

    /**
     * Sets the internal variables
     */
    private static void setSettings() {

	debugMode = config.getBoolean("modtrs.debug", false );
	databaseFile = config.getString("modtrs.database_file", "plugins" + File.separator + "ModTRS" + File.separator + "modtrs.db");
	notifyMods = config.getBoolean("modtrs.notify_mods", true );
	autoupdate = config.getBoolean("modtrs.autoupdate", true );

	List<String> keys = config.getKeys("modtrs.databases");

	if( keys != null ) {
	    for( String key : keys ) {
		databases.put(key, config.getString("modtrs.databases." + key) );
	    }

	}

	blacklist = config.getStringList("modtrs.blacklist", new ArrayList<String>());

    }

}
