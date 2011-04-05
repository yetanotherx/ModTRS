package yetanotherx.bukkitplugin.ModTRS.command;

import java.util.HashMap;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import yetanotherx.bukkitplugin.ModTRS.ModTRS;
import yetanotherx.bukkitplugin.ModTRS.ModTRSMessage;

public class CommandHandler implements CommandExecutor {

    private HashMap<String, CommandExecutor> commands = new HashMap<String, CommandExecutor>();
    
    public static String TIMEDATE_FORMAT = "MMM.d@kk.mm.ss";

    public CommandHandler(ModTRS parent) {
    }

    public static CommandHandler load(ModTRS parent) {
	
	CommandHandler handler = new CommandHandler(parent);
	
	handler.registerCommand( "modreq-help", new HelpCommand(parent));
	handler.registerCommand( "modreq", new ModreqCommand(parent));
	handler.registerCommand( "check", new CheckCommand(parent));
	handler.registerCommand( "check-id", new CheckIdCommand(parent));
	handler.registerCommand( "claim", new ClaimCommand(parent));
	handler.registerCommand( "unclaim", new UnclaimCommand(parent));
	handler.registerCommand( "complete", new CompleteCommand(parent));
	handler.registerCommand( "hold", new HoldCommand(parent));
	handler.registerCommand( "mod-broadcast", new BroadcastCommand(parent));
	handler.registerCommand( "modlist", new ModlistCommand(parent));
	
	//TODO: Check for permissions
	
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


	    if (commandName.equals("modreq")) {

		if (split.length == 1 && split[0].equals("help") ) {

		    if( !commands.containsKey("modreq-help") ) {
			return false;
		    }

		    return commands.get("modreq-help").onCommand(sender, command, commandLabel, args);

		}
		else if (split.length > 0) {

		    try {
			if( !commands.containsKey("modreq") ) {
			    return false;
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
	    //ConsoleCommandSender player = (ConsoleCommandSender) sender;

	    //TODO: Add console commands
	}

	return false;
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
