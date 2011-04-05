package yetanotherx.bukkitplugin.ModTRS;

//Bukkit imports
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import yetanotherx.bukkitplugin.ModTRS.command.CommandHandler;

//Java imports
import java.sql.SQLException;


//TODO: Add GPL header
public class ModTRS extends JavaPlugin {

    /**
     * Logger magic
     */
    public static final ModTRSLogger log = new ModTRSLogger();


    private CommandHandler commandHandler;

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

	try {
	    ModTRSSettings.load( this );
	}
	catch( SQLException e ) {
	    e.printStackTrace();
	    log.severe("SQL exception! Disabling plugin (version " + this.getDescription().getVersion() + ")");
	    this.getServer().getPluginManager().disablePlugin(this);
	}
	
	ModTRSPermissions.load(this);
	ModTRSHelp.load(this);
	this.commandHandler = CommandHandler.load(this);
	
	//TODO: Updater

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

}
