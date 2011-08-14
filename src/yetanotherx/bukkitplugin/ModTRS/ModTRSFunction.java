package yetanotherx.bukkitplugin.ModTRS;

import org.bukkit.Server;
import org.bukkit.entity.Player;

public class ModTRSFunction {
    
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

}
