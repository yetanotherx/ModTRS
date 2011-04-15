package yetanotherx.bukkitplugin.ModTRS;

//Bukkit imports
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

//LWC import
import com.griefcraft.lwc.DriverStub;
import com.griefcraft.lwc.Updater;

//ModTRS import
import yetanotherx.bukkitplugin.ModTRS.command.CommandHandler;
import yetanotherx.bukkitplugin.ModTRS.exception.ShutdownException;
import yetanotherx.bukkitplugin.ModTRS.sql.ModTRSSQL;

//Java import
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


/*
 * ModTRS Version 1.1 - Moderator Request Ticket System
 * Copyright (C) 2011 Yetanotherx <yetanotherx -a--t- gmail -dot- com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * Note that this license applies to all the files in this package, unless
 * otherwise specified.
 */

/**
 * TODO: Unit tests
 * TODO: Notify user when they log in if their request is closed (needs SQL change)
 * TODO: Add H2, MySQL, PostgreSQL (maybe) support (needs SQL change)
 * TODO: Disable claiming closed requests
 * TODO: /claim enabled god mode
 * TODO: /done # text (needs SQL change)
 * TODO: Ability to disable users having more than 1 open mod request
 */
public class ModTRS extends JavaPlugin {

    /**
     * ModTRS logger class
     */
    public static final ModTRSLogger log = new ModTRSLogger();

    /**
     * Command handler instance
     */
    private CommandHandler commandHandler;

    /**
     * Event listener registration class
     */
    public ModTRSListeners listeners;

    /**
     * Updater class
     */
    public Updater updater;

    public ModTRS() {
	ModTRSSettings.load();
	updater = new Updater(this);
    }

    /**
     * Outputs a message when disabled
     */
    @Override
    public void onDisable() {
	log.info("Plugin disabled. (version " + this.getDescription().getVersion() + ")");

	try {
	    if( ModTRSSettings.sqlite != null ) {
		log.debug("Closing SQLite database");
		ModTRSSettings.sqlite.close();
	    }
	} catch (SQLException e) {
	}

    }

    /**
     * Perform some massive loading action
     * 
     * Step 1: Load and parse config file, creating it if it doesn't exist.
     * Step 2: Check for updates, downloading JAR files if necessary
     * Step 3: Initialize the SQLite library, creating the tables if they don't exist
     * Step 4: Load the Permissions plugin
     * Step 5: Initialize the Help page
     * Step 6: Register events and listeners
     * Step 7: Register commands
     * 
     */
    @Override
    public void onEnable() {

	try {
	    ModTRSSettings.load();


	    log.debug("Checking for updates");
	    System.setProperty("org.sqlite.lib.path", updater.getOSSpecificFolder());
	    updater.loadVersions();

            ModTRSUpdate.load(this);

	    try {
		setupSQLite( this );
	    }
            catch( ShutdownException e ) {
                throw e;
            }
	    catch( Exception e ) {
		String log_text = "SQL exception! Disabling plugin (version " + this.getDescription().getVersion() + ")";
		log.severe(log_text);
                e.printStackTrace();
		throw new ShutdownException(log_text);
            }

	    ModTRSPermissions.load(this);

	    ModTRSHelp.load(this);
	    this.listeners = ModTRSListeners.load(this);
	    this.commandHandler = CommandHandler.load(this);

	    //Print that the plugin has been enabled!
	    log.info("Plugin enabled! (version " + this.getDescription().getVersion() + ")");
	    log.debug("Debug mode enabled!");

	}
	catch( ShutdownException e ) {
	    log.severe("Caught a shutdown command! " + e.getMessage() );
            e.printStackTrace();
	    this.getServer().getPluginManager().disablePlugin(this);
	    return;
	}
    }

    /**
     * Calls the command handler, which performs some magic to perform the command
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {

	return this.commandHandler.onCommand(sender, command, commandLabel, args);
    }

    /**
     * Initiate SQLite
     * 
     * @throws SQLException
     * @throws MalformedURLException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public static void setupSQLite( ModTRS parent ) throws SQLException, MalformedURLException, InstantiationException, IllegalAccessException, ShutdownException {

        if( !ModTRSSettings.database.get("type").equals("sqlite") ) {
            throw new ShutdownException("Only SQLite has been implemented");
        }
        
	String databaseUrl = "jdbc:sqlite:" + ModTRSSettings.database.get("database");

	ModTRS.log.debug("Loading SQLite: " + databaseUrl );

	try {
	    URLClassLoader classLoader = new URLClassLoader(new URL[] { new URL("jar:file:" + new File(Updater.DEST_LIBRARY_FOLDER + "lib/sqlite.jar") + "!/" ) });
	    Driver driver = (Driver) classLoader.loadClass("org.sqlite.JDBC").newInstance();
	    DriverManager.registerDriver(new DriverStub(driver));
	}
	catch( ClassNotFoundException e) {
	    ModTRS.log.severe("Error: Cannot locate the SQLite JDBC. Please download from http://www.zentus.com/sqlitejdbc/ and place in the plugins/ModTRS/ folder.");
	    parent.getServer().getPluginManager().disablePlugin(parent);
	}

	ModTRSSettings.sqlite = DriverManager.getConnection(databaseUrl);

	ModTRS.log.debug("Creating tables if necessary" );
	Statement stat = ModTRSSettings.sqlite.createStatement();
	stat.executeUpdate(ModTRSSQL.createUser);
	stat.executeUpdate(ModTRSSQL.createRequest);

	ModTRS.log.debug("Finished loading SQLite" );

    }

}
