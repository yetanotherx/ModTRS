package yetanotherx.bukkitplugin.ModTRS.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.bukkit.Server;
import org.bukkit.entity.Player;

public class ModTRSFunctions {
    
    /**
     * Send a message to all the moderators (people with the modtrs.mod permission)
     */
    public static void messageMods( String message, Server server ) {
	Player[] players = server.getOnlinePlayers();

	for( Player user : players ) {
	    if( ModTRSPermissions.has(user, "modtrs.mod") ) {
		user.sendMessage(message);
	    }
	}
    }
    
    /**
     * Returns true if a user is online
     */
    public static boolean isUserOnline( String username, Server server ) {
	return server.getPlayer(username).isOnline();
    }
    
    /**
     * Join a String[] into a single string with a joiner
     */
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

    public static String[] removeFirstArg(String[] args) {
        if( args.length == 0 ) {
            return args;
        }
        ArrayList<String> list = new ArrayList<String>(Arrays.asList(args));
        list.remove(0);
        return list.toArray(new String[0]);
    }

}
