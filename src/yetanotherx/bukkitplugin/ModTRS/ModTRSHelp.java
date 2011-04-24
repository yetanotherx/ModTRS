package yetanotherx.bukkitplugin.ModTRS;

import java.util.ArrayList;

import org.bukkit.entity.Player;

/**
 * Fix /modtrs help to only show commands the user has permission to use.
 */
public class ModTRSHelp {

    /**
     * List of all help messages
     * 
     * String[0] = Command syntax
     * String[1] = Description
     * String[2] = Necessary permission
     * String[3] = Only set if it is to be given to all users if Permissions is not enabled
     */
    private static ArrayList<String[]> helpCommands = new ArrayList<String[]>();

    /**
     * Registers all the help commands
     */
    public static void load( ModTRS parent ) {

	ModTRS.log.debug("Loading help messages");

	helpCommands.add( new String[] {"modreq [message]", "Request help from a moderator", "modtrs.command.modreq", "anyone"} );
	helpCommands.add( new String[] {"check [t:open|all|held]", "List all open requests", "modtrs.command.check"} );
	helpCommands.add( new String[] {"check-id [#]", "Get info about a request", "modtrs.command.check"} );
	helpCommands.add( new String[] {"tp-id [#]", "Teleport to the location of a request", "modtrs.command.teleport"} );
	helpCommands.add( new String[] {"claim [#]", "Claim the request", "modtrs.command.complete"} );
	helpCommands.add( new String[] {"unclaim [#]", "Unclaim the request", "modtrs.command.complete"} );
	helpCommands.add( new String[] {"done [#]", "Mark the request as completed", "modtrs.command.complete"} );
	helpCommands.add( new String[] {"reopen [#]", "Reopen a closed or held request", "modtrs.command.complete"} );
	helpCommands.add( new String[] {"hold [#]", "Put the request on hold", "modtrs.command.complete"} );
	helpCommands.add( new String[] {"mod-broadcast [message]", "Send a message to all moderators", "modtrs.command.broadcast"} );
	helpCommands.add( new String[] {"modlist", "List all moderators", "modtrs.command.modlist", "anyone"} );

    }

    /**
     * Returns a list of commands that a player can use.
     */
    public static ArrayList<String> getMessages( Player player ) {

	ArrayList<String> messages = new ArrayList<String>();

	for( String[] command : helpCommands ) {
            //The fourth field is added to ones that non-ops can use when using ops.txt
	    if( command.length > 3 ) {
		if( ModTRSPermissions.has(player, command[2], false ) ) {
                    messages.add( ModTRSMessage.modreq.getHelpCommand(command[0], command[1]) );
		}
	    }
	    else {
		if( ModTRSPermissions.has(player, command[2] ) ) {
		    messages.add( ModTRSMessage.modreq.getHelpCommand(command[0], command[1]) );
		}
	    }
	}

	return messages;

    }

}
