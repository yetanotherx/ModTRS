package yetanotherx.bukkitplugin.ModTRS;

import java.sql.SQLException;

import org.bukkit.entity.Player;


public class ModTRSCommand {

    public static boolean onModReqCheckCommand( ModTRS parent, Player player, String[] args, String joined ) {
	//System.out.println("Check");
	return false;

    }

    public static boolean onModReqInfoCommand( ModTRS parent, Player player, String[] args, String joined ) throws SQLException {
	
	/*ModTRSRequest request = new ModTRSRequest();
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
	    
	    Object[] filedByParameters = { filedUser.getName(), sdf.format(calendar.getTime()), request.getX(), request.getY(), request.getZ() };
	    

	    player.sendMessage( ModTRSMessage.parse( ModTRSMessage.infoForRequest, parameters ) );
	    player.sendMessage( ModTRSMessage.parse( ModTRSMessage.filedBy, filedByParameters ) );
	    
	    if( request.getModId() != 0 ) {
		Object[] modParameters = { modUser.getName(), sdf.format(calendarMod.getTime()) };
		player.sendMessage( ModTRSMessage.parse( ModTRSMessage.handledBy, modParameters ) );
	    }
	    
	    player.sendMessage( ModTRSMessage.parse( ModTRSMessage.requestText, parameters ) );

	}
	else {
	    player.sendMessage( ModTRSMessage.noSuchRequest );
	}*/
	return true;

    }


}
