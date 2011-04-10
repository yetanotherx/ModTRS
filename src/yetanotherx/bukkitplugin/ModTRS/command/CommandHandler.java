package yetanotherx.bukkitplugin.ModTRS.command;

import java.util.HashMap;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import com.griefcraft.lwc.Updater;

import yetanotherx.bukkitplugin.ModTRS.validator.ModTRSValidatorHandler;
import yetanotherx.bukkitplugin.ModTRS.ModTRS;
import yetanotherx.bukkitplugin.ModTRS.ModTRSMessage;

public class CommandHandler implements CommandExecutor {

    /**
     * All the command executors
     */
    private HashMap<String, CommandExecutor> commands = new HashMap<String, CommandExecutor>();

    /**
     * ModTRS plugin
     */
    private ModTRS parent;

    /**
     * Format for timestamps
     */
    public static String TIMEDATE_FORMAT = "MMM.d@kk.mm.ss";

    public CommandHandler(ModTRS parent) {
	this.parent = parent;
    }

    /**
     * Registers all the commands
     */
    public static CommandHandler load(ModTRS parent) {

	ModTRS.log.debug("Loading command handlers");
	CommandHandler handler = new CommandHandler(parent);

	handler.registerCommand( "modreq-help", new HelpCommand(parent));
	handler.registerCommand( "modreq", new ModreqCommand(parent));
	handler.registerCommand( "check", new CheckCommand(parent));
	handler.registerCommand( "check-id", new CheckIdCommand(parent));
	handler.registerCommand( "claim", new ClaimCommand(parent));
	handler.registerCommand( "unclaim", new UnclaimCommand(parent));
	handler.registerCommand( "done", new CompleteCommand(parent));
	handler.registerCommand( "complete", new CompleteCommand(parent));
        handler.registerCommand( "reopen", new ReopenCommand(parent));
	handler.registerCommand( "hold", new HoldCommand(parent));
	handler.registerCommand( "mod-broadcast", new BroadcastCommand(parent));
	handler.registerCommand( "modlist", new ModlistCommand(parent));

	return handler;
    }

    public void registerCommand(String name, CommandExecutor command) {
	commands.put(name, command);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {

	String[] split = args;
	String commandName = command.getName().toLowerCase();

	if( sender instanceof Player ) {
	    Player player = (Player) sender;


	    /**
	     * What's this hackery?
	     * /modreq should go to ModreqCommand, but /modreq help should go to HelpCommand. Eww.
	     */
	    if (commandName.equals("modreq")) {

		if (split.length == 1 && split[0].equals("help") ) {

		    if( !commands.containsKey("modreq-help") ) {
			return false;
		    }

		    if( ModTRSValidatorHandler.getInstance().hasValidator("modreq-help")) {
			if( !ModTRSValidatorHandler.getInstance().getValidator("modreq-help").isValid(args) ) {
			    return false;
			}
		    }
		    return commands.get("modreq-help").onCommand(sender, command, commandLabel, args);

		}
		else if (split.length > 0) {

		    try {
			if( !commands.containsKey("modreq") ) {
			    return false;
			}

			if( ModTRSValidatorHandler.getInstance().hasValidator("modreq")) {
			    if( !ModTRSValidatorHandler.getInstance().getValidator("modreq").isValid(args) ) {
				return false;
			    }
			}
			return commands.get("modreq").onCommand(sender, command, commandLabel, args);
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
	    else {
		try {
		    if( !commands.containsKey(commandName) ) {

			return false;
		    }

		    if( ModTRSValidatorHandler.getInstance().hasValidator(commandName)) {
			if( !ModTRSValidatorHandler.getInstance().getValidator(commandName).isValid(args) ) {
			    return false;
			}
		    }
		    return commands.get(commandName).onCommand(sender, command, commandLabel, args);

		}
		catch( Exception e ) {
		    e.printStackTrace();
		    player.sendMessage( ModTRSMessage.internalError );
		    return true;
		}
	    }

	}
	else if( sender instanceof ConsoleCommandSender ) {

	    if (commandName.equals("modtrs")) {

		ConsoleCommandSender player = (ConsoleCommandSender) sender;

		if (args.length == 0) {
		    return true;
		}
		else if( args[0].equalsIgnoreCase("version") ) {
		    player.sendMessage("[ModTRS] You're running " + parent.getDescription().getName() + " version " + parent.getDescription().getVersion());
		}
		else if( args[0].equalsIgnoreCase("update") ) {
		    ModTRS.log.info("Checking for updates");
		    Updater updater = new Updater(parent);
		    System.setProperty("org.sqlite.lib.path", updater.getOSSpecificFolder());
		    updater.loadVersions();
		}

	    }
	}

	return true;
    }

}
