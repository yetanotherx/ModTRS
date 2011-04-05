package yetanotherx.bukkitplugin.ModTRS.command;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import yetanotherx.bukkitplugin.ModTRS.ModTRS;
import yetanotherx.bukkitplugin.ModTRS.ModTRSMessage;
import yetanotherx.bukkitplugin.ModTRS.sql.ModTRSRequest;
import yetanotherx.bukkitplugin.ModTRS.sql.ModTRSRequestTable;
import yetanotherx.bukkitplugin.ModTRS.sql.ModTRSUserTable;

public class CheckCommand implements CommandExecutor {

    public CheckCommand(ModTRS parent) {
    }



    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {

	Player player = (Player) sender;

	try {

	    ArrayList<ModTRSRequest> requests;
	    requests = ModTRSRequestTable.getOpenRequests();


	    player.sendMessage( ModTRSMessage.parse(ModTRSMessage.listIntro, new Object[] { requests.size() } ) );
	
	    int count = 1;
	    for( ModTRSRequest request : requests ) {
		if( count > 5 ) {
		    player.sendMessage( ModTRSMessage.parse(ModTRSMessage.tooMany, new Object[] { ( requests.size() - 5 ) } ) );
		    //TODO: Pager
		    break;
		}
		
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(CommandHandler.TIMEDATE_FORMAT);

		calendar.setTimeInMillis( request.getTimestamp() );
		
		String substring = request.getText();
		
		if( substring.length() >= 25 ) {
		    substring = substring.substring(0, 25) + "...";
		}
		
		Object[] params = new Object[] {
			request.getId(),
			sdf.format(calendar.getTime()),
			ModTRSUserTable.getUserFromId(request.getUserId()).getName(),
			substring
		};
		player.sendMessage( ModTRSMessage.parse(ModTRSMessage.listItem, params ) );
		
		count++;
	    }
	
	
	}
	catch( SQLException e ) {
	    e.printStackTrace();
	    player.sendMessage( ModTRSMessage.internalError );
	}

	//TODO: -all functionality
	return true;

    }

}
