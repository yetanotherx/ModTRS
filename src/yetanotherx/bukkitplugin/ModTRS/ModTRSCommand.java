package yetanotherx.bukkitplugin.ModTRS;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.bukkit.entity.Player;

import yetanotherx.bukkitplugin.ModTRS.sql.ModTRSRequest;
import yetanotherx.bukkitplugin.ModTRS.sql.ModTRSUser;

public class ModTRSCommand {

    public static String TIMEDATE_FORMAT = "MMM.d@kk.mm.ss";

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
	request.insert(parent);
	//TODO: Error handling
	
	player.sendMessage( ModTRSMessage.messageSent );

	return true;

    }

    public static boolean onModReqCheckCommand( ModTRS parent, Player player, String[] args, String joined ) {
	System.out.println("Check");
	return false;

    }

    public static boolean onModReqInfoCommand( ModTRS parent, Player player, String[] args, String joined ) throws SQLException {
	
	ModTRSRequest request = new ModTRSRequest();
	request.setId( Integer.parseInt(args[0]) );
	
	if( request.requestExists( parent ) ) {
	    
	    request.getData( parent );
	    
	    Object[] parameters = { request.getId(), request.getModId(), request.getTimestamp(), request.getModTimestamp(),
		    			request.getWorld(), request.getX(), request.getY(), request.getZ(), request.getText(),
		    			request.getStatus(), request.getStatusText(true) };
	    
	    
	    
	    Calendar calendar = Calendar.getInstance();
	    Calendar calendarMod = Calendar.getInstance();
	    SimpleDateFormat sdf = new SimpleDateFormat(TIMEDATE_FORMAT);
            
	    calendar.setTimeInMillis( request.getTimestamp() );
	    calendarMod.setTimeInMillis( request.getModTimestamp() );
            
	    ModTRSUser filedUser = ModTRSUser.getUserFromId(request.getUserId(), parent);
	    ModTRSUser modUser = ModTRSUser.getUserFromId(request.getModId(), parent);
	    
	    Object[] filedByParameters = { filedUser.getName(), sdf.format(calendar.getTime()) };
	    

	    
	    player.sendMessage( ModTRSMessage.lineBreak );
	    
	    player.sendMessage( ModTRSMessage.parse( ModTRSMessage.infoForRequest, parameters ) );
	    player.sendMessage( ModTRSMessage.parse( ModTRSMessage.filedBy, filedByParameters ) );
	    
	    if( request.getModId() != 0 ) {
		Object[] modParameters = { modUser.getName(), sdf.format(calendarMod.getTime()) };
		player.sendMessage( ModTRSMessage.parse( ModTRSMessage.handledBy, modParameters ) );
	    }
	    
	    player.sendMessage( ModTRSMessage.parse( ModTRSMessage.requestText, parameters ) );
	    
	    player.sendMessage( ModTRSMessage.parse( ModTRSMessage.commandsOne, parameters ) );
	    player.sendMessage( ModTRSMessage.parse( ModTRSMessage.commandsTwo, parameters ) );
	    player.sendMessage( ModTRSMessage.parse( ModTRSMessage.commandsThree, parameters ) );
	    
	    player.sendMessage( ModTRSMessage.lineBreak );
	    
	    
	}
	else {
	    player.sendMessage( ModTRSMessage.noSuchRequest );
	}
	return true;

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
