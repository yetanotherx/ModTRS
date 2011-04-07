package yetanotherx.bukkitplugin.ModTRS;

//Bukkit imports
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.griefcraft.lwc.Updater;

//ModTRS imports
import yetanotherx.bukkitplugin.ModTRS.command.CommandHandler;

//Java imports
import java.sql.SQLException;


/*
 * ModTRS Version 0.2 - Moderator Request Ticket System
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
 * TODO: Notify user when they log in if their request is closed
 * TODO: Include part of the text when a request is closed (the notification)
 */
public class ModTRS extends JavaPlugin {

    /**
     * Logger magic
     */
    public static final ModTRSLogger log = new ModTRSLogger();


    private CommandHandler commandHandler;


    public ModTRSListeners listeners;


    private Updater updater;

    public ModTRS() {
	ModTRSSettings.load( this );

	updater = new Updater(this);
    }

    /**
     * Outputs a message when disabled
     */
    public void onDisable() {
	log.info("Plugin disabled. (version " + this.getDescription().getVersion() + ")");

	try {
	    if( ModTRSSettings.sqlite != null ) {
		ModTRSSettings.sqlite.close();
	    }
	} catch (SQLException e) {
	    e.printStackTrace();
	}

    }

    /**
     * 
     * Setup Permissions plugin & SQLite plugin
     * Hook the events into the plugin manager
     * 
     */
    public void onEnable() {

	ModTRSSettings.load( this );

	System.setProperty("org.sqlite.lib.path", updater.getOSSpecificFolder());
	updater.loadVersions(false);

	try {
	    ModTRSSettings.setupSQLite( this );
	}
	catch( Exception e ) {
	    e.printStackTrace();
	    log.severe("SQL exception! Disabling plugin (version " + this.getDescription().getVersion() + ")");
	    this.getServer().getPluginManager().disablePlugin(this);
	    return;
	}

	ModTRSPermissions.load(this);
	ModTRSHelp.load(this);
	this.listeners = ModTRSListeners.load(this);
	this.commandHandler = CommandHandler.load(this);

	//Print that the plugin has been enabled!
	log.info("Plugin enabled! (version " + this.getDescription().getVersion() + ")");

	log.debug("Debug mode enabled!");
    }

    /**
     * Called when a user performs a command
     */
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {

	return this.commandHandler.onCommand(sender, command, commandLabel, args);
    }
    
    public static void messageMods( String message, Server server ) {
	Player[] players = server.getOnlinePlayers();

	for( Player user : players ) {
	    if( ModTRSPermissions.has(user, "modtrs.mod") ) {
		user.sendMessage(message);
	    }
	}
    }

}
