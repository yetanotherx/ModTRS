package yetanotherx.bukkitplugin.ModTRS;

import java.util.ArrayList;

import org.bukkit.entity.Player;

public class ModTRSHelp {
    
    private static ModTRS parent;
    private static ArrayList<String[]> helpCommands = new ArrayList<String[]>();
    
    public static void load( ModTRS parent ) {
	ModTRSHelp.parent = parent;
	
	helpCommands.add( new String[] {"modreq [message]", "Request help from a moderator", "modtrs.command.modreq"} );
	helpCommands.add( new String[] {"check", "Request help from a moderator", "modtrs.command.check"} );
	helpCommands.add( new String[] {"check-id", "Request help from a moderator", "modtrs.command.check"} );
	helpCommands.add( new String[] {"claim", "Request help from a moderator", "modtrs.command.complete"} );
	helpCommands.add( new String[] {"complete", "Request help from a moderator", "modtrs.command.complete"} );
	helpCommands.add( new String[] {"hold", "Request help from a moderator", "modtrs.command.complete"} );
	helpCommands.add( new String[] {"mod-broadcast", "Request help from a moderator", "modtrs.command.broadcast"} );
	helpCommands.add( new String[] {"modlist", "Request help from a moderator", "modtrs.command.modlist"} );
    }
    
    public static ArrayList<String> getMessages( Player player ) {
	
	ArrayList<String> messages = new ArrayList<String>();
	
	for( String[] command : helpCommands ) {
	    if( ModTRSPermissions.has(player, command[2] ) ) {
		messages.add( "/" + command[0] + " - " + command[1] );
	    }
	}
	
	return messages;
	
    }

}
