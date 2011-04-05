package yetanotherx.bukkitplugin.ModTRS;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.bukkit.util.config.Configuration;

import yetanotherx.bukkitplugin.ModTRS.sql.ModTRSSQL;

public class ModTRSSettings {

    public static boolean debugMode = false;
    
    public static Configuration config = null;

    private static Connection sqlite;
    
    public static void load( ModTRS parent ) throws SQLException {
	
	File dataDirectory = new File("plugins" + File.separator + "ModTRS" + File.separator);
	
	dataDirectory.mkdirs();
	
	File file = new File("plugins" + File.separator + "BoosterTrax", "blocks.yml");
	
	ModTRS.log.debug("Loading config file: " + file.getPath() );
	
	config  = new Configuration(file);
	config.load();
	
	if( !file.exists() ) {
	    ModTRS.log.debug("Config file not found, saving bare-bones file");
	    config.setProperty("modtrs.debug", false );
	    config.save();
	}
	
	setSettings();
	
	setupSQLite(parent);
	
	ModTRS.log.debug("Settings loaded");
	
	
    }
    
    private static void setSettings() {
	
	debugMode = config.getBoolean("modtrs.debug", false );
	
    }
    
    private static void setupSQLite( ModTRS parent ) throws SQLException {

	String databaseUrl = "jdbc:sqlite:plugins" + File.separator + "ModTRS" + File.separator + "modtrs.db";

	ModTRS.log.debug("Loading SQLite: " + databaseUrl );
	
	try {
	    Class.forName("org.sqlite.JDBC");
	}
	catch( ClassNotFoundException e) {
	    ModTRS.log.severe("Error: Cannot locate the SQLite JDBC. Please download from http://www.zentus.com/sqlitejdbc/ and place in the plugins/ModTRS/ folder.");
	    parent.getPluginLoader().disablePlugin(parent);
	}

	sqlite = DriverManager.getConnection(databaseUrl);

	ModTRS.log.debug("Creating tables if necessary" );
	Statement stat = sqlite.createStatement();
	stat.executeUpdate(ModTRSSQL.createUser);
	stat.executeUpdate(ModTRSSQL.createRequest);
	
	ModTRS.log.debug("Finished loading SQLite" );

    }
}
