package yetanotherx.bukkitplugin.ModTRS;

//Bukkit imports
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

//Permissions imports
import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;

//Java imports
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

//Other ModTRS imports
import yetanotherx.bukkitplugin.ModTRS.sql.ModTRSSQL;



public class ModTRS extends JavaPlugin {

    /**
     * Logger magic
     */
    public static final Logger log = Logger.getLogger("Minecraft");

    /**
     * Permission plugin
     */
    public static PermissionHandler Permissions = null;
    public boolean usePermissions = true;

    /**
     * SQLite stuff
     */
    public Connection sqlite;

    /**
     * Outputs a message when disabled
     */
    public void onDisable() {
	log.info( "[" + this.getDescription().getName() + "]" + " Plugin disabled. (version" + this.getDescription().getVersion() + ")");

	try {
	    sqlite.close();
	} catch (SQLException e) {
	    e.printStackTrace();
	}

    }

    /**
     * 
     * Checks that Permissions is installed.
     * 
     */
    public void setupPermissions() {

	Plugin perm_plugin = this.getServer().getPluginManager().getPlugin("Permissions");

	if( Permissions == null ) {
	    if( perm_plugin != null ) {
		//Permissions found, enable it now
		this.getServer().getPluginManager().enablePlugin( perm_plugin );
		Permissions = ( (Permissions) perm_plugin ).getHandler();
	    }
	    else {
		//Permissions not found. Disable plugin
		log.warning( "[" + this.getDescription().getName() + "]" + " Permissions plugin now found, defaulting to op. (version" + this.getDescription().getVersion() + ")");
		this.usePermissions = false;
	    }
	}
    }

    /**
     * 
     * Setup Permissions plugin & SQLite plugin
     * Hook the events into the plugin manager
     * 
     */
    public void onEnable() {

	//TODO: Setup a config file to specify additional databases
	
	setupPermissions();

	try {
	    setupSQLite();
	}
	catch( Exception e ) {
	    e.printStackTrace();
	    log.severe( "[" + this.getDescription().getName() + "]" + " SQL exception! Disabling plugin (version" + this.getDescription().getVersion() + ")");
	    this.getServer().getPluginManager().disablePlugin(this);
	}

	//Print that the plugin has been enabled!
	log.info( "[" + this.getDescription().getName() + "]" + " Plugin enabled! (version" + this.getDescription().getVersion() + ")");		
    }


    private void setupSQLite() throws SQLException, ClassNotFoundException {

	new File("plugins" + File.separator + "ModTRS" + File.separator).mkdirs();

	String databaseUrl = "jdbc:sqlite:plugins" + File.separator + "ModTRS" + File.separator + "modtrs.db";

	try {
	    Class.forName("org.sqlite.JDBC");
	}
	catch( ClassNotFoundException e) {
	    log.severe("Error: Cannot locate the SQLite JDBC. Please download from http://www.zentus.com/sqlitejdbc/ and place in the plugins/ModTRS/ folder.");
	    this.getPluginLoader().disablePlugin(this);
	}

	sqlite = DriverManager.getConnection(databaseUrl);

	Statement stat = sqlite.createStatement();
	stat.executeUpdate(ModTRSSQL.createUser);
	stat.executeUpdate(ModTRSSQL.createRequest);

    }

    /**
     * Called when a user performs a command
     */
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {

	String[] split = args;
	String joined = implode(split, " ");
	String commandName = command.getName().toLowerCase();

	if (sender instanceof Player) {
	    Player player = (Player) sender;

	    if (commandName.equals("modreq")) {

		if (split.length == 1 && split[0].equals("help") ) {
		    //TODO:Help

		    return true;
		}
		else if (split.length > 0) {

		    try {
			return ModTRSCommand.onModReqCommand(this, player, args, joined);
		    }
		    catch( Exception e ) {
			e.printStackTrace();
			player.sendMessage( ModTRSMessage.messageNotSent );
			return true;
		    }

		} 
		else {
		    return false;
		}

	    }
	    if (commandName.equals("modreq-check") ) {
		try {
		    return ModTRSCommand.onModReqCheckCommand(this, player, args, joined);
		}
		catch( Exception e ) {
		    e.printStackTrace();
		    player.sendMessage( ModTRSMessage.internalError );
		    return false;
		}
	    }
	    if (commandName.equals("modreq-info") && split.length == 1 ) {
		try {
		    return ModTRSCommand.onModReqInfoCommand(this, player, args, joined);
		}
		catch( Exception e ) {
		    e.printStackTrace();
		    player.sendMessage( ModTRSMessage.internalError );
		    return false;
		}
	    }
	    if (commandName.equals("modreq-claim") && split.length == 1 ) {
		try {
		    return ModTRSCommand.onModReqClaimCommand(this, player, args, joined);
		}
		catch( Exception e ) {
		    e.printStackTrace();
		    player.sendMessage( ModTRSMessage.internalError );
		    return false;
		}
	    }
	    if (commandName.equals("modreq-hold") && split.length == 1 ) {
		try {
		    return ModTRSCommand.onModReqHoldCommand(this, player, args, joined);
		}
		catch( Exception e ) {
		    e.printStackTrace();
		    player.sendMessage( ModTRSMessage.internalError );
		    return false;
		}
	    }
	    if (commandName.equals("modreq-complete") && split.length == 1 ) {
		try {
		    return ModTRSCommand.onModReqCompleteCommand(this, player, args, joined);
		}
		catch( Exception e ) {
		    e.printStackTrace();
		    player.sendMessage( ModTRSMessage.internalError );
		    return false;
		}
	    }
	    if (commandName.equals("mod-broadcast") && split.length > 0 ) {
		try {
		    return ModTRSCommand.onModBroadcastCommand(this, player, args, joined);
		}
		catch( Exception e ) {
		    e.printStackTrace();
		    player.sendMessage( ModTRSMessage.internalError );
		    return false;
		}
	    }
	    if (commandName.equals("modlist")) {
		try {
		    return ModTRSCommand.onModlistCommand(this, player, args, joined);
		}
		catch( Exception e ) {
		    e.printStackTrace();
		    player.sendMessage( ModTRSMessage.internalError );
		    return false;
		}
	    }
	}
	return false;
    }



    public boolean hasPermission( Player player, String permission ) {
	return this.hasPermission( player, permission, false );
    }

    public boolean hasPermission( Player player, String permission, boolean restricted ) {

	if( this.usePermissions ) {
	    return Permissions.has(player, permission);
	}
	else {
	    if( !restricted || player.isOp() ) {
		return true;
	    }
	    return false;
	}

    }

    public static String implode( String[] array, String glue ) {

	String out = "";

	if( array.length == 0 ) {
	    return out;
	}

	for( String part : array ) {
	    out = out + part + glue;
	}
	out = out.substring(0, out.length() - glue.length() );

	return out;
    }

}
