package yetanotherx.bukkitplugin.ModTRS;

import java.sql.SQLException;

import org.bukkit.entity.Player;

import yetanotherx.bukkitplugin.ModTRS.sql.ModTRSRequest;
import yetanotherx.bukkitplugin.ModTRS.sql.ModTRSUser;

public class ModTRSCommand {


    public static boolean onModReqCommand( ModTRS parent, Player player, String[] args, String joined ) throws SQLException {

	ModTRSUser user = ModTRSUser.getUserFromName(player.getName(), parent);
	ModTRSRequest request = new ModTRSRequest();
	request.setUserId(user.getId());
	request.setText(joined);
	request.setTimestamp(System.currentTimeMillis());
	request.setWorld(player.getWorld().getName());
	request.setX(player.getLocation().getBlockX());
	request.setY(player.getLocation().getBlockY());
	request.setZ(player.getLocation().getBlockZ());
	
	if( request.insert(parent) ) {
	    player.sendMessage( ModTRSMessage.messageSent );

	    return true;
	}
	
	player.sendMessage( ModTRSMessage.messageNotSent );

	return true;

    }

    public static boolean onModReqCheckCommand( ModTRS parent, Player player, String[] args, String joined ) {
	System.out.println("Check");
	return false;

    }

    public static boolean onModReqInfoCommand( ModTRS parent, Player player, String[] args, String joined ) {
	System.out.println("Info");
	return false;

    }

    public static boolean onModReqClaimCommand( ModTRS parent, Player player, String[] args, String joined ) {
	System.out.println(args[0]);
	return false;

    }

    public static boolean onModReqHoldCommand( ModTRS parent, Player player, String[] args, String joined ) {
	System.out.println(args[0]);
	return false;

    }

    public static boolean onModReqCompleteCommand( ModTRS parent, Player player, String[] args, String joined ) {
	System.out.println(args[0]);
	return false;

    }

    public static boolean onModBroadcastCommand( ModTRS parent, Player player, String[] args, String joined ) {
	
	Player[] players = player.getServer().getOnlinePlayers();
	
	if( !ModTRS.Permissions.has(player, "modtrs.command.mod-broadcast") ) {
	    return true;
	}
	
	String[] message = {joined};
	
	for( Player user : players ) {
	    if( ModTRS.Permissions.has(user, "modtrs.mod") ) {
		user.sendMessage(ModTRSMessage.parse( ModTRSMessage.modbroadcast, message ) );
	    }
	}
	
	return true;

    }

    public static boolean onModlistCommand( ModTRS parent, Player player, String[] args, String joined ) {
	
	Player[] players = player.getServer().getOnlinePlayers();
	String mods = "";
	
	for( Player user : players ) {
	    if( ModTRS.Permissions.has(user, "modtrs.mod") ) {
		mods = mods + user.getName() + ", ";
	    }
	}
	
	mods = mods.substring(0, mods.length() - 2);
	String[] modlist = {mods};
	
	player.sendMessage( ModTRSMessage.parse( ModTRSMessage.modlist, modlist) );
	
	return true;

    }

}
