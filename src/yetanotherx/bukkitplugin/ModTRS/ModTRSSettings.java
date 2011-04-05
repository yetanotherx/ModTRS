package yetanotherx.bukkitplugin.ModTRS;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.bukkit.util.config.Configuration;

import yetanotherx.bukkitplugin.ModTRS.sql.ModTRSSQL;

public class ModTRSSettings {

    public static boolean debugMode = false;
    public static boolean notifyMods = true;
    public static HashMap<String, String> databases = new HashMap<String, String>();
    public static List<String> blacklist = new ArrayList<String>();
    
    
    public static Configuration config = null;
    public static Connection sqlite;

    public static void load( ModTRS parent ) throws SQLException {

	File dataDirectory = new File("plugins" + File.separator + "ModTRS" + File.separator);

	dataDirectory.mkdirs();

	File file = new File("plugins" + File.separator + "ModTRS", "config.yml");

	ModTRS.log.debug("Loading config file: " + file.getPath() );

	config  = new Configuration(file);
	config.load();

	if( !file.exists() ) {
	    ModTRS.log.debug("Config file not found, saving bare-bones file");
	    config.setProperty("modtrs.debug", debugMode );
	    config.setProperty("modtrs.notify_mods", notifyMods );
	    config.setProperty("modtrs.databases", databases );
	    config.setProperty("modtrs.blacklist", blacklist );
	    config.save();
	}

	setSettings();

	setupSQLite(parent);

	ModTRS.log.debug("Settings loaded");


    }

    private static void setSettings() {

	debugMode = config.getBoolean("modtrs.debug", false );
	notifyMods = config.getBoolean("modtrs.notify_mods", true );

	List<String> keys = config.getKeys("modtrs.databases");

	if( keys != null ) {
	    for( String key : keys ) {
		databases.put(key, config.getString("modtrs.databases." + key) );
	    }

	}
	
	blacklist = config.getStringList("modtrs.blacklist", new ArrayList<String>());

    }

    private static void setupSQLite( ModTRS parent ) throws SQLException {

	String databaseUrl = "jdbc:sqlite:plugins" + File.separator + "ModTRS" + File.separator + "modtrs.db";

	ModTRS.log.debug("Loading SQLite: " + databaseUrl );

	try {
	    Class.forName("org.sqlite.JDBC");
	}
	catch( ClassNotFoundException e) {
	    ModTRS.log.severe("Error: Cannot locate the SQLite JDBC. Please download from http://www.zentus.com/sqlitejdbc/ and place in the plugins/ModTRS/ folder.");
	    parent.getServer().getPluginManager().disablePlugin(parent);
	}

	sqlite = DriverManager.getConnection(databaseUrl);

	ModTRS.log.debug("Creating tables if necessary" );
	Statement stat = sqlite.createStatement();
	stat.executeUpdate(ModTRSSQL.createUser);
	stat.executeUpdate(ModTRSSQL.createRequest);

	ModTRS.log.debug("Finished loading SQLite" );

    }
}
