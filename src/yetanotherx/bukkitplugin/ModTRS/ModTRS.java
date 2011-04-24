package yetanotherx.bukkitplugin.ModTRS;

//Bukkit imports
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

//LWC import
import com.griefcraft.lwc.Updater;

//ModTRS import
import java.sql.SQLException;
import yetanotherx.bukkitplugin.ModTRS.command.CommandHandler;
import yetanotherx.bukkitplugin.ModTRS.exception.ShutdownException;

//Java import
import yetanotherx.bukkitplugin.ModTRS.sql.ModTRSDatabase;


/*
 * ModTRS Version 1.2 - Moderator Request Ticket System
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
     * Database handler
     */
    public ModTRSDatabase databaseHandler;

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
	
	try {
	    if( databaseHandler != null && databaseHandler.getDatabase() != null ) {
		log.debug("Closing database");
		databaseHandler.getDatabase().close();
	    }
	} catch (SQLException e) {
            e.printStackTrace();
	}

        log.info("Plugin disabled. (version " + this.getDescription().getVersion() + ")");

    }

    /**
     * Perform some massive loading action
     * 
     * Step 1: Load and parse config file, creating it if it doesn't exist.
     * Step 2: Check for updates, downloading JAR files if necessary
     * Step 3: Initialize the database library, creating the tables if they don't exist
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

            if( ModTRSSettings.database.get("type").equals("mysql") ) {
                updater.db_name = "mysql.jar";
            }

	    updater.loadVersions();

            ModTRSUpdate.load(this);

	    try {
		this.databaseHandler = new ModTRSDatabase( this );
	    }
            catch( ShutdownException e ) {
                throw e; //Passing it on...
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

}
