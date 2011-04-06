package yetanotherx.bukkitplugin.ModTRS;

import java.util.ArrayList;

import org.bukkit.entity.Player;

public class ModTRSHelp {
    
    private static ArrayList<String[]> helpCommands = new ArrayList<String[]>();
    
    public static void load( ModTRS parent ) {

	helpCommands.add( new String[] {"modreq [message]", "Request help from a moderator", "modtrs.command.modreq"} );
	helpCommands.add( new String[] {"check", "List all open requests", "modtrs.command.check"} );
	helpCommands.add( new String[] {"check-id [#]", "Get info about a request", "modtrs.command.check"} );
	helpCommands.add( new String[] {"claim [#]", "Claim the request", "modtrs.command.complete"} );
	helpCommands.add( new String[] {"unclaim [#]", "Unclaim the request", "modtrs.command.complete"} );
	helpCommands.add( new String[] {"done [#]", "Mark the request as completed", "modtrs.command.complete"} );
	helpCommands.add( new String[] {"hold [#]", "Put the request on hold", "modtrs.command.complete"} );
	helpCommands.add( new String[] {"mod-broadcast [message]", "Send a message to all moderators", "modtrs.command.broadcast"} );
	helpCommands.add( new String[] {"modlist", "List all moderators", "modtrs.command.modlist"} );
    }
    
    public static ArrayList<String> getMessages( Player player ) {
	
	ArrayList<String> messages = new ArrayList<String>();
	
	for( String[] command : helpCommands ) {
	    if( ModTRSPermissions.has(player, command[2] ) ) {
		messages.add( ModTRSMessage.parse( ModTRSMessage.helpCommand, command ) );
	    }
	}
	
	return messages;
	
    }

}
